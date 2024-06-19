package org.versionx.functionbridge.oldcode;

import org.graalvm.polyglot.Context;

public class Main {
    // Example Java function with optional arguments
    public static int add(int a, int b) {
        return a + b;
    }

    // Example Java function with optional arguments
    public static String transformText(String text,
                                       boolean reverse) {
        if (reverse) {
            text = new StringBuilder(text).reverse().toString();
        }
        return text;
    }

    public static class Maths {

        public int add(int a, int b) {
            return a + b;
        }

    }

    public static void main(String[] args) {
        try (Context context = Context.newBuilder().allowAllAccess(true).build()) {
            // Add functions to the context
            Maths maths = new Maths();
            context.getBindings("python").putMember("maths_1", maths);

            // Run a sample Python script

            context.eval("python", "print(add(a=5,b=6))");
        }
    }


}