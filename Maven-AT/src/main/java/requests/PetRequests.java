package requests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jdk.jfr.Description;
import models.pet.Pet;
import utils.Request;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;

import static config.ApiConfig.getRequestSpec;
import static constant.UrlConstants.PET_URL;
import static io.restassured.RestAssured.given;
import static utils.constant.RequestConstants.METHOD.*;

public class PetRequests {

    private final RequestSpecification spec;

    @ConstructorProperties({})
    public PetRequests() {
        this.spec = getRequestSpec();
    }

    // запрос создания животного
    @Description("Add a new pet to the store")
    public Response postPet_2(Pet pet) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Request request = new Request(POST, PET_URL);
        request.print();
        return request
            .setBody(pet)
            .getResponse();
    }

    @Description("Find pet by ID")
    public Response getPet_2(String petId) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Request request = new Request(GET, PET_URL + petId);
        //request.print();
        request.printFullPath();
        return request.getResponse();
    }

    @Description("Add a new pet to the store")
    public Response postPet(Pet pet) {
        return given(this.spec)
            .contentType(ContentType.JSON)
            .body(pet)
            .when()
            .post(PET_URL)
            .andReturn();
    }

    @Description("Update an existing pet")
    public Response updatePet(Pet pet) {
        return given(this.spec)
            .contentType(ContentType.JSON)
            .body(pet)
            .when()
            .put(PET_URL)
            .andReturn();
    }

    @Description("Find pet by ID")
    public Response getPet(String petId) {
        return given(this.spec)
            .contentType(ContentType.JSON)
            .when()
            .get(PET_URL /*+ "/"*/ + petId)
            .andReturn();
    }

    @Description("Deletes a pet")
    public Response deletePet(String petId) {
        return given(this.spec)
            .contentType(ContentType.JSON)
            .when()
            .delete(PET_URL /*+ "/"*/  + petId)
            .andReturn();
    }

}
