package com.project.stepDefinitions.auth;

import io.cucumber.java.ru.Когда;
import org.project.utils.auth.Auth;
import org.project.utils.auth.AuthToken;
import org.project.utils.base.BaseStep;
import org.project.utils.json.JsonSchema;
import pojo.json.auth.Token;
import requests.auth.AuthRequests;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static config.WebConfig.BASE_CONFIG;
import static org.project.utils.auth.Auth.printTokens;
import static org.project.utils.base.Register.printRegisterMap;

public class AuthStep extends BaseStep<AuthRequests, Token> {

    @ConstructorProperties({})
    public AuthStep() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        super(BASE_CONFIG);
    }

    @Когда("авторизоваться")
    public void auth()
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException, ClassNotFoundException, InstantiationException
    {
        Auth.init(new AuthToken(new JsonSchema().path("auth", "token")));
        printTokens();
        printRegisterMap();
    }

}
