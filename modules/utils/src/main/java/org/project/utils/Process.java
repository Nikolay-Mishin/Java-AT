package org.project.utils;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.project.utils.stream.StreamGobbler;

public class Process {
    private ProcessBuilder pb;
    private java.lang.Process p;
    private boolean inherit = false;

    @ConstructorProperties({"inherit", "params"})
    public Process(boolean inherit, String... params) throws IOException {
        this.inherit = inherit;
        run(params);
    }

    @ConstructorProperties({"params"})
    public Process(String... params) throws IOException {
        run(params);
    }

    public void run(String... params) throws IOException {
        pb = new ProcessBuilder(params);
        if (inherit) pb = pb.inheritIO();
        p = pb.start();
    }

    //[ProcessDestroy]
    public void destroy() {
        p.destroy();
        pb = null;
        p = null;
    }

    public ProcessBuilder pb() {
        return pb;
    }

    public java.lang.Process p() {
        return p;
    }

    public InputStream in() {
        return p.getInputStream();
    }

    public OutputStream out() {
        return p.getOutputStream();
    }

    public InputStream err() {
        return p.getErrorStream();
    }

    public void stream() {
        StreamGobbler.stream(p);
    }

    public void stream(PrintStream out) {
        StreamGobbler.stream(p, out);
    }

    public void transfer() throws IOException {
        StreamGobbler.transfer(p);
    }

    public void transfer(PrintStream out) throws IOException {
        StreamGobbler.transfer(p, out);
    }
}
