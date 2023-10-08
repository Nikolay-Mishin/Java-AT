package requests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jdk.jfr.Description;
import models.pet.Pet;
import utils.Request;

import java.lang.reflect.InvocationTargetException;

import static constant.UrlConstants.PET_URL;
import static io.restassured.RestAssured.given;
import static utils.constant.RequestConstants.METHOD.POST;

public class PetRequests {

    // запрос создания животного
    @Description("Add a new pet to the store")
    public Response postPet(Pet pet) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Request request = new Request(POST, PET_URL);
        request.getEndpoint();
        request.getUrl();
        request.getMethod();
        return request
            .setBody(pet)
            .getResponse();
    }

    @Description("Add a new pet to the store")
    public Response postPet_2(Pet pet) {
        return given()
            .contentType(ContentType.JSON)
            .body(pet)
            .when()
            .post(PET_URL)
            .andReturn();
    }

    @Description("Update an existing pet")
    public Response updatePet(Pet pet) {
        return given()
            .contentType(ContentType.JSON)
            .body(pet)
            .when()
            .put(PET_URL)
            .andReturn();
    }

    @Description("Find pet by ID")
    public Response getPet(String petId) {
        return given()
            .contentType(ContentType.JSON)
            .when()
            .get(PET_URL + "/" + petId)
            .andReturn();
    }

    @Description("Deletes a pet")
    public Response deletePet(String petId) {
        return given()
            .contentType(ContentType.JSON)
            .when()
            .delete(PET_URL + "/"  + petId)
            .andReturn();
    }

}
