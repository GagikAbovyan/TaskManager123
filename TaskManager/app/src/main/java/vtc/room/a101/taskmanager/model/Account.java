package vtc.room.a101.taskmanager.model;

public class Account {
    private final String email;
    private final String password;
    private String number;
    private String name;
    private String surname;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
