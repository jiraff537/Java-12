package com.simbirsoft.itplace.service.impl;

import com.simbirsoft.itplace.dao.repository.PersonRepository;
import com.simbirsoft.itplace.dao.repository.impl.PersonRepositoryFromPropertyFileImpl;
import com.simbirsoft.itplace.domain.entity.PersonalData;
import com.simbirsoft.itplace.service.api.SummaryService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Реализация интерфейса @see {@link SummaryService}
 */
public class SummaryServiceImpl implements SummaryService {

    /**
     * @see PersonalData
     */
    private PersonalData personalData;

    /**
     * Метод для подключения .properties файла
     * @param propertyFilePath - путь к .properties файла
     */
    public SummaryServiceImpl(String propertyFilePath){
        PersonRepository personRepository = new PersonRepositoryFromPropertyFileImpl(
                getClass().getClassLoader().getResourceAsStream(propertyFilePath)
        );
        this.personalData = personRepository.getPersonalData();
    }

    @Override
    public void createHtmlFile(String pathHtmlFile) {
        if (this.personalData != null){
            String html = "<!DOCTYPE html>\r\n" +
                    "<html lang=\"ru\">\r\n" +
                    "<head>\r\n" +
                    "    <title>Резюме</title>\r\n" +
                    "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\"\r\n" +
                    "          integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\"\r\n" +
                    "          crossorigin=\"anonymous\">\r\n" +
                    "    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"\r\n" +
                    "            integrity=\"sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa\"\r\n" +
                    "            crossorigin=\"anonymous\"></script>\r\n" +
                    "    <style>\r\n" +
                    "        body{background: darkgray; padding: 10px 0;}\r\n" +
                    "        .container { max-width: 50%; background: white; padding: 10px;}\r\n" +
                    "    </style>\r\n" +
                    "</head>\r\n" +
                    "<body>\r\n" +
                    "    <div class=\"container\">\r\n" +
                    "        <h4 class=\"text-center\"><strong>Резюме</strong><p>на должность Java-стажер</p></h4>\r\n" +
                    "        <div class=\"row\">\r\n" +
                    "            <div class=\"col-xs-8 col-md-8\">\r\n" +
                    "                <dl class=\"dl-horizontal\">\r\n" +
                    "                    <dt>ФИО:</dt><dd>" + personalData.getFIO() + "</dd>\r\n" +
                    "                    <dt>Дата рождения:</dt><dd>" + personalData.getDOB() + "</dd>\r\n" +
                    "                    <dt>Телефон:</dt><dd>" + personalData.getPhone() + "/dd>\r\n" +
                    "                    <dt>e-mail:</dt><dd>" + personalData.getEmail() + "</dd>\r\n" +
                    "                    <dt>Skype:</dt><dd>" + personalData.getSkype() + "</dd>\r\n" +
                    "                </dl></div>\r\n" +
                    "            <div class=\"col-xs-4 col-md-4\">\r\n" +
                    "                <img src=\"" + personalData.getAvatar() + "\" width=\"100%\">\r\n" +
                    "            </div>\r\n" +
                    "        </div>\r\n" +
                    "        <div class=\"card card-block\">\r\n" +
                    "            <h4 class=\"card-title\"><strong>Цель:</strong></h4>\r\n" +
                    "            <p class=\"card-text\">" + personalData.getTarget() + "</p>\r\n" +
                    "        </div>\r\n" +
                    "        <div class=\"card card-block\">\r\n" +
                    "            <h4 class=\"card-title\"><strong>Опыт работы:</strong></h4>\r\n" +
                    "            <ol class=\"card-text\">\r\n"
                                    + personalData.getExperiences() +
                    "            </ol>\r\n" +
                    "        </div>\r\n" +
                    "        <div class=\"card card-block\">\r\n" +
                    "            <h4 class=\"card-title\"><strong>Образование:</strong></h4>\r\n" +
                    "            <p class=\"card-text\">" + personalData.getEducations() + "</p>\r\n" +
                    "        </div>\r\n" +
                    "        <div class=\"card card-block\">\r\n" +
                    "            <h4 class=\"card-title\"><strong>Доп. образ. и курсы:</strong></h4>\r\n" +
                    "            <ol class=\"card-text\">\r\n"
                                    + personalData.getAdditionalEducations() +
                    "            </ol>\r\n" +
                    "        </div>\r\n" +
                    "        <div class=\"card card-block\">\r\n" +
                    "            <h4 class=\"card-title\"><strong>Навыки:</strong></h4>\r\n" +
                    "            <ol class=\"card-text\">\r\n"
                                    + personalData.getSkills() +
                    "            </ol>\r\n" +
                    "        </div>\r\n" +
                    "        <div class=\"card card-block\">\r\n" +
                    "            <h4 class=\"card-title\"><strong>Примеры моего кода:</strong></h4>\r\n" +
                    "            <div class=\"card-text\">\r\n"
                                    + personalData.getExamplesCode() +
                    "            </div>\r\n" +
                    "        </div>\r\n" +
                    "    </div>\r\n" +
                    "</body>\r\n" +
                    "</html>";
            try {
                File file = new File(pathHtmlFile);
                if(!file.exists()){
                    file.createNewFile();
                }
                FileWriter writer = new FileWriter(pathHtmlFile, false);
                writer.write(html);
                writer.flush();
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
}
