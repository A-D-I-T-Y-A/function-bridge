package org.versionx.functionbridge;

import org.graalvm.polyglot.*;
import org.graalvm.polyglot.proxy.ProxyExecutable;

import java.util.Map;

public class DynamicScriptExecutor {

    public static void executeScript(String language, String script, FunctionRegistry registry) {
        try (Context context = Context.newBuilder("python").allowAllAccess(true).build()) {

            // Create a new bindings object to hold the functions
            Value bindings = context.getBindings("python");

            // Inject registered functions into the context
            for (Map.Entry<String, ScriptFunction> entry : registry.getAllFunctions().entrySet()) {
                String functionName = entry.getKey();
                ScriptFunction function = entry.getValue();

                // Create a proxy for the function
                ProxyExecutable proxy = function::execute;
                bindings.putMember(functionName, proxy);
            }

            // Evaluate the user script with the injected functions
            context.eval("python", script);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
