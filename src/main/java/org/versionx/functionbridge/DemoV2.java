package org.versionx.functionbridge;

import org.graalvm.polyglot.Context;
import org.versionx.functionbridge.v2.*;

import java.io.IOException;

public class DemoV2 {

    public static void main(String[] args) throws IOException {

        ContextHandler contextHandler = new ContextHandler("python");
        contextHandler.loadFile("python", "src/main/resources/functions.py");

        ToolRegistry registry = new ToolRegistry();

        Tool addTool = new AddTool();
        Tool multiplyTool = new SimpleMultiLanguageTool("multiply", "python",
                contextHandler.getContext(), "a", "b");

        registry.registerTool("add", addTool);
        registry.registerTool("multiply", multiplyTool);

        // Upon receiving request
        Integer userId = 123;
        String userScript = "multiply(a=5)";

        registry.setExecutionContext(userId);

        ScriptExecutor executor = new ScriptExecutor("python", registry);
        System.out.println(executor.executeScript(userScript, true));

    }

}
