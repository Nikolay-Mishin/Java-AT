package app;

import app.interfaces.I_Bugatti;
import app.interfaces.I_Car;

public class Bugatti extends Car<Bugatti> implements I_Bugatti {

    public Bugatti() {
        this.init();
    }

}
