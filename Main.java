package com.company;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // write your wonderful and magic code here
        final int minN = 10;                    //минимальное и максимальное значение N.
        final int maxN = 100000;
        final String dictfile = "dict.txt";     //словарь
        final String inputfile = "input.txt";   //Файл_с_текстом
        final String outputfile = "output";     //имя выходного файла
        final String outExt = ".html";          //расширение выходного файла
        final int N;                            //N задается через константу или через параметры командной строки
        final int maxSizeDict = 2097152;        //максимвльный размер словаря, байт
        final int maxSizeInput = 2097152;       //максимвльный размер входного файла с текстом, байт
        final int maxLinesInput = 100000;       //максимальное количество строк входного файла с текстом

        //МОЁ ДОПУЩЕНИЕ для проверки структуры файла_с_текстом
        // и для исключения ситуации когда прийдется делить текст по N(10..100'000) строк,
        // а длинна одного предложения более 10 строк возникнет нерешаемая задача.
        final int maxSentenceLines = 10;        //максимальная длинна одного предложения в строках
        final String resultpath = "RESULT";                //Имя папки для выходных файлов (для удобства)

        //=-=
        Application Java12 = new Application(); //создание экземпляра класса Application
        Java12.run(args); //передаю параметры командной строки
    }//main
}
