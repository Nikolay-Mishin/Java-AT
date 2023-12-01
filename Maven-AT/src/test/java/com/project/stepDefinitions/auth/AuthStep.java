package com.project.stepDefinitions.auth;

import io.cucumber.java.ru.Когда;
import models.pet.Pet;
import org.project.utils.auth.Auth;
import org.project.utils.auth.AuthToken;
import org.project.utils.json.JsonSchema;
import requests.PetRequests;
import utils.base.Step;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.project.utils.Helper.debug;
import static org.project.utils.auth.Auth.printTokens;
import static org.project.utils.base.Register.printRegisterMap;
import static org.project.utils.config.Config.debugLvl;

public class AuthStep extends Step<PetRequests, Pet> {

    public AuthStep() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {}

    @Когда("авторизоваться")
    public void auth()
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException, ClassNotFoundException, InstantiationException
    {
        Auth.init(new AuthToken(new JsonSchema().path("auth", "token")));
        printTokens();
        printRegisterMap();
        debug("debugLvl:", debugLvl());
    }

}
