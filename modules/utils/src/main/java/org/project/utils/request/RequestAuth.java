package org.project.utils.request;

import io.restassured.authentication.CertificateAuthSettings;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.authentication.OAuthSignature;
import io.restassured.specification.RequestSpecification;

/**
 * <b>Authentication Specification:</b>
 * <ul>
 * <li>{@code Basic} отправка username и пароля, закодированных в Base64.
 * <li>{@code Preemptive} отправка credentials в первом запросе, даже перед получением ответа от сервера. Это полезно для API, которые требуют аутентификации для каждого запроса и не требуют вызова с кодом статуса 401.
 * <li>{@code Form} аутентификация на основе HTML-формы, где credentials передаются в запросе. REST Assured анализирует ответ HTML, находит поля для ввода и отправляет параметры формы.
 * <li>{@code OAuth 2.0} конфигурирование токена доступа для запроса защищённого ресурса.
 * <li>{@code OAuth 1.0a} метод, получающий Consumer Key, Secret, Access Token и Token Secret для доступа к защищённому ресурсу.
 * </ul>
 * Некоторые методы интерфейса {@code AuthenticationSpecification} и их описание:
 * <ul>
 * <li>{@code basic(String userName, String password)} использует базовую аутентификацию.
 * <li>{@code preemptive()} возвращает спецификацию упреждающей аутентификации.
 * <li>{@code none()} явно указывает, что в запросе не будет использоваться аутентификация. Это полезно, если указана схема аутентификации по умолчанию, и нужно переопределить её для одного запроса.
 * </ul>
 * При использовании {@code AuthenticationSpecification} в <b>REST Assured</b> могут возникать ошибки, например:
 * <ul>
 * <li>Ошибка при использовании OAuth 2.0 — библиотека не помогает в получении токена доступа, его нужно получить самому.
 * <li>Ошибка при использовании аутентификации через форму — REST Assured может не работать, если страница логина сложная или путь контекста не включён в атрибут действия сервиса. В этом случае можно явно передать необходимые поля, указав конфигурацию аутентификации (FormAuthConfig).
 * </ul>
 *
 * @see     RequestOptions#auth()
 * @since   4.18.0
 */
public class RequestAuth<T extends RequestAuth<T>> extends RequestOptions<T> {

    public RequestSpecification none() {
        return auth().none();
    }

    public RequestSpecification basic(String username, String password) {
        return auth().basic(username, password);
    }

    public RequestSpecification preemptive(String username, String password) {
        return auth().preemptive().basic(username, password);
    }

    public RequestSpecification digest(String username, String password) {
        return auth().digest(username, password);
    }

    public RequestSpecification form(String username, String password) {
        return auth().form(username, password);
    }

    public RequestSpecification form(String username, String password, FormAuthConfig config) {
        return auth().form(username, password, config);
    }

    public RequestSpecification certificate(String username, String password) {
        return auth().certificate(username, password);
    }

    public RequestSpecification certificate(String username, String password, CertificateAuthSettings config) {
        return auth().certificate(username, password, config);
    }

    public RequestSpecification oauth(String s, String s1, String s2, String s3) {
        return auth().oauth(s, s1, s2, s3);
    }

    public RequestSpecification oauth(String s, String s1, String s2, String s3, OAuthSignature sign) {
        return auth().oauth(s, s1, s2, s3, sign);
    }

    public RequestSpecification oauth2(String s) {
        return auth().oauth2(s);
    }

    public RequestSpecification oauth2(String s, OAuthSignature sign) {
        return auth().oauth2(s, sign);
    }

}
