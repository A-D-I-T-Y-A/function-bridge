package org.versionx.functionbridge.oldcode;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class FunctionRegistry {

    private Map<String, ScriptFunction> registry;

    private Context context;

    private Map<String, Source> sourceCache;

    private Object executionContext;

    private List<String> enabledLanguages;

    public FunctionRegistry(String... languages) {

        this.enabledLanguages = Arrays.asList(languages);
        this.registry = new ConcurrentHashMap<>();
        this.context = Context.newBuilder(languages).allowAllAccess(true).build();
        this.sourceCache = new ConcurrentHashMap<>();
        for(String language : languages){
            this.context.initialize(language);
        }
    }

    public void registerMultiLanguageFunction(String registeredName, String language, String scriptPath, String functionName) throws IOException {
        if(sourceCache.containsKey(scriptPath)){
            //get member directly
        } else {
            Source source = Source.newBuilder(language, new File(scriptPath)).build();
            context.eval(source);
            sourceCache.put(scriptPath, source);
        }

        ScriptFunction function = new ExternalFunction(this.context.getBindings(language).getMember(functionName));
        this.registry.put(registeredName, function);

    }

    public void registerFunction(String name, ScriptFunction function) {
        registry.put(name, function);
    }

    public ScriptFunction getFunction(String name) {
        return registry.get(name);
    }

    public boolean hasFunction(String name) {
        return registry.containsKey(name);
    }

    public void removeFunction(String name) {
        registry.remove(name);
    }

    public Map<String, ScriptFunction> getAllFunctions() {
        return registry;
    }

    public void setExecutionContext(Object executionContext) {
        this.executionContext = executionContext;
        for(String language: enabledLanguages){
            this.context.getBindings(language).putMember("executionContext", executionContext);
        }
    }

    private class ExternalFunction implements ScriptFunction {

        Value function;

        public ExternalFunction(Value function){
            assert function.canExecute();
            this.function = function;
        }

        @Override
        public Object execute(Object... args) {
            return function.execute(args);
        }
    }

}
