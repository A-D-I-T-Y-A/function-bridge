package org.versionx.functionbridge.v2;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.proxy.ProxyExecutable;

import java.util.List;

public class PythonContextEmbedder implements ContextEmbedder{

    @Override
    public void embedTool(String embeddedName, Tool tool, Context context) {
        List<String> argumentNames = tool.getArgumentNames();

        String privateName = "_" + embeddedName;
        ProxyExecutable proxy = tool::execute;
        context.getBindings("python").putMember(privateName, proxy);

        List<String> wrapperBodyItems = argumentNames.stream().map(this::getElement).toList();
        String wrapperBody = privateName + "(" + String.join(", ", wrapperBodyItems) + ")";

        String wrapperDefinition = "def " + embeddedName + "(**kwargs):\n" +
                " return " + wrapperBody;

        context.eval("python", wrapperDefinition);

    }

    private String getElement(String argumentName) {
        return "kwargs.get('" + argumentName + "')";
    }
}
