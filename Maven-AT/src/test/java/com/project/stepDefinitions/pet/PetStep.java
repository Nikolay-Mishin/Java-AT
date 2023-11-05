package com.project.stepDefinitions.pet;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.restassured.response.Response;
import models.pet.Category;
import models.pet.Pet;
import models.pet.TagsItem;
import requests.PetRequests;
import utils.base.*;
import utils.fs.JsonSchema;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import static constant.UrlConstants.PET_URL;
import static java.lang.System.out;
import static org.junit.Assert.assertEquals;

public class PetStep extends Step<PetRequests, Pet> {

    private Long petId;

    @ConstructorProperties({})
    public PetStep() {
        super(new PetRequests(), Pet.class);
    }

    private Response getPet() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        Response resp = req.getPet(petId);
        out.println(resp.getStatusCode());
        return resp;
    }

    private Response createPet(List<List<String>> dataTable)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException, URISyntaxException, ClassNotFoundException, InstantiationException
    {
        Pet pet = new Model<>(modelClass, dataTable, new HashMap<Integer, Class<? extends Model<?>>>(3, 4).values(Category.class, TagsItem.class), PET_URL).get();
        Response resp = req.postPet(pet);
        petId = resp.path("id");
        out.println(resp.getStatusCode());
        out.println(petId);
        //Category category = resp.jsonPath().get("category");
        //out.println(category);
        int categoryId = resp.path("category.id");
        out.println(categoryId);
        Auth.instance(new Token(new JsonSchema().path("auth", "token")));
        Auth.printTokens();
        return resp;
    }

    @Когда("создать животное статус {int}")
    public void createPet(int statusCode, List<List<String>> dataTable)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException, URISyntaxException, ClassNotFoundException, InstantiationException {
        Response resp = createPet(dataTable);
        assertEquals(statusCode, resp.getStatusCode());
    }

    @Тогда("получить животное статус {int}")
    public void getCreatedPet(int statusCode) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        Response resp = getPet();
        assertEquals(statusCode, resp.getStatusCode());
    }

}
