package javapost.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    public static String readFile(String fileName) throws IOException {
        String data = "[]";
        File file = new File(fileName);
        if(file.exists()){
             data = new String(Files.readAllBytes(Paths.get(fileName)));
        }
        return data;
    }

    public static void writeFile(String content, String fileName) throws IOException {
        new File(fileName).createNewFile();
        byte[] strToBytes = content.getBytes();
        Path path = Paths.get(fileName);
        Files.write(path, strToBytes);
    }

}
