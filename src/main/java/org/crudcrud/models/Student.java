package org.crudcrud.models;

public class Student {

    private String name;
    private int grade;

    //constructor
    public Student (String name,int grade){
        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getGrade() {
        return grade;
    }


    //Переопределим метод toString для получения json в нужном нам формате из объекта
    @Override
    public String toString() {
        return "{" +
                "\"name\": \"" + name + "\"," + // Экранируем двойные кавычки
                "\"grade\": " + grade +
                "}";
    }

}
