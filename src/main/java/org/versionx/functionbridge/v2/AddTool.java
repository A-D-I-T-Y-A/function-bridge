package org.versionx.functionbridge.v2;

import org.graalvm.polyglot.Value;

public class AddTool extends AbstractTool {

    Integer extraValue = 0;

    public AddTool() {
        argumentNames.add("ab");
        argumentNames.add("cd");
        argumentTypes.add(Integer.class);
        argumentTypes.add(Integer.class);
    }

    @Override
    public Object execute(Value... args) {
        Integer a = args[0].asInt();
        Integer b = args[1].asInt();
        return add(a, b);
    }

    private Integer add(Integer a, Integer b) {
        return a + b + (Integer)executionContext;
    }
}
