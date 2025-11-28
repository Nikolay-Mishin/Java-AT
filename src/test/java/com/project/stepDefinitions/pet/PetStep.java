package com.project.stepDefinitions.pet;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
    public PetStep() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        super();
        hashMap = new HashMap<String, Class<?>>("category", "tags").values(Category.class, TagsItem.class);
    }

    @Override
    protected Response post(List<List<String>> dataTable)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException, URISyntaxException {
        Response resp = super.post(dataTable);
        int categoryId = resp.path("category.id");
        debug(categoryId);
        return resp;
    }

    @When("создать животное статус {int}")
    public void postPet(int statusCode, List<List<String>> dataTable)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException, URISyntaxException {
        assertEquals(statusCode, post(dataTable).getStatusCode());
    }

    @And("получить животное статус {int}")
    public void getPet(int statusCode) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        assertEquals(statusCode, get().getStatusCode());
    }

    @And("удалить животное статус {int}")
    public void deletePet(int statusCode) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        assertEquals(statusCode, delete().getStatusCode());
    }

}
