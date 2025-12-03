package org.project.utils.stream;

import java.beans.ConstructorProperties;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.InputStream;

import static org.project.utils.Helper.debug;

public class GobblerStream extends Thread {
    private InputStream in;
    private PrintStream out;

    @ConstructorProperties({"in", "out"})
    public GobblerStream(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    public InputStream in() {
        return in;
    }

    public PrintStream out() {
        return out;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = input.readLine()) != null)
                debug(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stream(Process p, PrintStream out) {
        GobblerStream pOut = new GobblerStream(p.getInputStream(), out);
        GobblerStream err = new GobblerStream(p.getErrorStream(), out);
        pOut.start();
        err.start();
    }

    public static void stream(Process p) {
        stream(p, System.out);
    }

    public static void stream(org.project.utils.Process p, PrintStream out) {
        stream(p.p(), out);
    }

    public static void stream(org.project.utils.Process p) {
        stream(p, System.out);
    }

    public static void transfer(Process p, PrintStream out) throws IOException {
        p.getInputStream().transferTo(out);
        p.getErrorStream().transferTo(out);
    }

    public static void transfer(Process p) throws IOException {
        transfer(p, System.out);
    }

    public static void transfer(org.project.utils.Process p, PrintStream out) throws IOException {
        transfer(p.p(), out);
    }

    public static void transfer(org.project.utils.Process p) throws IOException {
        transfer(p.p());
    }

}
