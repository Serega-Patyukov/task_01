package org.example.app.services;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreateFile {

    // Список для сохранения всех текстовых файлов.
    private List<Path> list = new ArrayList<>();

    // Строка для сохранения содержимого со всех текстовых файлов.
    private String str = "";

    // Файл для сохранения содержимого со всех текстовых файлов.
    private File fileResult = new File("src/main/resources/static/file.txt");

    // Абсолютный путь к корневой папке.
    private Path path = Paths.get("");

    public CreateFile(String uri) {
        init(uri);
    }

    private void init(String uri) {

        if ((uri == null) || uri == "") throw new RuntimeException("Абсолютный путь к корневой папке не указан.");

        try {
            path = Paths.get(uri);
        } catch (InvalidPathException e) {
            throw new RuntimeException("Абсолютный путь введен не корректно.");
        }

        if (!path.isAbsolute()) throw new RuntimeException("Указанный путь не является абсолютным.");
    }

    public void main() {
        createList(path);   // Получаем список всех текстовых файлов.
        Collections.sort(list, (o1, o2) -> o1.getFileName().compareTo(o2.getFileName()));   // Сортируем список.
        createStr();   // Заполняем строку содержимым со всех текстовых файлов.
        createFile();   // Сохраняем строку в файл.

        System.out.println("Файл с результатом тут " + fileResult.getAbsolutePath());
    }

    // Получаем список всех текстовых файлов.
    public void createList(Path path) {

        DirectoryStream<Path> paths;

        try {
            paths = Files.newDirectoryStream(path);   // Получаем коллекцию файлов (и поддиректорий) из полученной директории.
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Path p : paths) {   // Проходим по всей коллекции.
            if (Files.isDirectory(p)) createList(p);   // Если очередной элемент является директорией, то передаем ее в метод void createList(Path path).
            if (Files.isRegularFile(p)) {   // Если очередной элемент является файлом, то ...
                // Проверяем файл на принадлежность к текстовым файлам. Если файл текстовый, то добавляем его в список файлов.
                String fileName = p.toString();
                if (fileName.substring(fileName.length() - 3).equalsIgnoreCase("txt")) list.add(p);
            }
        }
    }

    // Заполняем строку содержимым со всех текстовых файлов.
    public void createStr() {
        for (Path path : list) {
            try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
                while (reader.ready()) {
                    str += reader.readLine();   // Сохраняем очередную строку файла в строку для сохранения содержимого со всех текстовых файлов.
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            str += "\n";   // Содержимое каждого нового файла будет сохранено с новой строки.
        }
    }

    // Сохраняем строку в файл.
    public void createFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileResult))) {
            writer.write(str);   // Сохраняем строку с содержимым со всех текстовых файлов в файл.
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Path> getList() {
        return list;
    }

    public void setList(List<Path> list) {
        this.list = list;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public File getFileResult() {
        return fileResult;
    }

    public void setFileResult(File fileResult) {
        this.fileResult = fileResult;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(String uri) {
        init(uri);
    }
}
