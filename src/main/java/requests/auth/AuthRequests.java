package requests.auth;

import org.project.utils.request.BaseRequests;
import pojo.json.auth.Token;

import static constant.UrlConstants.AUTH_URL;

public class AuthRequests extends BaseRequests<Token> {

    public AuthRequests() {
        super(AUTH_URL);
    }

}
