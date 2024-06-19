package org.versionx.functionbridge.v2;

import java.util.HashMap;
import java.util.Map;

public class ToolRegistry {

    private Map<String, Tool> tools = new HashMap<String, Tool>();
    private Object executionContext;

    public ToolRegistry() {

    }

    public void registerTool(String toolName, Tool tool) {
        tools.put(toolName, tool);
    }

    public Map<String, Tool> getTools(){
        return tools;
    }

    public void setExecutionContext(Object executionContext) {
        this.executionContext = executionContext;
        tools.forEach((name, tool) -> tool.setExecutionParams(executionContext));
    }
}
