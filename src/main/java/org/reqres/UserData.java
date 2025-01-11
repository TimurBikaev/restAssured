package org.reqres;

public class UserData {

    //переведем типы в объекты
    private Integer id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;


    //при извлечении Гет-запроса будем создавать конструктор для создания экз.класса
    public UserData(Integer id, String email, String first_name, String last_name, String avatar) {
        this.id = id;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.avatar = avatar;
    }

    //создадим и геттеры для проверок нужных полей
    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getAvatar() {
        return avatar;
    }
}
