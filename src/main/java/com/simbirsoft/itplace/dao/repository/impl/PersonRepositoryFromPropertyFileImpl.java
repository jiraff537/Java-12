package com.simbirsoft.itplace.dao.repository.impl;

import com.simbirsoft.itplace.common.constants.PersonPropertyKeys;
import com.simbirsoft.itplace.dao.repository.api.PersonRepository;
import com.simbirsoft.itplace.domain.entity.PersonalData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Реализация репозитория @see {@link PersonRepository}
 *
 * @author an.stratonov
 * @version 1.0
 */
public class PersonRepositoryFromPropertyFileImpl implements PersonRepository {

    /**
     * Свойство - опыт работы
     */
    private Properties personDataFile;

    public PersonRepositoryFromPropertyFileImpl(InputStream configFileInput) {
        this.personDataFile = getProperties(configFileInput);
        this.personDataFile.setProperty(PersonPropertyKeys.EXPERIENCES,CommaToLiTag(this.personDataFile.getProperty(PersonPropertyKeys.EXPERIENCES)));
        this.personDataFile.setProperty(PersonPropertyKeys.SKILLS,CommaToLiTag(this.personDataFile.getProperty(PersonPropertyKeys.SKILLS)));
        this.personDataFile.setProperty(PersonPropertyKeys.ADDITIONAL_EDUCATIONS,CommaToLiTag(this.personDataFile.getProperty(PersonPropertyKeys.ADDITIONAL_EDUCATIONS)));

                //(PersonPropertyKeys.SKILLS)//=;
    }
    //TO DONE: заменю "," на тэги <li></li> и добавлю <li> в начало и </li> в конец
    private String CommaToLiTag(String propertyData) {
        propertyData= propertyData.replaceAll(",","</li><li>");
        return "<li>"+propertyData+"</li>";
    }
    /**
     * Возвращает объект файла найстроек
     *
     * @param configFileInput - поток файла настроек
     * @return - объект Properties
     */
    private Properties getProperties(InputStream configFileInput) {
        Properties property = new Properties();
        try {
            property.load(new InputStreamReader(configFileInput, Charset.forName("UTF-8")));
            return property;
        } catch (FileNotFoundException e) {
            System.out.println("Не найден файл настроек");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * @see PersonRepository
     */
    @Override
    public PersonalData getPersonalData() {
        PersonalData personalData = null;
        if (this.personDataFile != null) {
            personalData = new PersonalData(
                    personDataFile.getProperty(PersonPropertyKeys.FIO),
                    personDataFile.getProperty(PersonPropertyKeys.DOB),
                    personDataFile.getProperty(PersonPropertyKeys.PHONE),
                    personDataFile.getProperty(PersonPropertyKeys.EMAIL),
                    personDataFile.getProperty(PersonPropertyKeys.SKYPE),
                    personDataFile.getProperty(PersonPropertyKeys.AVATAR),
                    personDataFile.getProperty(PersonPropertyKeys.TARGET),
                    personDataFile.getProperty(PersonPropertyKeys.EXPERIENCES),
                    personDataFile.getProperty(PersonPropertyKeys.EDUCATIONS),
                    personDataFile.getProperty(PersonPropertyKeys.ADDITIONAL_EDUCATIONS),
                    personDataFile.getProperty(PersonPropertyKeys.SKILLS),
                    personDataFile.getProperty(PersonPropertyKeys.EXAMPLES_CODE)
            );
        }
        return personalData;
    }
}
