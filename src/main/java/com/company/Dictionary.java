package main.java.com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jiraff537
 */
public class Dictionary extends Java12abstract {


//    OVERRIDE  TRY delete it later //=============QQQ========================================

    //@Override
    public void setFilename(String filename,String codepage) {
        this.filename = filename;
        this.codepage = codepage;
    }

//    OVERRIDE  TRY delete it later //================QQQ=====================================


    //проверка структуры файла
    //содержит произвольное количество строк, каждая из которых содержит ровно одно слово.
    void checkStructure(String pattern) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: файл " + filename + " отсутствует!");
            System.exit(537);
        }
        String line;
        try {
            if (reader.readLine() == null) {
                System.out.println("Ошибка: Файл " + filename + " пуст!");
                System.exit(537);
            }
            while ((line = reader.readLine()) != null) {
                Pattern p = Pattern.compile(pattern); //использую регулярные выражения
                Matcher m = p.matcher(line);
                if (m.matches()) {
                    // если строка только из букв
                } else {
                    // если строка содержит не только буквы
                    System.out.println("Ошибка: файл " + filename + " должен содержать в каждой строке ровно одно слово!");
                    System.exit(537);
                }
            }
        } catch (IOException e) {
            System.out.println("Файл " + filename + " отсутствует!");
            System.exit(537);
        }
    }

    //чтение данных из файла в память целиком
    Set<String> readAll() {
        Set<String> mySet = new HashSet<>();
        try {
            mySet.addAll(Files.readAllLines(Paths.get(filename), Charset.defaultCharset()));
        } catch (IOException e) {
            System.out.println("Файл " + filename + " отсутствует!");
            System.exit(537);
        }
        return mySet;

    }
}
