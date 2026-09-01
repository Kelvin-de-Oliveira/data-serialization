package view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class HumanFX {
    private SimpleIntegerProperty id;
    private SimpleStringProperty fullName;
    private SimpleDoubleProperty income;
    private SimpleStringProperty birthday;

    public HumanFX(int id, String fullName, double income, String birthday) {
        this.id = new SimpleIntegerProperty(id);
        this.fullName = new SimpleStringProperty(fullName);
        this.income = new SimpleDoubleProperty(income);
        this.birthday = new SimpleStringProperty(birthday);
        this.birthday = new SimpleStringProperty(birthday);
    }

    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }

    public String getFullName() { return fullName.get(); }
    public void setFullName(String fullName) { this.fullName.set(fullName); }

    public double getIncome() { return income.get(); }
    public void setIncome(double income) { this.income.set(income); }

    public String getBirthday() { return birthday.get(); }
    public void setBirthday(String birthday) { this.birthday.set(birthday); }
}