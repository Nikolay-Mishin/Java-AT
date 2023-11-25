package app.models;

import static java.lang.System.out;
import java.lang.reflect.Type;

import app.interfaces.I_Car;

public class Car<T extends I_Car> /*extends Instance<I_Car>*/ implements I_Car {
    String model; // модель
    int maxSpeed; // максимальная скорость
    int wheels; // ширина дисков
    double engineVolume; // объем двигателя
    String color; // цвет
    int yearOfIssue; // год выпуска
    String ownerFirstName; // имя владельца
    String ownerLastName; // фамилия владельца
    long price; // цена
    boolean isNew; // новая или нет
    int placesInTheSalon; // число мест в салоне
    String salonMaterial; // материал салона
    boolean insurance; // застрахована ли
    String manufacturerCountry; // страна-производитель
    int trunkVolume; // объем багажника
    int accelerationTo100km; // разгон до 100 км/час в секундах

    public Car() {
        super();
        this.model = this._model;
        this.maxSpeed = this._maxSpeed;
        this.color = String.valueOf(_color.Black);
        this.accelerationTo100km = 3;
        this.engineVolume = 6.3;
        this.manufacturerCountry = "Italy";
        this.ownerFirstName = "Amigo";
        this.yearOfIssue = 2016;
        this.insurance = true;
        this.price = 2000000;
        this.isNew = false;
        this.placesInTheSalon = 2;

        out.println(this.getClass());
        Type[] interfaces = this.getClass().getGenericInterfaces();
        Type _interface = interfaces[0];
        out.println(_interface);
        out.println(T._model);
        /*
        ParameterizedType eventHandlerInterface = (ParameterizedType) _interface;
        out.println(eventHandlerInterface);
        Type[] types = eventHandlerInterface.getActualTypeArguments();
        out.println(types);
        T dataType = (T) types[0]; // <--String or Date, in your case
        out.println(dataType);
        */

        //Class _class = getEntityClass();
        //out.println("Class: " + _class);
    }

    public <I extends T> void init() {
        out.println(I._model);
    }

    @Override
    public void printInfo() {
        out.println("Модель: " + this.model + ". Цвет: " + this.color + ", макс. скорость: " + this.maxSpeed + ", объем двигателя - " + this.engineVolume +
            ", багажника - " + this.trunkVolume + ", салон сделан из " + this.salonMaterial + ", ширина дисков - " + this.wheels +
            ". Была приоберетена в 2018 году господином " + this.ownerLastName);
    }
}
