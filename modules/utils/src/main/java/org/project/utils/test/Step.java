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
import org.project.utils.test.model.Category;
import org.project.utils.test.model.Model;
import org.project.utils.test.model.TagsItem;

/**
 *
 */
public class Step extends BaseStep<Requests, Model> {

    /**
     *
     * @throws ReflectiveOperationException throws
     */
    @ConstructorProperties({})
    public Step() throws ReflectiveOperationException {
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

    /**
     *
     * @param statusCode int
     * @param id int
     * @throws Exception throws
     */
    public void getOrder(int statusCode, int id) throws Exception {
        req.get(req.get().baseUrl("store/order"));
        assertEquals(statusCode, get(id).getStatusCode());
    }

    /**
     *
     * @param statusCode int
     * @param dataTable List {List {String}}
     * @throws Exception throws
     */
    @When("создать заказ gen статус {int}")
    public void postOrder(int statusCode, List<List<String>> dataTable) throws Exception {
        req.post(req.post().baseUrl("store/order"));
        assertEquals(statusCode, super.post(dataTable).getStatusCode());
    }

    /**
     *
     * @param statusCode int
     * @param dataTable List {List {String}}
     * @throws Exception throws
     */
    @When("создать животное статус {int}")
    public void postPet(int statusCode, List<List<String>> dataTable) throws Exception {
        assertEquals(statusCode, post(dataTable).getStatusCode());
    }

    /**
     *
     * @param statusCode int
     * @throws Exception throws
     */
    @And("получить животное статус {int}")
    public void getPet(int statusCode) throws Exception {
        assertEquals(statusCode, get().getStatusCode());
    }

    /**
     *
     * @param statusCode int
     * @throws Exception throws
     */
    @And("удалить животное статус {int}")
    public void deletePet(int statusCode) throws Exception {
        assertEquals(statusCode, delete().getStatusCode());
    }

}
