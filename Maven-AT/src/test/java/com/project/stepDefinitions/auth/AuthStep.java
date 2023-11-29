package com.project.stepDefinitions.auth;

import io.cucumber.java.ru.Когда;
import org.project.utils.auth.AuthToken;
import org.project.utils.json.JsonSchema;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.project.utils.auth.Auth.init;
import static org.project.utils.auth.Auth.printTokens;
import static org.project.utils.base.Register.printRegisterMap;

public class AuthStep {

    @Когда("авторизоваться")
    public void auth()
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException, ClassNotFoundException, InstantiationException
    {
        init(new AuthToken(new JsonSchema().path("auth", "token")));
        printTokens();
        printRegisterMap();
    }

}
