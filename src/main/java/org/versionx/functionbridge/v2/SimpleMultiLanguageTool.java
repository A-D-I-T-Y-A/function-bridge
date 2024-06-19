package org.versionx.functionbridge.v2;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleMultiLanguageTool extends AbstractTool{

    private Context context;
    private String functionName;
    private Value function;

    public SimpleMultiLanguageTool(String functionName, String language, Context context, String... parameterNames) {
        this.context = context;
        this.functionName = functionName;

        this.function = this.context.getBindings(language).getMember(this.functionName);

        this.argumentNames.addAll(Arrays.stream(parameterNames).toList());
    }

    @Override
    public Object execute(Value... args) {
        List<Value> argList = new ArrayList<>(List.of(args));
        argList.addFirst(Value.asValue(executionParams));
        return this.function.execute(argList.toArray());
    }
}
