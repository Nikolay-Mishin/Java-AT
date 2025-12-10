package com.project.stepDefinitions.pet;

import java.beans.ConstructorProperties;
import java.util.List;

import static org.junit.Assert.assertEquals;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static org.project.utils.Helper.debug;

import org.project.utils.base.BaseStep;
import org.project.utils.base.HashMap;

import models.pet.Category;
import models.pet.Pet;
import models.pet.TagsItem;
import requests.PetRequests;

public class PetStep extends BaseStep<PetRequests, Pet> {

    @ConstructorProperties({})
    public PetStep() throws ReflectiveOperationException {
        super();
        hashMap = new HashMap<String, Class<?>>("category", "tags").values(Category.class, TagsItem.class);
    }

    @Override
    protected Response post(List<List<String>> dataTable) throws Exception {
        Response resp = super.post(dataTable);
        int categoryId = resp.path("category.id");
        debug(categoryId);
        return resp;
    }

    @When("создать животное статус {int}")
    public void postPet(int statusCode, List<List<String>> dataTable) throws Exception {
        assertEquals(statusCode, post(dataTable).getStatusCode());
    }

    @And("получить животное статус {int}")
    public void getPet(int statusCode) throws Exception {
        assertEquals(statusCode, get().getStatusCode());
    }

    @And("удалить животное статус {int}")
    public void deletePet(int statusCode) throws Exception {
        assertEquals(statusCode, delete().getStatusCode());
    }

}
