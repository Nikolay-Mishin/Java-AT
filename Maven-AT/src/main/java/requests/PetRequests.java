package requests;

import io.restassured.response.Response;
import jdk.jfr.Description;
import models.pet.Pet;
import utils.request.BaseRequests;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static constant.UrlConstants.PET_URL;

public class PetRequests extends BaseRequests<Pet> {

    public PetRequests() {
        baseUrl = PET_URL;
    }

    // запрос создания животного
    @Override
    @Description("Add a new pet to the store")
    public Response post(Pet pet) throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return super.post(pet);
    }

    // запрос получения животного
    @Override
    @Description("Find pet by ID")
    public Response get(Long petId) throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return super.get(petId);
    }

    @Override
    @Description("Update an existing pet")
    public Response put(Pet pet) throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return super.put(pet);
    }

    @Override
    @Description("Deletes a pet")
    public Response delete(Long petId) throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return super.delete(petId);
    }

}
