// Java program to invoke method with its name
// using Reflection API

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.lang.System.out;
import static utils.Helper.DefaultCharsetPrinter;

public class Main {

    public void printMessage(String message) {
        out.println("you invoked me with the message: " + message);
    }

    public static void main(String[] args) throws Exception {
        DefaultCharsetPrinter();

        out.println("Invoke method by Name in Java using Reflection!");

        // create class object to get its details
        Main obj = new Main();

        out.println(obj);

        Class<?> classObj = obj.getClass();

        // get method object for "printMessage" function by
        // name
        Method printMessage = classObj.getDeclaredMethod("printMessage", String.class);

        out.println(printMessage);

        try {
            // invoke the function using this class obj
            // pass in the class object
            printMessage.invoke(obj, "hello");
        } catch (InvocationTargetException e) {
            out.println(e.getCause());
        }
    }
}
