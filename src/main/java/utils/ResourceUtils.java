package utils;

import java.net.URL;

public class ResourceUtils {

    public static String getResourcePath(String file) {
        String resourcePath = null;
        URL resource = ResourceUtils.class.getClassLoader().getResource(file);
        if (resource != null) {
            resourcePath = resource.getFile();
        } else {
            System.out.println("Resource not found:" + file);
        }
        return resourcePath;
    }
}
