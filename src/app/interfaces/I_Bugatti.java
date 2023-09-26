package app.interfaces;

public interface I_Bugatti extends I_Car {
    String _model = "Bugatti Chiron"; // модель
    int _maxSpeed = 400; // максимальная скорость
    enum _color {
        Blue, Green, Black
    }

    void printInfo();
}
