package app;

import static java.lang.System.out;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static core.utils.Math.*;
import app.factories.CarFactory;
import app.models.*;
import app.interfaces.*;

public class App {

    public App() {

    }

    public static void generateRandomArray() throws Exception {
        out.println("Hello and welcome!");

        out.println("Псевдослучайное целое число: " + random(10, 75));

        List<Integer> list = generateArray(20, () -> random(0, 20));
        out.println(list);

        for (Integer el : list) {
            out.println("i: " + el);
        }
    }

    public static void createCars() {
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
    }

    public static void createCarsFactory() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
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
