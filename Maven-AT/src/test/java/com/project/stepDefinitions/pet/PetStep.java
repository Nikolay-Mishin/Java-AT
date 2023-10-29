package com.project.stepDefinitions.pet;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.restassured.response.Response;
import models.pet.Category;
import models.pet.Pet;
import models.pet.TagsItem;
import requests.PetRequests;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

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

    private Response сreatePet(List<List<String>> dataTable) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        out.println(dataTable.get(2).get(0));
        out.println(parseLong(dataTable.get(2).get(1)));
        pet = Pet.builder()
            .photoUrls(List.of(dataTable.get(0).get(1)))
            .name(dataTable.get(1).get(1))
            .id(parseLong(dataTable.get(2).get(1)))
            .category(new Category(dataTable.get(3).get(1), parseInt(dataTable.get(3).get(2))))
            .tags(List.of(new TagsItem(dataTable.get(4).get(1), parseInt(dataTable.get(4).get(2)))))
            .status(dataTable.get(5).get(1))
            .build();
        Response resp = petReq.postPet(pet);
        petId = resp.path("id");
        out.println(petId);
        //Category category = resp.jsonPath().get("category");
        //out.println(category);
        int categoryId = resp.path("category.id");
        out.println(categoryId);
        out.println(resp.getStatusCode());
        return resp;
    }

    @Когда("создать животное статус {int}")
    public void сreatePet(int statusCode, List<List<String>> dataTable) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        Response resp = сreatePet(dataTable);
        assertEquals(statusCode, resp.getStatusCode());
    }

    @Тогда("получить животное статус {int}")
    public void getCreatedPet(int statusCode) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        Response resp = getPet();
        assertEquals(statusCode, resp.getStatusCode());
    }

}
