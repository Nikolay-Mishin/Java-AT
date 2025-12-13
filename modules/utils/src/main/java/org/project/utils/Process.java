package org.project.utils;

import static java.awt.Desktop.getDesktop;
import static java.awt.Desktop.isDesktopSupported;
import static java.lang.Thread.sleep;

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

/**
 *
 */
public class Process {
    /**
     *
     */
    public static final Desktop desktop = getDesktop();
    /**
     *
     */
    private ProcessBuilder pb;
    /**
     *
     */
    private java.lang.Process p;
    /**
     *
     */
    private boolean inherit = false;

    /**
     *
     * @param inherit boolean
     * @param params String[]
     * @throws IOException throws
     */
    @ConstructorProperties({"inherit", "params"})
    public Process(boolean inherit, String... params) throws IOException {
        this.inherit = inherit;
        run(params);
    }

    /**
     *
     * @param params String[]
     * @throws IOException throws
     */
    @ConstructorProperties({"params"})
    public Process(String... params) throws IOException {
        run(params);
    }

    /**
     *
     * @param app String
     * @param params String[]
     * @return Process
     * @throws IOException throws
     */
    public static Process run(String app, String... params) throws IOException {
        debug(Arrays.toString(params));
        return new Process(app, Arrays.toString(params));
    }

    /**
     *
     * @param params String[]
     * @throws IOException throws
     */
    public void run(String... params) throws IOException {
        debug(params);
        pb = new ProcessBuilder(params);
        if (inherit) pb = pb.inheritIO();
        p = pb.start();
    }

    /**
     *
     * @param app String
     * @param sleep boolean
     * @throws InterruptedException throws
     */
    public static void open(String app, boolean sleep) throws InterruptedException {
        if (sleep) open(app, 1000);
        else open(app);
    }

    /**
     *
     * @param app String
     * @param sleep long
     * @throws InterruptedException throws
     */
    public static void open(String app, long sleep) throws InterruptedException {
        sleep(sleep);
        open(app);
    }

    /**
     *
     * @param app String
     */
    public static void open(String app) {
        try {
            File file = new File(app);

            /* Check if there is support for Desktop or not */
            if(!isDesktopSupported()) {
                debug("not supported");
                return;
            }

            if (file.exists()) {
                debug("open: " + app);
                desktop.open(file);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            debug("Encountered Exception\n");
            throw new RuntimeException(e);
        }
    }

    /**
     * ProcessDestroy
     */
    public void destroy() {
        p.destroy();
        pb = null;
        p = null;
    }

    /**
     *
     * @return ProcessBuilder
     */
    public ProcessBuilder pb() {
        return pb;
    }

    /**
     *
     * @return Process
     */
    public java.lang.Process p() {
        return p;
    }

    /**
     *
     * @return InputStream
     */
    public InputStream in() {
        return p.getInputStream();
    }

    /**
     *
     * @return OutputStream
     */
    public OutputStream out() {
        return p.getOutputStream();
    }

    /**
     *
     * @return InputStream
     */
    public InputStream err() {
        return p.getErrorStream();
    }

    /**
     *
     */
    public void stream() {
        GobblerStream.stream(p);
    }

    /**
     *
     * @param out PrintStream
     */
    public void stream(PrintStream out) {
        GobblerStream.stream(p, out);
    }

    /**
     *
     * @throws IOException throws
     */
    public void transfer() throws IOException {
        GobblerStream.transfer(p);
    }

    /**
     *
     * @param out PrintStream
     * @throws IOException throws
     */
    public void transfer(PrintStream out) throws IOException {
        GobblerStream.transfer(p, out);
    }
}
