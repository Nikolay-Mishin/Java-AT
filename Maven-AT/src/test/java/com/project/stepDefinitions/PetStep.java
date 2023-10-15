package com.project.stepDefinitions;

import io.cucumber.java.ru.Когда;
import io.restassured.response.Response;
import models.pet.Category;
import models.pet.Pet;
import models.pet.TagsItem;
import requests.PetRequests;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static java.lang.System.out;
import static org.junit.Assert.assertEquals;

public class PetStep {

    private Pet pet;

    private List<String> photoUrls = List.of("string");
    private String name = "doggie";
    private int id = 0;
    private Category category = new Category("string", 0);
    private List<TagsItem> tags = List.of(new TagsItem("string", 0));
    private String status = "available";

    @ConstructorProperties({})
    public PetStep() {
        this.pet = new Pet(this.photoUrls, this.name, this.id, this.category, this.tags, this.status);
    }

    @Когда("получено животное с id {string} статус {int}")
    public void getPetByID(String arg0, int arg1) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        PetRequests petReq = new PetRequests();
        //Response newPet = petReq.postPet(this.pet);
        //out.println(newPet);
        Response resp = petReq.getPet(arg0);
        Response resp2 = petReq.getPet_2(arg0);
        assertEquals(arg1, resp2.getStatusCode());
    }

}
