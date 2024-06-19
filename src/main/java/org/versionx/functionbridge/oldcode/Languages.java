package org.versionx.functionbridge.oldcode;

public enum Languages {

    JS("js"),
    PYTHON("python"),
    JAVA("java");

    private final String name;

    Languages(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
