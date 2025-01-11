package org.reqres;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

//спека для запросов и ответов
public class Spec {

    private final static String URL = "https://reqres.in";

    //добавим метод сетапа спецификации
    public static void installSpecification(RequestSpecification request, ResponseSpecification response){
        //настраиваеем RestAssured под наши спеки
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }

    //спека для запросов
    public static RequestSpecification requestSpec(){
        //базовая ссылка и тип ожидаемых данных через билдер спецификаций РестАшюреда
        return new RequestSpecBuilder()
                .setBaseUri(URL)
                .setContentType(ContentType.JSON)
                .build();
    }

    //спека для ответов 200
    public static ResponseSpecification responseSpecOK200(){
        //базовая ссылка и тип ожидаемых данных через билдер спецификаций РестАшюреда
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    //спека для ответов 400
    public static ResponseSpecification responseSpec400(){
        //базовая ссылка и тип ожидаемых данных через билдер спецификаций РестАшюреда
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .build();
    }

    //спека для ответов c нужным кодом
    public static ResponseSpecification responseSpec(int code){
        //базовая ссылка и тип ожидаемых данных через билдер спецификаций РестАшюреда
        return new ResponseSpecBuilder()
                .expectStatusCode(code)
                .build();
    }



}
