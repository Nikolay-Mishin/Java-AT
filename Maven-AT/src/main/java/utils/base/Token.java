package utils.base;

import java.beans.ConstructorProperties;

public class Token {

    private String token;
    private String fileToken;
    private String refreshToken;

    @ConstructorProperties({"token", "fileToken", "refreshToken"})
    public Token(String token, String fileToken, String refreshToken) {
        this.token = token;
        this.fileToken = fileToken;
        this.refreshToken = refreshToken;
    }

    public void refreshTokens(Token token) {
        this.setToken(token.token);
        this.setFileToken(token.fileToken);
        this.setRefreshToken(token.refreshToken);
    }

    public String getToken() {
        return token;
    }

    public String getFileToken() {
        return fileToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    private void setToken(String token) {
        this.token = token;
    }

    private void setFileToken(String fileToken) {
        this.fileToken = fileToken;
    }

    private void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
