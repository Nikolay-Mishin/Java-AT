package org.project.utils;

import static java.awt.Desktop.getDesktop;
import static java.awt.Desktop.isDesktopSupported;

import java.awt.Desktop;
import java.beans.ConstructorProperties;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.project.utils.Helper.debug;

import org.project.utils.stream.GobblerStream;

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
        debug(Arrays.toString(params));
        return new Process(app, Arrays.toString(params));
    }

    public void run(String... params) throws IOException {
        debug(params);
        pb = new ProcessBuilder(params);
        if (inherit) pb = pb.inheritIO();
        p = pb.start();
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
        GobblerStream.stream(p);
    }

    public void stream(PrintStream out) {
        GobblerStream.stream(p, out);
    }

    public void transfer() throws IOException {
        GobblerStream.transfer(p);
    }

    public void transfer(PrintStream out) throws IOException {
        GobblerStream.transfer(p, out);
    }
}
