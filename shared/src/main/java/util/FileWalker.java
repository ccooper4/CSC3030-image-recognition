package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileWalker {
    private static final Logger log = LoggerFactory.getLogger(FileWalker.class);

    /**
     * Search a designated file path for files.
     * @param path  The path to search.
     * @return      The list of found files.
     */
    public static List<File> discoverFilesOnPath(String path) {
        List<File> discoveredFiles = null;
        log.info("Walking file path " + path);

        try {
            discoveredFiles = Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .filter(f -> f.toFile().getName().endsWith(".png"))
                    .map(Path::toString).map(File::new)
                    .collect(Collectors.toList());

            log.info("Found " + discoveredFiles.size() + " files in total:");
            discoveredFiles.forEach(f -> log.info(f.getName()));

        } catch (IOException e) {
            log.error("Exception while searching for files", e);
        }

        return discoveredFiles;
    }
}
