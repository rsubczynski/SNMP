package modules;

import model.VectorModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class FileManager {

    private static FileManager fileManagerInstance = null;

    private List<VectorModel> testObjectList;

    private FileManager() {
        testObjectList = convertToClass(loadFileFromResource());
    }

    public static FileManager getFileManagerInstance() {
        if(fileManagerInstance == null){
            fileManagerInstance = new FileManager();
        }
        return fileManagerInstance;

    }

    private InputStreamReader loadFileFromResource() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("Maple_Oak.txt");
        return new InputStreamReader(Objects.requireNonNull(is));
    }

    private List<VectorModel> convertToClass(InputStreamReader isReader) {
        String textFromFile = new BufferedReader(isReader)
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));

        return Arrays.stream(textFromFile.split("\n"))
                .map(s -> new VectorModel(Arrays.stream(s.split(",")).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    public List<VectorModel> getAllObjectList() {
        return testObjectList;
    }

}
