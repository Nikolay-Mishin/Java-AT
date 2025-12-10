package org.project.utils.test;

import java.beans.ConstructorProperties;
import java.util.List;

import static org.junit.Assert.assertEquals;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static org.project.utils.Helper.debug;

import org.project.utils.base.BaseStep;
import org.project.utils.base.HashMap;

public class Step extends BaseStep<Requests, Model> {

    @ConstructorProperties({})
    public Step() throws ReflectiveOperationException {
        super();
        hashMap = new HashMap<String, Class<?>>("category", "tags").values(Category.class, TagsItem.class);
    }

    public void getOrder(int statusCode, int id) throws Exception {
        req.get(req.get().baseUrl("store/order"));
        assertEquals(statusCode, get(id).getStatusCode());
    }

    @When("создать заказ gen статус {int}")
    public void postOrder(int statusCode, List<List<String>> dataTable) throws Exception {
        req.post(req.post().baseUrl("store/order"));
        assertEquals(statusCode, super.post(dataTable).getStatusCode());
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
