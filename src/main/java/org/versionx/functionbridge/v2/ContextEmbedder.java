package org.versionx.functionbridge.v2;

import org.graalvm.polyglot.Context;

public interface ContextEmbedder {

    public void embedTool(String embeddedName, Tool tool, Context context);

}
