package com.project.stepDefinitions.auth;

import io.cucumber.java.ru.Когда;
import utils.auth.Auth;
import utils.json.JsonSchema;
import utils.auth.AuthToken;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static utils.base.Register.printRegisterMap;

public class AuthStep {

    @Когда("авторизоваться")
    public void auth()
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException, ClassNotFoundException, InstantiationException
    {
        Auth.instance(new AuthToken(new JsonSchema().path("auth", "token")));
        Auth.printTokens();
        printRegisterMap();
    }

}
