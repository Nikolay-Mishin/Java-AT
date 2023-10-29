package requests;

import io.restassured.response.Response;
import jdk.jfr.Description;
import models.pet.Pet;
import utils.Request;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static constant.UrlConstants.PET_URL;
import static utils.constant.RequestConstants.METHOD.*;

public class PetRequests {

    // запрос создания животного
    @Description("Add a new pet to the store")
    public Response postPet(Pet pet) throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return new Request(POST, PET_URL)
            .body(pet)
            .response();
    }

    // запрос получения животного
    @Description("Find pet by ID")
    public Response getPet(Long petId) throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Request request = new Request(GET, PET_URL, petId);
        //request.print();
        return request.response();
    }

    @Description("Update an existing pet")
    public Response putPet(Pet pet) throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return new Request(PUT, PET_URL)
            .body(pet)
            .response();
    }

    @Description("Deletes a pet")
    public Response deletePet(Long petId) throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return new Request(DELETE, PET_URL, petId).response();
    }

}
