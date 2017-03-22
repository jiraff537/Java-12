package com.simbirsoft.itplace.domain.entity.api;

/**
 * Created by jiraff537 on 22.03.2017.
 */
public interface PersonalDataInt {
    /**
     * Свойство - Фамилия Имя Отчество
     */
     String FIO="";

    /**
     * Свойство - дата рождения
     */
     String DOB="";

    /**
     * Свойство - телефон
     */
     String phone="";

    /**
     * Свойство - электронная почта
     */
     String email="";

    /**
     * Свойство - скайп
     */
     String skype="";

    /**
     * Свойство - ссылка на аватарку
     */
     String avatar="";

    /**
     * Свойство - цель
     */
     String target="";

    /**
     * Свойство - опыт работы
     */
     String experiences="";

    /**
     * Свойство - образование
     */
     String educations="";

    /**
     * Свойство - дополнительное образование
     */
     String additionalEducations="";

    /**
     * Свойство - скилы
     */
     String skills="";

    /**
     * Свойство - примеры кода
     */
     String examplesCode="";

    public String getFIO();

    public void setFIO(String FIO);

    public String getDOB();

    public void setDOB(String DOB);

    public String getPhone();

    public void setPhone(String phone);

    public String getEmail();

    public void setEmail(String email);

    public String getSkype();

    public void setSkype(String skype);

    public String getAvatar();

    public void setAvatarPath(String avatar);

    public String getTarget();

    public void setTarget(String target);

    public String getExperiences();

    public void setExperiences(String experiences);

    public String getEducations();

    public void setEducations(String educations);

    public String getAdditionalEducations();

    public void setAdditionalEducations(String additionalEducations) ;

    public String getSkills();

    public void setSkills(String skills);

    public String getExamplesCode();

    public void setExamplesCode(String examplesCode);

    ;

}
