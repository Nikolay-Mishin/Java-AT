package com.project.stepDefinitions.pet;

import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.restassured.response.Response;
import models.pet.Category;
import models.pet.Pet;
import models.pet.TagsItem;
import org.project.utils.base.BaseStep;
import org.project.utils.base.HashMap;
import requests.PetRequests;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import static config.WebConfig.BASE_CONFIG;
import static java.lang.System.out;
import static org.junit.Assert.assertEquals;

public class PetStep extends BaseStep<PetRequests, Pet> {

    @ConstructorProperties({})
    public PetStep() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        super(BASE_CONFIG);
        hashMap = new HashMap<String, Class<?>>("category", "tags").values(Category.class, TagsItem.class);
    }

    @Override
    protected Response post(List<List<String>> dataTable)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException, URISyntaxException {
        Response resp = super.post(dataTable);
        int categoryId = resp.path("category.id");
        out.println(categoryId);
        return resp;
    }

    @Когда("создать животное статус {int}")
    public void postPet(int statusCode, List<List<String>> dataTable)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException, URISyntaxException {
        assertEquals(statusCode, post(dataTable).getStatusCode());
    }

    @И("получить животное статус {int}")
    public void getPet(int statusCode) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        assertEquals(statusCode, get().getStatusCode());
    }

    @И("удалить животное статус {int}")
    public void deletePet(int statusCode) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        assertEquals(statusCode, delete().getStatusCode());
    }

}
