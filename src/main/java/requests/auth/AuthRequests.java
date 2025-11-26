package requests.auth;

import org.project.utils.request.AuthBaseRequests;

import static constant.UrlConstants.AUTH_URL;

import pojo.json.auth.Token;

public class AuthRequests extends AuthBaseRequests<Token> {
    public AuthRequests() {
        super(AUTH_URL);
    }
}
