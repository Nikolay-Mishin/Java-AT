package utils.fs;

import jdk.jfr.Description;

import java.io.*;
import java.util.Arrays;

import static java.lang.System.out;

public class FS {

    @Description("Generate url path")
    public static String getPath(Object... pathList) {
        return String.join("/", Arrays.stream(pathList).map(Object::toString).toArray(String[]::new));
    }

    private static String readFromInputStream(InputStream inputStream)
        throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                 = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    public static String readFile(String path) throws IOException {
        FileInputStream inputStream = new FileInputStream(path);
        String data = readFromInputStream(inputStream);
        out.println(data);
        return data;
    }

}
