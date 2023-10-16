package com.project.stepDefinitions;

import io.cucumber.java.ru.Когда;
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

import static java.lang.System.out;
import static org.junit.Assert.assertEquals;

public class PetStep {

    private final PetRequests petReq;
    private final Pet pet;

    private final List<String> photoUrls = List.of("string");
    private final String name = "doggie";
    private final Long id = 0L;
    private final Category category = new Category("string", 0);
    private final List<TagsItem> tags = List.of(new TagsItem("string", 0));
    private final String status = "available";

    @ConstructorProperties({})
    public PetStep() {
        this.petReq = new PetRequests();
        this.pet = new Pet(this.photoUrls, this.name, this.id, this.category, this.tags, this.status);
    }

    @Когда("получено животное с id {long} статус {int}")
    public void getPetByID(Long arg0, int arg1) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        Response resp = this.petReq.getPet(arg0);
        out.println(resp.getStatusCode());
        assertEquals(arg1, resp.getStatusCode());
    }

    @Когда("получено созданное животное статус {int}")
    public void getCreatedPetByID(int arg0) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        Response newPet = this.petReq.postPet(this.pet);
        out.println(newPet);
        out.println(newPet.getStatusCode());
        Long id = newPet.path("id");
        //int categoryId = newPet.path("category", "id");
        out.println(id);
        //out.println(categoryId);
        getPetByID(id, arg0);
    }

}
