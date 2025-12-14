package org.project.utils.request;

/**
 *
 * @param <T> T
 */
public class AuthBaseRequests<T> extends BaseRequests<T> {
    /**
     *
     * @param baseUrl String
     * @throws Exception throws
     */
    public AuthBaseRequests(String baseUrl) throws Exception {
        super(baseUrl);
    }
}
