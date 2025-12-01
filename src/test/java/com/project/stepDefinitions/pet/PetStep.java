package com.project.stepDefinitions.pet;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertEquals;

import io.cucumber.java.en.*;
import io.restassured.response.Response;

import static org.project.utils.Helper.debug;

import org.project.utils.base.*;

import models.pet.*;
import requests.PetRequests;

public class PetStep extends BaseStep<PetRequests, Pet> {

    @ConstructorProperties({})
    public PetStep() throws ReflectiveOperationException {
        super();
        hashMap = new HashMap<String, Class<?>>("category", "tags").values(Category.class, TagsItem.class);
    }

    @Override
    protected Response post(List<List<String>> dataTable) throws ReflectiveOperationException, IOException, URISyntaxException {
        Response resp = super.post(dataTable);
        int categoryId = resp.path("category.id");
        debug(categoryId);
        return resp;
    }

    @When("создать животное статус {int}")
    public void postPet(int statusCode, List<List<String>> dataTable) throws ReflectiveOperationException, IOException, URISyntaxException {
        assertEquals(statusCode, post(dataTable).getStatusCode());
    }

    @And("получить животное статус {int}")
    public void getPet(int statusCode) throws ReflectiveOperationException, MalformedURLException, URISyntaxException {
        assertEquals(statusCode, get().getStatusCode());
    }

    @And("удалить животное статус {int}")
    public void deletePet(int statusCode) throws ReflectiveOperationException, MalformedURLException, URISyntaxException {
        assertEquals(statusCode, delete().getStatusCode());
    }

}
