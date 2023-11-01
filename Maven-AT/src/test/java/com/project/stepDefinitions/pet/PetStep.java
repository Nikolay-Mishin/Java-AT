package com.project.stepDefinitions.pet;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.restassured.response.Response;
import models.pet.Category;
import models.pet.Pet;
import models.pet.TagsItem;
import requests.PetRequests;
import utils.base.HashMap;
import utils.base.Model;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import static config.WebConfig.BASE_CONFIG;
import static constant.UrlConstants.PET_URL;
import static java.lang.System.out;
import static org.junit.Assert.assertEquals;
import static utils.Reflection.getClassSimpleName;
import static utils.constant.RequestConstants.METHOD_LOWER_CASE.post;
import static utils.fs.FS.getPath;
import static utils.fs.JsonSchema.getJsonSchemaPath;

public class PetStep {

    private final PetRequests petReq;
    private Long petId;
    private final Class<Pet> clazz = Pet.class;
    private final String jsonSchemaPath;

    @ConstructorProperties({})
    public PetStep() {
        petReq = new PetRequests();
        jsonSchemaPath = getJsonSchemaPath(PET_URL, post + getClassSimpleName(clazz));
    }

    private Response getPet() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        Response resp = petReq.getPet(petId);
        out.println(resp.getStatusCode());
        return resp;
    }

    private Response createPet(List<List<String>> dataTable) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException, URISyntaxException {
        Pet pet = new Model<>(clazz, dataTable, new HashMap<Integer, Class<?>>(3, 4).values(Category.class, TagsItem.class), jsonSchemaPath).get();
        Response resp = petReq.postPet(pet);
        petId = resp.path("id");
        out.println(resp.getStatusCode());
        out.println(petId);
        //Category category = resp.jsonPath().get("category");
        //out.println(category);
        int categoryId = resp.path("category.id");
        out.println(categoryId);
        return resp;
    }

    @Когда("создать животное статус {int}")
    public void createPet(int statusCode, List<List<String>> dataTable)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException, URISyntaxException
    {
        Response resp = createPet(dataTable);
        assertEquals(statusCode, resp.getStatusCode());
    }

    @Тогда("получить животное статус {int}")
    public void getCreatedPet(int statusCode) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        Response resp = getPet();
        assertEquals(statusCode, resp.getStatusCode());
    }

}
