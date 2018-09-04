package com.asa.meta.helpers.files;

import java.io.File;

public class FileUtils {
    public static boolean checkFile(File file) {
        return file != null && file.exists() && file.isFile();
    }
}
