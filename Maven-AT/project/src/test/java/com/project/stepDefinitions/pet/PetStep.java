package com.project.stepDefinitions.pet;

import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.restassured.response.Response;
import models.pet.Category;
import models.pet.Pet;
import models.pet.TagsItem;
import requests.PetRequests;
import utils.base.HashMap;
import utils.base.Model;
import utils.base.Step;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import static java.lang.System.out;
import static org.junit.Assert.assertEquals;

public class PetStep extends Step<PetRequests, Pet> {

    private Long petId;

    @ConstructorProperties({})
    public PetStep() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {}

    private Response getPet() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        Response resp = req.getPet(petId);
        out.println(resp.getStatusCode());
        return resp;
    }

    private Response postPet(List<List<String>> dataTable) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException, URISyntaxException {
        Pet pet = new Model<>(modelClass, dataTable, new HashMap<String, Class<? extends Model<?>>>("category", "tags").values(Category.class, TagsItem.class)).get();
        Response resp = req.postPet(pet);
        petId = resp.path("id");
        out.println(resp.getStatusCode());
        out.println(petId);
        //int categoryId = resp.jsonPath().get("category.id");
        int categoryId = resp.path("category.id");
        out.println(categoryId);
        return resp;
    }

    private Response deletePet() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        Response resp = req.deletePet(petId);
        out.println(resp.getStatusCode());
        return resp;
    }

    @Когда("создать животное статус {int}")
    public void postPet(int statusCode, List<List<String>> dataTable)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException, URISyntaxException {
        assertEquals(statusCode, postPet(dataTable).getStatusCode());
    }

    @И("получить животное статус {int}")
    public void getPet(int statusCode) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        assertEquals(statusCode, getPet().getStatusCode());
    }

    @И("удалить животное статус {int}")
    public void deletePet(int statusCode) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        assertEquals(statusCode, deletePet().getStatusCode());
    }

}
