package requests;

import org.project.utils.request.BaseRequests;

import static constant.Endpoints.PET_URL;

import models.pet.Pet;

public class PetRequests extends BaseRequests<Pet> {
    public PetRequests() throws Exception {
        super(PET_URL);
    }
}
