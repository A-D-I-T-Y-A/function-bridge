package org.versionx.functionbridge.v2;

import org.graalvm.polyglot.Value;

import java.util.List;

public interface Tool {
    /**
     * Execute the tool with the given arguments.
     *
     * @param args the arguments to be passed to the tool.
     * @return the result of the execution.
     */
    Object execute(Value... args);

    /**
     * Get the list of argument names that this tool expects.
     *
     * @return the list of argument names.
     */
    List<String> getArgumentNames();


    void setExecutionParams(Object executionParams);
}