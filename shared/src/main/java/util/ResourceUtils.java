package util;

import java.net.URL;

public class ResourceUtils {

    public static String getResourcePathAsString(String file) {
        String resourcePath = null;
        URL resource = getResourcePathAsURL(file);
        if (resource != null) {
            resourcePath = resource.getFile();
        } else {
            System.out.println("Resource not found:" + file);
        }
        return resourcePath;
    }

    public static URL getResourcePathAsURL(String file) {
        return ResourceUtils.class.getClassLoader().getResource(file);
    }


}
