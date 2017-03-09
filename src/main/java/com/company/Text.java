package main.java.com.company;

import java.io.*;
import java.util.*;


/**
 * Created by jiraff537
 */
public class Text extends Java12abstract  {
    private int readed = 0; //обработано строк изфайла
    LinkedList<String> ostatok = new LinkedList<>(); //чтобы не делить одно предложение на два файла тут буду хранить его кусок

    //проверка количества строк в файле и вывод сообщения об ошибке в случае превышения.
    void checkStringCount(int maxLinesInput) {
        int i = 0;
        BufferedReader bufferedReader = null;
        try {
            FileReader fileReader = new FileReader(filename);
            bufferedReader = new BufferedReader(fileReader);
            while (bufferedReader.readLine() != null)
                i++;
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (i > maxLinesInput) {
            System.out.println("Ошибка: файл " + filename + " содержит более " + maxLinesInput + " строк.");
            System.exit(537);
        }
    }


    //чтение файла частями
    private LinkedList<String> ReadPart(int start, int end) {
        //======================ЧТЕНИЕ ФАЙЛА input ЧАСТЯМИ===================================
        LinkedList<String> inputlines = new LinkedList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Файл " + filename + " не найден");
        }
        String line;
        int counter = 0;

        try {
            while ((line = reader.readLine()) != null && counter < end) { //т.е. буду читать частями
                if (counter >= start) {
                    inputlines.add(line);
                }
                counter++;
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Ошибка: файл " + filename + " отсутствует!");
        }
        return inputlines;
    }

    //чтение следующего блока текста
    //читаю по N строк и если предложение разрезается посередине отрезаю его и добавляю его к следующему прочитанному блоку текста
    List<String> readNexPiece(char sentenceSeparator, int N) {
        LinkedList<String> inputlines = new LinkedList<>(); //для хранения блока данных из Файла_с_текстом, он будет читаться частями
        inputlines = ReadPart(readed * N, (readed + 1) * N); //читаю файл  кусками в N строк, ведь потом N строк и писать требуется.
        inputlines.addAll(0, ostatok); // если отпредыдущего файла остались cтроки то добавляем их в начало
        ostatok.clear();//очищаем остаток - он сейчас снова будет использоваться.

        // задача не делить одно предложение на 2 разных HTML_файла.
        // моя интерпретация: отрезать от что после последней точки и доконца в переменную ostatok для дальнейшей обработки со следующим блоком текста.
        Iterator rti = inputlines.descendingIterator();
        String line;

        while (rti.hasNext()) {//буду с конца списка inputlines искать искать строку с точкой:
            while (inputlines.size() > N) {
                ostatok.addFirst(rti.next().toString());
                rti.remove();
            }
            line = (rti.next().toString()).trim();//беру первую с конца списка строку, отрезаю висящие пробелы.
            if (line.lastIndexOf(sentenceSeparator) == line.length()) {// если точка в конце строки и в остаток добавлять нечего. использую lastIndexOf - вдруг точек в строке больше чем одна?
                break; //outer;// выходим из while т.к. "задача не делить одно предложение на 2 разных HTML_файла." выполнена!
            } else {
                if (line.indexOf(sentenceSeparator) != -1) { //если точка в строке есть (в середине а не в конце)
                    ostatok.addFirst(line.substring(line.lastIndexOf(sentenceSeparator) + 1).trim());//кусок от последней точки и до конца строки в остаток без висящих пробелов
                    rti.remove();                                                           //стоку удаляем
                    inputlines.add(line.substring(0, line.lastIndexOf(sentenceSeparator) + 1).trim());//добавляем ту часть строки что оканчивается точной.
                    break; //outer;// выходим из while т.к. "задача не делить одно предложение на 2 разных HTML_файла." выполнена!
                } else {
                    ostatok.addFirst(line);//если точки в середине строки нет забрать всю строку в остаток
                    rti.remove();//удаляю эту строку т.к. это оборванная часть предложения
                }
            }
        }
        readed++;
        return inputlines;
    }

    //метод выделяет слово из строки, сравнивает со словарем
    public String markWords(String text, Set<String> slovar, String before, String after) {
        String[] split = text.split("\\s");              //разбиваю строку на массив по пробелу; получаю релультат:
        // [Слово,|слово_слово_слово|слово.| и т.д.]
        text = "";
        String s;
        for (int i = 0; i < split.length; i++) {
            s = split[i];
            String tmp = s.replaceAll("[^A-Za-zА-Яа-я_]", "");  // удалится все(заки препинаниня и прочий мусор) кроме букв(рус+англ) и знака подчеркивания
            if (slovar.contains(tmp)) {                          //проверяю содержится ли очищенное слово в словаре?
                s = s.replace(tmp, surroundWith(tmp, before, after));               //если да - обрабатываю это слово согластно ТЗ(bold+italic)
            }
            StringBuilder sb = new StringBuilder(text);         //собираю массив обратно в строку потребляю память экономно
            sb.append(s + " ");
            text = sb.toString();
            text.trim();
        }
        return text;
    }

    //окружает строку с двух сторон строками before и after соответственно например: "текст" "<b><i>текст</i></b>"
    private String surroundWith(String str, String before, String after) {
        //добавит в строку str: в вначало before и after в конец
        StringBuilder s = new StringBuilder(str);
        s.append(after);
        s.insert(0, before);
        //возвращаю измененную строку
        return s.toString();
    }

    //записывает блок текста в HTML файл довавляя шапку и тег переноса после каждой строки,
    // выводит информационные сообщения о результатах работы программы.
    void writePartToHTMLFile(String resultpath, String filename, int k, String outExt, List<String> outputLines) {
        int zapisanoStrok = 0;
        PrintWriter out = null;
        try {
            out = new PrintWriter(resultpath + filename + k + outExt);
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        }
        //добавляю заголовок HTML
        out.println("<!DOCTYPE html>\n" +
                "<meta charset=\"utf-8\">\n" +
                "<title>" + k + " HTML</title>\n" +
                "<body>");
        for (int i = 0; i < outputLines.size(); i++) {
            String text = outputLines.get(i);
            out.println(text + "<br>"); //тег <br>  перевод строки
            zapisanoStrok++;
        }
        out.println("</body>");//в конце файла закрываю тег body
        out.close(); //закрываю файл.
        System.out.println("В файл " + resultpath + filename + k + outExt + " записано " + zapisanoStrok + " строк.");
    }

}//class Text
