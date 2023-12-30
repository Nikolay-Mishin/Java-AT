package constant;

public interface UrlConstants {
    // список endpoits's
    // константы пишутся camelCase + postfix = "URL"

    // auth
    String AUTH_URL = "login";

    // pet
    String PET_URL = "pet";

    // store
    String STORE_BASE_URL = "store/";
    String ORDER_URL = STORE_BASE_URL + "order";

}
