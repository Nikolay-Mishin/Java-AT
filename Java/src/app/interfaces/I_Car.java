package app.interfaces;

public interface I_Car {
    String _model = "Bugatti Veyron"; // модель
    int _maxSpeed = 407; // максимальная скорость
    enum _color {
        Blue, Green, Black
    }

    void printInfo();
}
