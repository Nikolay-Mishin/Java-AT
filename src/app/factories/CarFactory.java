package app.factories;

public class CarFactory<T> {

    T instance;

    public CarFactory() {
        CarFactory<T> instance = new CarFactory<>();
        this.instance = (T) instance;
    }

}
