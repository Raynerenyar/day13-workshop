package sg.edu.nus.iss.app.workshop13.workshop13.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.io.FilenameUtils;

@Component("ioutil")
public class IOUtil {

    private static final Logger logger = LoggerFactory.getLogger(IOUtil.class);

    private static String workinDir = System.getProperty("user.dir");
    private String dataDir;

    public static void createDir(String path) {
        String filePath = FilenameUtils.separatorsToSystem(workinDir + path);
        File dir = new File(filePath);
        Boolean dirIsCreated = dir.mkdirs();
        if (dirIsCreated) {
            String osName = System.getProperty("os.name");

            // for windows, no problem in creating a new directory programmatically
            if (!osName.contains("Windows")) {
                // for other OS, have to set perms
                try {
                    String perm = "rwxrwx---";
                    Set<PosixFilePermission> permissions = PosixFilePermissions.fromString(perm);
                    Files.setPosixFilePermissions(dir.toPath(), permissions);
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    public String getWorkinDir() {
        return workinDir;
    }

    public String getDataDir() {
        return dataDir;
    }

    public void setDataDir(String dataDir) {
        this.dataDir = dataDir;
    }

}
