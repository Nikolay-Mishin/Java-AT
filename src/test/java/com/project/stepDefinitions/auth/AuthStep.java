package com.project.stepDefinitions.auth;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;

import io.cucumber.java.en.*;

import static org.project.utils.auth.Auth.printTokens;
import static org.project.utils.base.Register.printRegisterMap;

import org.project.utils.auth.Auth;
import org.project.utils.base.BaseStep;

import static org.project.utils.config.TestConfig.config;

import pojo.json.auth.Token;
import requests.auth.AuthRequests;

public class AuthStep extends BaseStep<AuthRequests, Token> {

    @ConstructorProperties({})
    public AuthStep() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    }

    @When("авторизоваться")
    public void auth() throws Exception {
        Auth.init(config().getAuth());
        printTokens();
        printRegisterMap();
    }

}
