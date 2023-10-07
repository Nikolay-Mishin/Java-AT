package app.test.java;

import static java.lang.System.out;

public class Person {

    // модификатор доступа [default] - доступ в рамках класса
    String name = "Name";
    // модификатор доступа [private] - доступ в рамках класса
    private int age = 30;

    // инкапсуляция - получение доступа к приватным свойствам класса через публичный метод
    // Главная страница

    // меню - приватное свойство
    // добавить в меню - будличная функция

    String getName() {
        out.println(name);
        return name;
    }

    // модификатор доступа [protected] - доступ в рамках класса и его потомков
    protected void setName(String name) {
        this.name = name;
    }

    String getAge() {
        out.println(name);
        return name;
    }

    protected void setAge(int age) {
        this.age = age;
    }

    // модификатор доступа [public] - доступ в любом классе
    public void display() {
        out.println("Имя: " + this.name + ", возраст: " + this.age);
    }
}
