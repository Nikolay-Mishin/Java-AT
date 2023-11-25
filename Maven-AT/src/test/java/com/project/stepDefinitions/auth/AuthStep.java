package com.project.stepDefinitions.auth;

import io.cucumber.java.ru.Когда;
import utils.base.Auth;
import utils.fs.JsonSchema;
import utils.tokens.AuthToken;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static utils.Register.printRegisterMap;

public class AuthStep {

    private void auth()
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException, ClassNotFoundException, InstantiationException
    {
        Auth.instance(new AuthToken(new JsonSchema().path("auth", "token")));
        Auth.printTokens();
        printRegisterMap();
    }

    @Когда("авторизоваться")
    public void createPet() throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        auth();
    }

}
