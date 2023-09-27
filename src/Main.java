import static java.lang.System.out;
import java.util.*;

import static core.utils.Math.*;
import app.factories.CarFactory;
import app.models.*;
import app.interfaces.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static void main(String[] args) throws Exception {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        out.println("Hello and welcome!");

        out.println("Псевдослучайное целое число: " + random(10, 75));

        List<Integer> list = generateArray(20, () -> random(0, 20));
        out.println(list);

        for (Integer el : list) {
            out.println("i: " + el);
        }
        /*
        I_Car bugatti = new Car<>();
        Car<I_Bugatti> bugatti2 = new Car<>();
        Car<Bugatti> bugatti3 = new Car<>();
        I_Bugatti bugatti4 = new Bugatti();
        Bugatti bugatti5 = new Bugatti();

        bugatti.printInfo();
        bugatti2.printInfo();
        bugatti3.printInfo();
        bugatti4.printInfo();
        bugatti5.printInfo();
        */

        CarFactory<Bugatti> factory = new CarFactory<>(Bugatti.class);
        Bugatti car = factory.instance;
        I_Bugatti I_car = factory.instance;
        out.println(factory.getInstance(Bugatti.class));
        out.println(factory.instantiate(Bugatti::new));
        car.printInfo();
        car.<I_Bugatti>init();
        out.println(I_car._model);
    }
}
