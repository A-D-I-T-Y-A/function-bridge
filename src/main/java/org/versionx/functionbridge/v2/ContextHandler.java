package org.versionx.functionbridge.v2;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;

import java.io.File;
import java.io.IOException;

public class ContextHandler {

    private final String languages;

    Context context;

    public ContextHandler(String languages) {
        this.languages = languages;
        this.context = Context.newBuilder(languages).allowAllAccess(true).build();
    }

    public void loadFile(String language, String path) throws IOException {
        this.context.eval(Source.newBuilder(language, new File(path)).build());
    }

    public Context getContext(){
        return this.context;
    }

}
