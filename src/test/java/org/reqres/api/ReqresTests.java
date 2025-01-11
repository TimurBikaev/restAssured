package org.reqres.api;

import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reqres.*;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class ReqresTests {





    @Test
    @Description("Тест 1")
    public void nameAvatarsShouldHaveId() {

        //сетапим спеки
        Spec.installSpecification(Spec.requestSpec(), Spec.responseSpecOK200());

        // используя сервис получить список пользователей со страницы 2
        List<UserData> users = given()
                .when()
                //тип контента установлен в спеке выше
                .get("/api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath()
                .getList("data", UserData.class); //путь к данным внутри json и куда извлечь - в POJO класс

        //Далее извлекаем данные из полученных экземпляров класса
        // переберем весь список users -- Убедимся, что имена аватаров содержат в себе id юзеров
        users.forEach(x -> Assertions.assertTrue(x.getAvatar().contains(x.getId().toString())));

        //второй способ
        //соберем в список имена файлов аватаров и айди и почты
        List<String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());
        List<String> ids = users.stream().map(x -> x.getId().toString()).collect(Collectors.toList());
        List<String> mails = users.stream().map(UserData::getEmail).collect(Collectors.toList());

        //перебором проверим совпадения, вызывая аватары и айди по индексу
        for (int i = 0; i < avatars.size(); i++) {
            Assertions.assertTrue(avatars.get(i).contains(ids.get(i)));
        }


        //Проверка что почта заканчивается нужным доменом
        //ПЕРВЫЙ СПОСОБ - через allMatch через стрим проверяем каждый элемент, извлекаем почту и проверяем окончание
        Assertions.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("reqres.in")));

        //ВТОРОЙ СПОСОБ - перебором проверим совпадения, вызывая mails по индексу
        for (int i = 0; i < mails.size(); i++) {
            Assertions.assertTrue(mails.get(i).endsWith("reqres.in"));
        }

    }

    @Test
    @Description("Тест 2")
    public void successRegTest() {
        Spec.installSpecification(Spec.requestSpec(), Spec.responseSpecOK200()); //ждем ответ 200

        //ожид.рез.
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";

        //Создадим экз. класса регистр.юзер
        Register user = new Register("eve.holt@reqres.in", "pistol");

        SuccessRegister successRegister = given()
                .body(user)
                .when().post("api/register")
                .then().log().all() //извлекаем всё
                .extract().as(SuccessRegister.class); //извлекаем в класс и проверим через ассерт

        //проверка на непустой результат
        Assertions.assertNotNull(successRegister);
        Assertions.assertEquals(id, successRegister.getId());
        Assertions.assertEquals(token, successRegister.getToken());

        //


    }


    @Test
    @Description("Тест 3")
    public void unSuccessRegTest() {
        Spec.installSpecification(Spec.requestSpec(), Spec.responseSpec400()); //ждем ответ 400


        //Создадим экз. класса регистр.юзер
        Register user = new Register("eve.holt@reqres.in", "");

        UnSuccessRegister unSuccessRegister = given()
                .body(user)
                .when().post("api/register")
                .then().log().all() //извлекаем всё
                .extract().as(UnSuccessRegister.class); //извлекаем в класс и проверим через ассерт

        Assertions.assertEquals("Missing password", unSuccessRegister.getError());

    }

    @Test
    @Description("Тест 4")
    public void sortedTest(){
        Spec.installSpecification(Spec.requestSpec(), Spec.responseSpecOK200());

        //соберем список объектов
        List<ColorsData> colors = given()
                .when().get("api/unknown")
                .then().log().all()
                .extract().body().jsonPath().getList("data", ColorsData.class);

        //достанем стримом годы
        List<Integer> years = colors.stream().map(ColorsData::getYear).collect(Collectors.toList());
        //отсортируем его
        List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList());
        //Проверить что полученный и отсортированный список совпадают
        Assertions.assertEquals(sortedYears, years);

    }

    @Test
    public void deleteTest(){
        //проверка по спеке
        Spec.installSpecification(Spec.requestSpec(), Spec.responseSpec(204));
        given()
                .when()
                .delete("api/users/2")
                .then().log().all();
    }


}
