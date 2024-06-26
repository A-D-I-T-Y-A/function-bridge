package org.versionx.functionbridge.v2;

import org.graalvm.polyglot.Context;

import java.util.Objects;

public class ScriptExecutor {

    private Context cachedContext = null;
    private ToolRegistry toolRegistry = null;
    private String language = null;
    private ContextEmbedder contextEmbedder = null;

    public ScriptExecutor(String language, ToolRegistry toolRegistry) {
        this.language = language;
        this.toolRegistry = toolRegistry;
        this.cachedContext = Context.newBuilder(language).allowAllAccess(true).build();

        if (Objects.equals(language, "python")) {
            this.contextEmbedder = new PythonContextEmbedder();
        }

        this.toolRegistry.getTools().forEach((name,tool) -> this.contextEmbedder.embedTool(name, tool, this.cachedContext));
    }

    public Object executeScript(String script, Boolean useCachedContext) {

        Context localContext = useCachedContext ? this.cachedContext : buildNewContext();

        return localContext.eval(this.language, script);

    }

    private Context buildNewContext(){
        Context newContext = Context.newBuilder(this.language).allowAllAccess(true).build();
        this.toolRegistry.getTools().forEach((name,tool) -> this.contextEmbedder.embedTool(name, tool, newContext));
        return newContext;
    }
}