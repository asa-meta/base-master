package com.asa.meta.helpers.filesUtils;

import java.io.File;

public class FileUtils {
    public static boolean checkFile(File file) {
        return file != null && file.exists() && file.isFile();
    }
}
