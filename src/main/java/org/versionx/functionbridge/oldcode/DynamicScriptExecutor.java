package org.versionx.functionbridge.oldcode;

import org.graalvm.polyglot.*;
import org.graalvm.polyglot.proxy.ProxyExecutable;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class DynamicScriptExecutor {

    public static Map<String, Context> contextCache = new HashMap<String, Context>();

    public Object executeScript(String language, String script, FunctionRegistry registry) {
        Context context = contextCache.computeIfAbsent(language, k -> Context.newBuilder(k).allowAllAccess(true).build());
//        try (Context context = Context.newBuilder(language).allowAllAccess(true).build()) {

            // Create a new bindings object to hold the functions
            Value bindings = context.getBindings(language);

            // Inject registered functions into the context
//            for (Map.Entry<String, ScriptFunction> entry : registry.getAllFunctions().entrySet()) {
//                String functionName = entry.getKey();
//                ScriptFunction function = entry.getValue();
//
//                // Create a proxy for the function
//                ProxyExecutable proxy = function::execute;
//                bindings.putMember(functionName, proxy);
//            }

            ProxyExecutable proxyAdd = this::execAdd;
//            ProxyExecutable proxyMultiply = DynamicScriptExecutor::execMultiply;
            bindings.putMember("add_java", proxyAdd);
//            bindings.putMember("multiply", proxyMultiply);

            String wrapper = """
                    def add(**kwargs):
                        return add_java(kwargs.get('a'), kwargs.get('b'))
                    """;
            context.eval(language, wrapper);

            // Evaluate the user script with the injected functions
            return context.eval(language, script);

//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error: " + e.getMessage();
//        }
    }

    private Object execAdd(Value... values){
        try {
            Method method = DynamicScriptExecutor.class.getMethod("add", Integer.class, Integer.class);
            Integer a = values[0].isNull()? 0: values[0].asInt();
            Integer b = values[1].isNull()? 0: values[1].asInt();
            return method.invoke(this, a, b);
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    private static Object execMultiply(Value... values) {
        try {
            Method[] methods = DynamicScriptExecutor.class.getMethods();
            for (Method method : methods) {
                if (method.getName().equals("multiply")) {
                    Map<String, Object> namedArgs = new HashMap<>();
                    for (Value arg : values) {
                        if (arg.hasMembers()) {
                            for (String key : arg.getMemberKeys()) {
                                namedArgs.put(key, arg.getMember(key).as(Object.class));
                            }
                        } else {
                            throw new IllegalArgumentException("Expected named arguments as a dictionary");
                        }
                    }

                    Parameter[] parameters = method.getParameters();
                    Object[] parametersWithDefaults = new Object[parameters.length];
                    for (int i = 0; i < parameters.length; i++) {
                        String paramName = parameters[i].getName();
                        if (namedArgs.containsKey(paramName)) {
                            parametersWithDefaults[i] = namedArgs.get(paramName);
                        } else {
                            parametersWithDefaults[i] = 100; // Or throw an error if needed
                        }
                    }
                    return method.invoke(null, parametersWithDefaults);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Integer add(Integer a, Integer b) {
        return a + b;
    }

    public static int multiply(int a, int b) {
        return a * b;
    }
}
