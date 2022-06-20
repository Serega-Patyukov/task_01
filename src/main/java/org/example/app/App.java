package org.example.app;

import org.example.app.services.CreateFile;

import java.util.Scanner;

public class App  {
    public static void main( String[] args ) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите абсолютный путь к корневой папке: ");
        new CreateFile(scanner.nextLine()).main();
    }
}
