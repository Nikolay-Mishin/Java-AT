package com.project.stepDefinitions.pet;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.restassured.response.Response;
import models.pet.Category;
import models.pet.Pet;
import models.pet.TagsItem;
import requests.PetRequests;
import utils.base.Model;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.System.out;
import static org.junit.Assert.assertEquals;

public class PetStep {

    private final PetRequests petReq;
    private Pet pet;
    private Long petId;

    @ConstructorProperties({})
    public PetStep() {
        petReq = new PetRequests();
    }

    private Response getPet() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        Response resp = petReq.getPet(petId);
        out.println(resp.getStatusCode());
        return resp;
    }

    private Response createPet(List<List<String>> dataTable) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        HashMap<Integer, Class<? extends Model>> hashMap = new utils.base.HashMap<Integer, Class<? extends Model>>(3, 4).values(Category.class, TagsItem.class);
        pet = Pet.getModel(Pet.class, dataTable, hashMap);
        out.println(pet);
        pet = Pet.builder()
            .photoUrls(List.of(dataTable.get(0).get(0)))
            .name(dataTable.get(1).get(0))
            .id(parseLong(dataTable.get(2).get(0)))
            .category(Category.builder()
                .name(dataTable.get(3).get(0))
                .id(parseInt(dataTable.get(3).get(1)))
                .build())
            .tags(List.of(TagsItem.builder()
                .name(dataTable.get(4).get(0))
                .id(parseInt(dataTable.get(4).get(1)))
                .build()))
            .status(dataTable.get(5).get(0))
            .petId(parseInt(dataTable.get(6).get(0)))
            .bool(parseBoolean(dataTable.get(7).get(0)))
            .build();
        out.println(pet);
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
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException
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
