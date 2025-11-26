package com.project.stepDefinitions.auth;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import io.cucumber.java.en.*;

import static org.project.utils.auth.Auth.printTokens;
import static org.project.utils.base.Register.printRegisterMap;

import org.project.utils.auth.Auth;
import org.project.utils.base.BaseStep;

import static config.WebConfig.BASE_CONFIG;

import pojo.json.auth.Token;
import requests.auth.AuthRequests;

public class AuthStep extends BaseStep<AuthRequests, Token> {

    @ConstructorProperties({})
    public AuthStep() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        super(BASE_CONFIG);
    }

    @When("авторизоваться")
    public void auth()
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException, ClassNotFoundException, InstantiationException, NoSuchFieldException
    {
        Auth.init("auth/token");
        printTokens();
        printRegisterMap();
    }

}
