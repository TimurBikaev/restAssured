package org.reqres.api;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reqres.Spec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ReqresWithoutPojo {

    /**
     * Без создания классов целесообразно проверять ограниченное число полей
     */

    @Test
    public void checkAvatars(){
        Spec.installSpecification(Spec.requestSpec(), Spec.responseSpec(200));
        //interface RestAssured
        Response response = given()
                .when()
                .get("api/users?page=2")
                .then()
                .body("page", equalTo(2)) //сразу работаем с полем page через hamcrest.Matchers
                .body("data.id", notNullValue()) //проверяем поля внутри иерархии json
                .body("data.email", notNullValue()) //проверяем поля внутри иерархии json
                .body("data.avatar", notNullValue()) //проверяем поля внутри иерархии json
                .extract().response();

        //дальше можем собранный респонс перевести в json библиотекой JsonPath от RestAssured
        JsonPath jsonPath = response.jsonPath();
        //извлечем в список все емэйлы
        List<String> emails = jsonPath.get("data.email");
        List<Integer> ids = jsonPath.get("data.id");
        List<String> avatars = jsonPath.get("data.avatar");

        //проверка содержания id в именах файлов аватаров
        for (int i = 0; i < avatars.size(); i++) {
            Assertions.assertTrue(avatars.get(i).contains(ids.get(i).toString()));
        }
        //проверка окончания почтовых адресов
        for (int i = 0; i < emails.size(); i++) {
            Assertions.assertTrue(emails.get(i).endsWith(".in"));
        }
    }

    @Test
    public void successUserRegWithoutPojo(){
        Spec.installSpecification(Spec.requestSpec(), Spec.responseSpec(200));
        //создадим мапу с данными для регистрации
        Map<String, String> user = new HashMap<>();
        //заполняем хэшкарту
        user.put("email", "eve.holt@reqres.in"); //ключ - значение
        user.put("password", "pistol");
        given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .body("id", equalTo(4)) //сразу проверяем
                .body("token", equalTo("QpwL5tke4Pnpja7X4")); //сразу проверяем
    }


}
