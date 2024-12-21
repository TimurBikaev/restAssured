package org.crudcrud;


import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.internal.mapping.Jackson1Mapper;
import org.apache.http.HttpStatus;
import org.crudcrud.models.Student;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public class Test {

    //метод для настройки RestAssured
    @BeforeAll
    public static void setUp() {
        //Логирование
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        //Базовый урл
        RestAssured.baseURI = "https://crudcrud.com/api/239cc93637694d9f96d5ce3074ecb2d5";
    }

    @org.junit.jupiter.api.Test
    public void userShouldBeAbleCreateStudent() {

        //Сериализация из JSON в объект и наоборот
//        String body = "{"
//                + "\"name\": \"Timur\","
//                + "\"grade\": 2"
//                + "}";

        //Создадим объект класса студент с нужными параметрами
        Student student1 = new Student("Timur", 2);

        //Передадим в метод создания студента
        Methods methods = new Methods();
        methods.createStudent(student1.toString()); // получим json из объекта


        /** получаем ответ в виде
         * {
         *     "name": "Timur",
         *     "grade": 2,
         *     "_id": "6766ac8b6297b503e8bd90ad"
         * }
         */

    }

    @org.junit.jupiter.api.Test
    public void userShoudBeAbleDeleteExistingStudent() {

        //подготовка данных - создадим юзера для удаления

        String body = "{"
                + "\"name\": \"Timur\","
                + "\"grade\": 2"
                + "}";

        //Создадим объект класса студент с нужными параметрами
        Student student2 = new Student("Tom", 3);

        Gson gson = new Gson();
        String json = gson.toJson(student2); //перевод объекта в json
        Student obj = gson.fromJson(json, Student.class);

        System.out.println( "GSON to json " + json);
        // Выводим объект
        System.out.println("GSON to obj " + obj);
        System.out.println(obj.toString());

        //Передадим на создание
        String userId = Methods.createStudent(json);


        //проверка что можем получить созданного студента
        given().get("/student/" + userId).then().assertThat().statusCode(200);


        //удаление
        given()
                .delete("/student/" + userId)
                .then()
                .assertThat().statusCode(200);


        //Проверка что удалено - не можем получить
        given().get("/student/" + userId).then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);

    }
}
