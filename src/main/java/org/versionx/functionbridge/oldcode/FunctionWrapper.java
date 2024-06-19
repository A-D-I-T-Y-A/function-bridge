package org.versionx.functionbridge.oldcode;

import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyExecutable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class FunctionWrapper implements ProxyExecutable {
    private final String methodName;
    private final Class<?> clazz;

    public FunctionWrapper(String methodName, Class<?> clazz) {
        this.methodName = methodName;
        this.clazz = clazz;
    }

    @Override
    public Object execute(Value... args) {
        try {
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Map<String, Object> namedArgs = new HashMap<>();
                    for (Value arg : args) {
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
}

