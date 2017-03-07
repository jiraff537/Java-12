package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by jiraff537
 */
public abstract class Java12abstract { //абстрактный класс родитель для Text и Dictionary в него поместил их общие черты которые заметил в ТЗ
    String filename;

    //метод присвайт имя файла
    public void setFilename(String filename) {
        this.filename = filename;
    }

    //метод возвращает текущее имя файла
    public String getFilename() {
        return filename;
    }

    //Проверка существования файла this.getFilename
    public void checkFileExist() {
        if (!Files.exists(Paths.get(filename))) {
            System.out.println("Ошибка: файл " + this.getFilename() + " не найден");
            System.exit(537);
        }
    }

    //Проверяю что размер файла не больше %size%, отображает сообщение об ошибке
    public void checkBiggerThan(int size) {

        try {
            if (Files.size(Paths.get(filename)) > size) {
                System.out.println("Ошибка: размер файла " + this.getFilename() + " превышает " + size + " байт");
                System.exit(537);
            }
        } catch (IOException e) {
            System.out.println("Ошибка: файл " + this.getFilename() + " не найден");
            System.exit(537);
        }
    }
}
