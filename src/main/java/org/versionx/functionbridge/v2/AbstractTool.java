package org.versionx.functionbridge.v2;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTool implements Tool {
    protected List<String> argumentNames = new ArrayList<>();
    protected Object executionParams = null;

    @Override
    public List<String> getArgumentNames() {
        return argumentNames;
    }

    @Override
    public void setExecutionParams(Object executionParams) {
        this.executionParams = executionParams;
    }


}