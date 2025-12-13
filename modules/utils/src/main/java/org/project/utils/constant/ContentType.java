package org.project.utils.constant;

import static org.project.utils.config.WebConfig.config;

/**
 *
 */
public enum ContentType {
    /**
     *
     */
    ANY(new String[]{"*/*"});

    /**
     *
     */
    private final String[] ctStrings;

    /**
     *
     * @param contentTypes String[]
     */
    ContentType(String... contentTypes) {
        this.ctStrings = contentTypes;
    }
    @Override
    public String toString() {
        return this.ctStrings[0];
    }
    /**
     *
     * @return ContentType
     */
    public static io.restassured.http.ContentType getContentType() {
        return config().getContentType();
    };
    /**
     *
     * @return ContentType
     */
    public static io.restassured.http.ContentType getAccept() {
        return config().getAccept();
    };
}
