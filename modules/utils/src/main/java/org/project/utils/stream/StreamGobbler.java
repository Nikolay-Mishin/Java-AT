package org.project.utils.stream;

import java.beans.ConstructorProperties;
import java.io.*;

public class StreamGobbler extends Thread {
    private InputStream in;
    private PrintStream out;

    @ConstructorProperties({"in", "out"})
    public StreamGobbler(InputStream in, PrintStream out) {
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
                out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stream(Process p, PrintStream out) {
        StreamGobbler pOut = new StreamGobbler(p.getInputStream(), out);
        StreamGobbler err = new StreamGobbler(p.getErrorStream(), out);
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
