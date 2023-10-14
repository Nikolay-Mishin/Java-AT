package com.project.stepDefinitions;

import io.cucumber.java.ru.Когда;
import io.restassured.response.Response;
import requests.PetRequests;

import static org.junit.Assert.assertEquals;

public class PetStep {
    @Когда("получено животное с id {string} статус {int}")
    public void getPetByID(String arg0, int arg1) {
        Response resp = new PetRequests().getPet(arg0);
        assertEquals(arg1, resp.getStatusCode());
    }

}
