package app;

import static core.libs._Math.random;
import static java.lang.System.out;

import app.interfaces.I_Bugatti;
import app.interfaces.I_Car;

public class Car<T> implements I_Car {
    private T instance;
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
        this.init();
    }

    public void init() {
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
    }

    public void printInfo() {
        out.println("Модель: " + this.model + ". Цвет: " + this.color + ", макс. скорость: " + this.maxSpeed + ", объем двигателя - " + this.engineVolume +
            ", багажника - " + this.trunkVolume + ", салон сделан из " + this.salonMaterial + ", ширина дисков - " + this.wheels +
            ". Была приоберетена в 2018 году господином " + this.ownerLastName);
    }
}
