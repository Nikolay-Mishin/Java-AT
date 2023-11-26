package utils.fs;

import jdk.jfr.Description;

import java.io.*;
import java.util.Arrays;

import static java.lang.String.join;
import static java.lang.System.out;
import static utils.Helper.isInstance;

public class FS {

    @Description("Generate url path")
    public static String getPath(Object... pathList) {
        out.println(Arrays.toString(pathList));
        return join("/", Arrays.stream(pathList.length == 1 && !(pathList[0] instanceof String) ? (Object[]) pathList[0] : pathList)
            .map(path -> isInstance(path, Object[].class) ? getPath(path) : path.toString())
            .toArray(String[]::new));
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
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
