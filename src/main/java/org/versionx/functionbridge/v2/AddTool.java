package org.versionx.functionbridge.v2;

import org.graalvm.polyglot.Value;

public class AddTool extends AbstractTool {

    public AddTool() {
        argumentNames.add("a");
        argumentNames.add("b");
    }

    @Override
    public Object execute(Value... args) {
        Integer a = args[0].asInt();
        Integer b = args[1].asInt();
        return add(a, b);
    }

    private Integer add(Integer a, Integer b) {
        if((Integer) executionParams == 123)
            return a + b;
        else {
            return 0;
        }
    }
}
