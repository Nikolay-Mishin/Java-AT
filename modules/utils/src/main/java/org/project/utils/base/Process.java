package org.project.utils.base;

import java.beans.ConstructorProperties;
import java.io.IOException;

public class Process {
    private ProcessBuilder processBuilder;
    private java.lang.Process process;

    @ConstructorProperties({})
    public Process(String... params) throws IOException {
        this.processBuilder = new ProcessBuilder(params);
        this.process = this.processBuilder.start();
    }

    //[ProcessDestroy]
    public void destroy() {
        this.process.destroy();
        this.processBuilder = null;
        this.process = null;
    }

    public ProcessBuilder processBuilder() {
        return this.processBuilder;
    }

    public java.lang.Process process() {
        return this.process;
    }
}
