// пакет - путь (вложенность папок) до класса
package test;

// импорт статичного свойства out из класса System
import static java.lang.System.out;

// импорт класса Random
import java.util.Random;

import static app.App.*;
import test.java.Employee;

public class Test {

    public Test() throws Exception {
        testTypes();
        testCycles();
        testArithmeticOperators();
        testLogicalOperators();
        testUnaryOperators();
        testComparisonOperators();
        testBitwiseOperators();
        testAssignmentOperators();
        testTernaryOperators();
        testArrayRandom();

        setValue("text");
        setValue(5);

        this.createPersons();

        generateRandomArray();
        createCars();
        createCarsFactory();
    }

    public static void testTypes() {
        // тип данных / название переменной / присвоение значения
        boolean         result              = true;
        char capitalC = 'C';
        String str = "Hello world!";
        byte b = 100;
        short s = 10000;
        int i = 100000;
        long creditCardNumber = 1234_5678_9012_3456L;
        double d2 = 1.234e2;
        float f1 = 123.4f;
        float f2 = 456.7f; // переменная не подсвечена - нигде не используется

        out.println(str);
        out.println(result);
        out.println(capitalC);
        out.println(b);
        out.println(s);
        out.println(i);
        out.println(creditCardNumber);
        out.println(d2);
        out.println(f1);
    }

    public static void testCycles() {
        // вывести
        /* for ([инифциализация счетчика]; [условие]; [изменение счетчика]) {
            // действие
        } */

        // пример цикла for
        for (int i = 0; i < 9; i++) {
            out.println("Число i = " + i);
        }

        // пример while - сначала проверяется условия, потом производится действие
        int count1 = 0;
        while (count1 < 5) {
            out.println(count1);
            count1++; // инкрементация
        }

        // пример do-while - сначала выполняется действие, потом проверяется условие
        int count2 = 0;
        do {
            out.println(count2);
            count2++;
        } while (count2 < 5);
    }

    public static void testArithmeticOperators() {
        // +
        int result1 = 1 + 2;
        out.println("Сложение: " + result1); // + = конкатенация

        // -
        int result2 = 1 - 2;
        out.println("Вычитание: " + result2);

        // *
        int result3 = 1 * 2;
        out.println("Умножение: " + result3);

        // /
        int result4 = 1 / 2;
        out.println("Деление: " + result4);

        // %
        int result5 = 1 % 2;
        out.println("Остаток: " + result5);
    }

    public static void testLogicalOperators() {
        boolean b1 = true;
        boolean b2 = false;

        // && (AND) - выполняется, если оба условия true
        if (b1 && b2) {
            out.println("AND");
        }

        // || (OR) - выполняется, если одно из условий true
        if (b1 || b2) {
            out.println("OR");
        }

        // ! (NOT)
        if (!b2) {
            out.println("NOT");
        }
    }

    public static void testUnaryOperators() {
        // ++ инкремент
        int x = 9;
        x++;
        out.println(x);

        // -- декримент
        int y = 21;
        y--;
        out.println(y);

        // +
        // -
        // !
    }

    public static void testComparisonOperators() {
        int a = 1;
        int b = 2;

        // ==
        boolean comparisonResult = a == b;
        out.println("a == b: " + comparisonResult);

        // !=
        comparisonResult = a != b;
        out.println("a != b: " + comparisonResult);

        // >
        comparisonResult = a > b;
        out.println("a > b: " + comparisonResult);

        // <
        comparisonResult = a < b;
        out.println("a < b: " + comparisonResult);

        // >=
        comparisonResult = a >= b;
        out.println("a >= b: " + comparisonResult);

        // <=
        comparisonResult = a <= b;
        out.println("a <= b: " + comparisonResult);
    }

    public static void testBitwiseOperators() {
        // &
        // |
        // ^
        // ~
        // <<
        // >>
        // >>>
    }

    public static void testAssignmentOperators() {
        // =
        int x = 0;

        // +=
        x += 10; // x = 0 + 10 => x = 10
        out.println(x);

        // -=
        int y = 0;
        y -= 5; // y = 0 - 5 => y = -5
        out.println(y);

        // *=
        int z = 1;
        z *= 5; // z = 1 * 5 => z = 5
        out.println(z);

        // /=
        int r = 5;
        r /= 5; // r = 5 / 5 => r = 1
        out.println(r);

        // %=
        int u = 0;
        u %= 3; // u = 9 % 3 => u = 2; (3 + 3 + 2)
        out.println(u);

        // &=
        // |=
        // ^=
    }

    public static void testTernaryOperators() {
        // ?:
        boolean b = true;
        int i = b ? 1 : 2;
        out.println("?: " + i);
    }

    public static void testArrayRandom() {
        int[] myArray; // объявление массива
        myArray = new int[10]; // создание, выделение памяти для массива  на 10 элементов типа int
        out.println("myArray.length: " + myArray.length); // вывели в консоль длину массива, те количество элементов, которые мы можем поместить в массив

        // настроить работу с рандомными значениями
        Random rand = new Random();
        int rand_int1 = rand.nextInt(1000); // Generate random integer in range  0 to 999
        int rand_int2 = rand.nextInt(5, 9); // Generate random integer in range  5 to 9
        double rand_dub1 = rand.nextDouble(1000); // Generate random double
        double rand_dub2 = rand.nextDouble(1000); // Generate random double
        double rand_g1 = rand.nextGaussian(rand_dub1, rand_dub2); // Generate random Gaussian
        float rand_fl1 = rand.nextFloat(1000); // Generate random float
        long rand_l1 = rand.nextLong(1000); // Generate random long


        out.println("rand_int1: " + rand_int1);
        out.println("rand_int2: " + rand_int2);
        out.println("rand_dub1: " + rand_dub1);
        out.println("rand_dub2: " + rand_dub2);
        out.println("rand_g1: " + rand_g1);
        out.println("rand_fl1: " + rand_fl1);
        out.println("rand_l1: " + rand_l1);

        myArray[0] = 12;
        myArray[1] = rand.nextInt(1000);
        myArray[4] = rand.nextInt(1000);

        for (int i = 0; i < myArray.length; i++) {
            myArray[i] = rand.nextInt(1000); // добавляю рандомные значения в массив
            out.println(myArray[i]); // вывожу значения элементов массива
        }
    }

    public static void setValue(String text) {
        out.println(text);
    }

    public static void setValue(int value) {
        out.println(value);
    }

    public void createPersons() {
        Employee sam = new Employee("Sam", 20);
        sam.display();

        Employee tom = new Employee("Tom", 30);
        tom.display();
    }

}
