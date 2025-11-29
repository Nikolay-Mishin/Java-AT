package requests.auth;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.project.utils.request.AuthBaseRequests;

import static constant.UrlConstants.AUTH_URL;

import pojo.json.auth.Token;

public class AuthRequests extends AuthBaseRequests<Token> {
    public AuthRequests() throws MalformedURLException, URISyntaxException {
        super(AUTH_URL);
    }
}
