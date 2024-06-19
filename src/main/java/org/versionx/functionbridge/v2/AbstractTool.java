package org.versionx.functionbridge.v2;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTool implements Tool {
    protected List<String> argumentNames = new ArrayList<>();
    protected List<Class<?>> argumentTypes = new ArrayList<>();
    protected Object executionContext = null;

    @Override
    public List<String> getArgumentNames() {
        return argumentNames;
    }

    @Override
    public List<Class<?>> getArgumentTypes() {
        return argumentTypes;
    }

    @Override
    public void setExecutionContext(Object executionContext) {
        this.executionContext = executionContext;
    }


}