package requests.auth;

import org.project.utils.request.AuthBaseRequests;
import pojo.json.auth.Token;

import static constant.UrlConstants.AUTH_URL;

public class AuthRequests extends AuthBaseRequests<Token> {
    public AuthRequests() {
        super(AUTH_URL);
    }
}
