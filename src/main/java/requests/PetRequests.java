package requests;

import models.pet.Pet;
import org.project.utils.request.BaseRequests;

import static constant.UrlConstants.PET_URL;

public class PetRequests extends BaseRequests<Pet> {

    public PetRequests() {
        super(PET_URL);
    }

}
