package org.crudcrud;


import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.http.HttpStatus;
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

        String body = "{"
                + "\"name\": \"Timur\","
                + "\"grade\": 2"
                + "}";

        Methods methods = new Methods();
        methods.createStudent(body);


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

        String userId = Methods.createStudent(body);


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
