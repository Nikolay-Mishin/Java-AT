package requests;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.project.utils.request.BaseRequests;

import static constant.UrlConstants.PET_URL;

import models.pet.Pet;

public class PetRequests extends BaseRequests<Pet> {
    public PetRequests() throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        super(PET_URL);
    }
}
