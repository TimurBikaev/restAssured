package org.crudcrud;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;

public class Methods {

    public static String createStudent(String body) {
        //Логирование
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.baseURI = "https://crudcrud.com/api/239cc93637694d9f96d5ce3074ecb2d5";

        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/student")
                .then()
                .assertThat().statusCode(201)
                .body("$", hasKey("_id")) //проверка на наличие в боди ключа
                .extract().path("_id");
    }
}
