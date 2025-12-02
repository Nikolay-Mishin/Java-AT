package org.project.utils.constant;

import static org.project.utils.config.WebConfig.config;

public enum ContentType {
    ANY(new String[]{"*/*"});

    private final String[] ctStrings;

    public String toString() {
        return this.ctStrings[0];
    }

    private ContentType(String... contentTypes) {
        this.ctStrings = contentTypes;
    }

    public static io.restassured.http.ContentType getContentType() {
        return config().getContentType();
    };

    public static io.restassured.http.ContentType getAccept() {
        return config().getAccept();
    };
}
