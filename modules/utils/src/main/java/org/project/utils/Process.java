package org.project.utils;

import java.awt.*;
import java.beans.ConstructorProperties;
import java.io.*;
import java.util.Arrays;

import org.project.utils.stream.StreamGobbler;

import static java.awt.Desktop.getDesktop;
import static java.awt.Desktop.isDesktopSupported;
import static org.project.utils.Helper.debug;

public class Process {
    public static final Desktop desktop = getDesktop();
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

    public static Process run(String app, String... params) throws IOException {
        return new Process(app, Arrays.toString(params));
    }

    public static void open(String app) {
        try {
            File file = new File(app);

            /* Check if there is support for Desktop or not */
            if(!isDesktopSupported()) {
                debug("not supported");
                return;
            }

            if (file.exists()) {
                debug("Open " + app + "\n");
                desktop.open(file);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            debug("Encountered Exception\n");
            throw new RuntimeException(e);
        }
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
