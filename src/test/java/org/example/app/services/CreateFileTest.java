package org.example.app.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateFileTest {

    @Test
    public void initTest() {

        String test = "********";

        try {
            new CreateFile("");
        } catch (RuntimeException e) {
            test = e.getMessage();
        }
        Assertions.assertTrue(test.equals("Абсолютный путь к корневой папке не указан."));

        try {
            new CreateFile(null);
        } catch (RuntimeException e) {
            test = e.getMessage();
        }
        Assertions.assertTrue(test.equals("Абсолютный путь к корневой папке не указан."));

        try {
            new CreateFile("// dfbdf");
        } catch (RuntimeException e) {
            test = e.getMessage();
        }
        Assertions.assertTrue(test.equals("Абсолютный путь введен не корректно."));

        try {
            new CreateFile("dfbdf");
        } catch (RuntimeException e) {
            test = e.getMessage();
        }
        Assertions.assertTrue(test.equals("Указанный путь не является абсолютным."));
    }

    @Test
    public void createListTest() {

        // Абсолютный путь, который может указать пользователь.
        Path absolutePath = Paths.get("src/test/resources/static/testDir").toAbsolutePath();

        // Тестовый список всех текстовых файлов.
        List<Path> listTest = new ArrayList<>(Arrays.asList(
                Paths.get("src/test/resources/static/testDir/gdgred/a.txt").toAbsolutePath(),
                Paths.get("src/test/resources/static/testDir/b.TXT").toAbsolutePath(),
                Paths.get("src/test/resources/static/testDir/dtgyhdfgtdhj/c.txt").toAbsolutePath(),
                Paths.get("src/test/resources/static/testDir/gdgred/fk/d.txt").toAbsolutePath(),
                Paths.get("src/test/resources/static/testDir/E.txt").toAbsolutePath()
        ));

        CreateFile createFile = new CreateFile(absolutePath.toString());
        createFile.main();

        // Сравниваем тестовый список с рабочим.
        for (int i = 0; i < createFile.getList().size(); i++) {
            Assertions.assertTrue(listTest.get(i).toString().equals(createFile.getList().get(i).toString()));
        }
    }

    @Test
    public void createStrTest() {

        // Абсолютный путь, который может указать пользователь.
        Path absolutePath = Paths.get("src/test/resources/static/testDir").toAbsolutePath();

        // Тестовая строка которая должна получится в результирующем файле.
        String testStr = "a\n" +
                "bb\n" +
                "ccc\n" +
                "dddd\n" +
                "eeeee\n";

        CreateFile createFile = new CreateFile(absolutePath.toString());
        createFile.main();

        // Сравниваем тестовую строку с рабочей.
        Assertions.assertTrue(testStr.equals(createFile.getStr()));
    }

    @Test
    public void createFileTest() {

        // Абсолютный путь, который может указать пользователь.
        Path absolutePath = Paths.get("src/test/resources/static/testDir").toAbsolutePath();

        // По этому пути лежит результирующий файл.
        Path pathFile = Paths.get("src/main/resources/static/file.txt").toAbsolutePath();

        // Тестовая строка которая должна получится в результирующем файле.
        String testStr = "test";

        CreateFile createFile = new CreateFile(absolutePath.toString());
        createFile.setStr(testStr);   // Тестовая строка.
        createFile.createFile();   // Записываем тестовую строку в результирующий файл.

        // Проверяем на существование результирующего файла.
        Assertions.assertTrue(Files.exists(pathFile));

        // Считываем строку из записанного результирующего файла.
        String strResult = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(pathFile.toFile()))) {
            while (reader.ready()) {
                strResult += reader.readLine();   // Сохраняем очередную строку результирующего файла в результирующую тестовую строку.
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Сравниваем тестовую строку с рабочей.
        Assertions.assertTrue(testStr.equals(strResult));
    }

}
