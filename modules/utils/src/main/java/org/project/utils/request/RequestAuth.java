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

    public String bearerToken(String accessToken) {
        return "Bearer " + accessToken;
    }

    public RequestSpecification none() {
        return auth().none();
    }

    /**
     * При упреждающей аутентификации данные для аутентификации отправляются в заголовке запроса независимо от того, запрашивает ли их сервер.
     * @param username
     * @param password
     * @return RequestSpecification
     */
    public RequestSpecification basic(String username, String password) {
        return auth().basic(username, password);
    }

    /**
     * При упреждающей аутентификации данные для аутентификации отправляются в заголовке запроса независимо от того, запрашивает ли их сервер.
     * @param username
     * @param password
     * @return RequestSpecification
     */
    public RequestSpecification preemptive(String username, String password) {
        return auth().preemptive().basic(username, password);
    }

    /**
     * Этот метод похож на аутентификацию на основе запроса, но он более безопасен, так как в последующих запросах используется дайджест-ключ.
     * <p>Обратите внимание, что мы не можем использовать {@code preemptive()} метод, аналогичный базовой аутентификации, поскольку в этой схеме используется только аутентификация по запросу.
     * @param username
     * @param password
     * @return RequestSpecification
     */
    public RequestSpecification digest(String username, String password) {
        return auth().digest(username, password);
    }

    /**
     * Сначала проанализирует HTML-ответ, чтобы найти поля для ввода, а затем отправит параметры формы.
     * <p>Процесс может завершиться неудачей, например, если веб-страница сложная или если сервис настроен с использованием контекстного пути, который не указан в атрибуте {@code action}.
     * @param username
     * @param password
     * @return RequestSpecification
     */
    public RequestSpecification form(String username, String password) {
        return auth().form(username, password);
    }

    /**
     * Сначала проанализирует HTML-ответ, чтобы найти поля для ввода, а затем отправит параметры формы.
     * <p>{@code new FormAuthConfig("/perform_signIn", "user", "password");}
     * Помимо этих базовых конфигураций, REST Assured предоставляет следующие функции:
     * <ul>
     * <li>обнаружение или указание поля токена CSRF на веб-странице.
     * <li>использование дополнительных полей формы в запросе.
     * <li>запись информации о процессе аутентификации в журнал.
     * </ul>
     * @param username
     * @param password
     * @param action
     * @param userNameTag
     * @param passwordTag
     * @return RequestSpecification
     */
    public RequestSpecification form(String username, String password, String action, String userNameTag, String passwordTag) {
        return form(username, password, new FormAuthConfig(action, userNameTag, passwordTag));
    }

    /**
     * Сначала проанализирует HTML-ответ, чтобы найти поля для ввода, а затем отправит параметры формы.
     * <p>{@code new FormAuthConfig("/perform_signIn", "user", "password");}
     * @param username
     * @param password
     * @return RequestSpecification
     */
    public RequestSpecification form(String username, String password, FormAuthConfig config) {
        return auth().form(username, password, config);
    }

    /**
     * Параметры <b>OAuth</b> динамически считывают необходимые данные от пользователя.
     * @param consumerKey
     * @param consumerSecret
     * @param accessToken
     * @param tokenSecret
     * @return RequestSpecification
     */
    public RequestSpecification oauth(String consumerKey, String consumerSecret, String accessToken, String tokenSecret) {
        return auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret);
    }

    /**
     * Параметры <b>OAuth</b> динамически считывают необходимые данные от пользователя.
     * @param consumerKey
     * @param consumerSecret
     * @param accessToken
     * @param tokenSecret
     * @param sign
     * @return RequestSpecification
     */
    public RequestSpecification oauth(String consumerKey, String consumerSecret, String accessToken, String tokenSecret, OAuthSignature sign) {
        return auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret, sign);
    }

    /**
     * При использовании <b>OAuth 2.0</b> необходимо напрямую передавать токен доступа.
     * <p>Нужно будет добавить зависимость {@code scribejava-apis}, если мы используем функции OAuth 2.0 в версии ниже 2.5.0 или если мы используем функции OAuth 1.0a.
     * @param accessToken
     * @return RequestSpecification
     */
    public RequestSpecification oauth2(String accessToken) {
        return auth().oauth2(accessToken);
    }

    /**
     * При использовании <b>OAuth 2.0</b> необходимо напрямую передавать токен доступа.
     * <p>Нужно будет добавить зависимость {@code scribejava-apis}, если мы используем функции OAuth 2.0 в версии ниже 2.5.0 или если мы используем функции OAuth 1.0a.
     * @param accessToken
     * @param sign
     * @return RequestSpecification
     */
    public RequestSpecification oauth2(String accessToken, OAuthSignature sign) {
        return auth().oauth2(accessToken, sign);
    }

    public RequestSpecification certificate(String username, String password) {
        return auth().certificate(username, password);
    }

    public RequestSpecification certificate(String username, String password, CertificateAuthSettings config) {
        return auth().certificate(username, password, config);
    }

    public RequestSpecification bearer(String accessToken) {
        return oauth2(bearerToken(accessToken));
    }

    public RequestSpecification bearer(String accessToken, OAuthSignature sign) {
        return oauth2(bearerToken(accessToken), sign);
    }

}
