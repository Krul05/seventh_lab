package lib.Models;

import java.io.Serializable;

/**
 * Класс Person с полями  <b>name</b>, <b>passportID</b>, <b>nationality</b>.
 */
public class Person implements Comparable<Person>, Serializable {
    private String name;
    private String passportID;
    private Country nationality;
    /**
     * @param name - Имя
     * @param passportID - ПаспортID
     * @param nationality - Национальность
     */
    public Person(String name, String passportID, Country nationality) {
        this.name = name;
        this.passportID = passportID;
        this.nationality = nationality;
    }
    public Person() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassportID() {
        return passportID;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        if (passportID != null) {
            return "Имя: " + name + "\n" +
                    "Паспорт: " + passportID + "\n" +
                    "Национальность: " + nationality.getName();
        } else {
            return "Имя: " + name + "\n" +
                    "Паспорт: \n" +
                    "Национальность: " + nationality.getName();
        }
    }
    public boolean is_valide() {
        if (name!=null && !name.isEmpty()  && nationality!=null) {
            if (passportID == null) {
                return true;
            } else if (passportID.length()>=9 && passportID.length()<=40 && !passportID.isEmpty()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Person o) {
        return this.getName().compareTo(o.getName());
    }
}
