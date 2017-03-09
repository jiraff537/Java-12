package main.java.com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by jiraff537
 */
public class Application {

    //приватный метод получает из параметра командой строки N проверяет на ошибки ввода неверного значения
    private int CheckNInRange(String[] args, int min, int max) {
        //получаю N из коммандной строки и проверяю на минимальное и максимальное значение, если ошибка сообщаю об этом.
        int N;
        try {
            if (Integer.parseInt(args[0]) >= min && Integer.parseInt(args[0]) <= max) {
                N = Integer.parseInt(args[0]);
                //System.out.println(N);
                return N;
            } else {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            {   // обнаружил ошибку, действую:
                // Остановлю работу программы, намекну пользователю как правильно "загрузить белье в нашу стиральную машинку"
                System.out.println("Ошибка: введенный вами параметр командной строки N не число находящееся в диаппазоне " + min + "..." + max + ".");
                System.exit(537);
            }
        }
        return 0;
    }

    //основной метод работы приложения
    public void run(String[] args) {
        //основное тело программы ===============================================================================================
        //константы (возможно их перенесу в отдельный ini файл...)
        //из задания:
        final int minN = 10;                    //минимальное и максимальное значение N.
        final int maxN = 100000;
        final String dictfile = "dict.txt";     //словарь
        final String inputfile = "input.txt";   //Файл_с_текстом
        final String outputfile = "output";     //имя выходного файла
        final String outext = ".html";          //расширение выходного файла
        final int N;                            //N задается через параметр командной строки
        final int maxSizeDict = 2097152;//2Mb   //максимвльный размер словаря, байт
        final int maxSizeInput = 2097152;//2Mb  //максимвльный размер входного файла с текстом, байт
        final int maxLinesInput = 100000;       //максимальное количество строк входного файла с текстом
        final String insBefore = "<i><b>";      //тег для выделения текста bold+italic
        final String insAfter = "</b></i>";     //закрывающий тег выделения текста bold+italic
        // МОЁ ДОПУЩЕНИЕ
        //для проверки структуры файла_с_текстом и для исключения ситуации когда прийдется делить текст по N(10..100'000) строк,
        // а длинна одного предложения более 10 строк возникнет логический вакуум в решении задания.
        final int maxSentenceLines = minN;        //максимальная длинна одного предложения в строках, должна быть меньше или равна minN (иначе боль и мучения)
        final String resultpath = "RESULT\\";     //Имя папки для выходных файлов (для удобства)
        final String patternForDict = "^[А-Яа-яA-Za-z]+$"; //паттерн того что должно содержаться в каждой строке словаря
        final char sentenceSeparator = '.';       //символ разделителя предложений.

        N = CheckNInRange(args, minN, maxN);

        Dictionary dict = new Dictionary();
        dict.setFilename(dictfile);
        dict.checkFileExist();//Проверка существования файла словаря
        dict.checkBiggerThan(maxSizeDict);//Проверка соответствия размера файла словаря
        dict.checkStructure(patternForDict);//Проверка структуры словаря

        Set<String> dictionary = dict.readAll(); //читаю считываю весь файл словаря в память по ТЗ.

        Text text = new Text();
        text.setFilename(inputfile);
        text.checkFileExist(); //Проверка существования файла с текстом
        text.checkBiggerThan(maxSizeInput); //Проверка размера файла с текстом
        text.checkStringCount(maxLinesInput); //Проверка количества строк

        List<String> lines = new ArrayList<>(); //здесь храню необработанный текст
        List<String> outlines = new ArrayList<>(); //здесь буду хранить обработанный текст готовый к записи в файл.

        int k = 0; //счетчик количества записанных файлов
        //основная логика==
        while ((lines = text.readNexPiece(sentenceSeparator, N)).size() != 0) {   //пока есть предложения на входе данных
            for (String s : lines) { //выделяю слова в тексте совпадающие с словами из словаря в соответствии с ТЗ
                s = text.markWords(s, dictionary, insBefore, insAfter);
                outlines.add(s); //добавляю обработанную строку в список для длальнейшего вывода в файл
            }
            text.writePartToHTMLFile(resultpath, outputfile, k, outext, outlines); //вывод в файл
            k++;
            outlines.clear();//очистка
        }//основная логика==

        System.out.println("Работа программы успешно завершена.");
    }//run
}
