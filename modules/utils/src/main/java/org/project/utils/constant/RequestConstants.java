package org.project.utils.constant;

/**
 *
 */
public interface RequestConstants {
    /**
     *
     */
    enum METHOD {
        /**
         *
         */
        GET,
        /**
         *
         */
        POST,
        /**
         *
         */
        PUT,
        /**
         *
         */
        PATCH,
        /**
         *
         */
        DELETE
    }

    /**
     *
     */
    enum METHOD_LOWER_CASE {
        /**
         *
         */
        get,
        /**
         *
         */
        post,
        /**
         *
         */
        put,
        /**
         *
         */
        patch,
        /**
         *
         */
        delete
    }

    /**
     * POST -> post
     * @param method METHOD
     * @return String
     */
    static String getMethod(METHOD method) { return method.toString().toLowerCase(); };
}
