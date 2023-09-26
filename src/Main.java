import static java.lang.System.out;
import java.util.*;
import static core.libs._Math.*;

import app.Bugatti;
import app.Car;
import app.interfaces.I_Bugatti;
import app.interfaces.I_Car;
import core.libs._Math;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static void main(String[] args) throws Exception {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        out.println("Hello and welcome!");

        out.println("Псевдослучайное целое число: " + random(10, 75));

        Car<I_Car> bugatti = new Car<>();
        Car<I_Bugatti> bugatti2 = new Car<>();
        I_Bugatti bugatti3 = new Bugatti();

        bugatti.printInfo();
        bugatti2.printInfo();
        bugatti3.printInfo();

        List<Integer> list = _Math.<Integer>generateArray(20, () -> { return random(0, 20); });
        out.println(list);

        for (Integer el : list) {
            out.println("i: " + el);
        }
    }
}
