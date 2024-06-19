package org.versionx.functionbridge;

import org.versionx.functionbridge.v2.AddTool;
import org.versionx.functionbridge.v2.DynamicScriptExecutor;
import org.versionx.functionbridge.v2.Tool;
import org.versionx.functionbridge.v2.ToolRegistry;

public class DemoV2 {

    public static void main(String[] args) {

        ToolRegistry registry = new ToolRegistry();
        Tool addTool = new AddTool();
        registry.registerTool("add", addTool);
        org.versionx.functionbridge.v2.DynamicScriptExecutor executor = new DynamicScriptExecutor("python", registry);

        int extraValue = 5;
        registry.setExecutionContext(extraValue);

        String userScript = "result_add = add(ab=5, cd=3)\n" +
                "print(result_add)\n" +
                "result_add";

        executor.executeScript(userScript, true);

    }

}
