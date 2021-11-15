package com.phuong.reqres.apitesting;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import model.Account;
import model.User;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class APITests {

    @Test
    public void getListUsers() {
        String endPoint = "https://reqres.in/api/users?page=2";
        ValidatableResponse response = RestAssured.given().when().get(endPoint).then()
                .assertThat().statusCode(200)
                .body("page", Matchers.equalTo(2))
                .body("per_page", Matchers.equalTo(6))
                .body("total", Matchers.equalTo(12))
                .body("total_pages", Matchers.equalTo(2))
                .body("data.size()", Matchers.greaterThan(0))
                .body("data.id", Matchers.everyItem(Matchers.notNullValue()))
                .body("data.email", Matchers.everyItem(Matchers.notNullValue()))
                .body("data.first_name", Matchers.everyItem(Matchers.notNullValue()))
                .body("data.last_name", Matchers.everyItem(Matchers.notNullValue()))
                .body("data.avatar", Matchers.everyItem(Matchers.notNullValue()));
        response.log().body();
    }

    @Test
    public void getSingleUser() {
        String endPoint = "https://reqres.in/api/users/2";
        ValidatableResponse response = RestAssured.given().when().get(endPoint).then()
                .assertThat().statusCode(200)
                .body("data.id",Matchers.notNullValue())
                .body("data.email",Matchers.equalTo("janet.weaver@reqres.in"))
                .body("data.first_name", Matchers.equalTo("Janet"))
                .body("data.last_name", Matchers.equalTo("Weaver"))
                .body("data.avatar", Matchers.equalTo("https://reqres.in/img/faces/2-image.jpg"));
        response.log().body();
    }

    @Test
    public void testGetSingleUserNotFound() {
        String endPoint = "https://reqres.in/api/users/23";
        ValidatableResponse response = RestAssured.given().when().get(endPoint).then()
                .assertThat().statusCode(404);
        response.log().body();
    }

    @Test
    public void testGetListResource() {
        String endPoint = "https://reqres.in/api/unknown";
        ValidatableResponse response = RestAssured.given().when().get(endPoint).then()
                .assertThat().statusCode(200)
                .body("page", Matchers.equalTo(1))
                .body("per_page", Matchers.equalTo(6))
                .body("total", Matchers.equalTo(12))
                .body("total_pages", Matchers.equalTo(2))
                .body("data.size()", Matchers.greaterThan(0))
                .body("data.id", Matchers.everyItem(Matchers.notNullValue()))
                .body("data.name", Matchers.everyItem(Matchers.notNullValue()))
                .body("data.year", Matchers.everyItem(Matchers.notNullValue()))
                .body("data.color", Matchers.everyItem(Matchers.notNullValue()))
                .body("data.pantone_value", Matchers.everyItem(Matchers.notNullValue()));
        response.log().body();
    }

    @Test
    public void testGetSingleResource(){
        String endPoint = "https://reqres.in/api/unknown/2";
        ValidatableResponse response = RestAssured.given().when().get(endPoint).then()
                .assertThat().statusCode(200)
                .body("data.id", Matchers.equalTo(2))
                .body("data.name", Matchers.equalTo("fuchsia rose"))
                .body("data.year", Matchers.equalTo(2001))
                .body("data.color", Matchers.equalTo("#C74375"))
                .body("data.pantone_value", Matchers.equalTo("17-2031"));
        response.log().body();

    }

    @Test
    public void testGetSinggleResourceNotFound(){
        String endPoint = "https://reqres.in/api/unknown/23";
        ValidatableResponse response = RestAssured.given().when().get(endPoint).then()
                .assertThat().statusCode(404);
        response.log().body();

    }

    @Test
    public void testPostCreateUser() {
        String endPoint = "https://reqres.in/api/users";
        User user = new User("morpheus", "leader");
        ValidatableResponse response = RestAssured.given().header("Content-Type", "application/json")
                .body(user).when().post(endPoint).then().log().body()
                .assertThat().statusCode(201)
                .body("name", Matchers.equalTo("morpheus"))
                .body("job", Matchers.equalTo("leader"))
                .body("id", Matchers.notNullValue())
                .body("createdAt", Matchers.notNullValue());
    }

    @Test
    public void testPutUpdateUser() {
        String endPoint = "https://reqres.in/api/users/2";
        User user = new User("morpheus", "zion resident");
        ValidatableResponse response = RestAssured.given().header("Content-Type", "application/json")
                .body(user).when()
                .put(endPoint).then().log().body()
                .assertThat().statusCode(200)
                .body("name", Matchers.equalTo("morpheus"))
                .body("job", Matchers.equalTo("zion resident"))
                .body("updatedAt", Matchers.notNullValue());
    }

    @Test
    public void testPatchUpdateUser(){
        String endPoint = "https://reqres.in/api/users/2";
        User user = new User("morpheus", "zion resident");
        ValidatableResponse response = RestAssured.given().header("Content-Type", "application/json")
                .body(user).when()
                .patch(endPoint).then().log().body()
                .assertThat().statusCode(200)
                .body("name", Matchers.equalTo("morpheus"))
                .body("job", Matchers.equalTo("zion resident"))
                .body("updatedAt", Matchers.notNullValue());
    }

    @Test
    public void testDeleteUser() {
        String endPoint = "https://reqres.in/api/users/2";
        ValidatableResponse response = RestAssured.given().when()
                .delete(endPoint).then()
                .assertThat().statusCode(204);
        response.log().body();

    }

    @Test
    public void testRegister_successful() {
        String endPoint = "https://reqres.in/api/register";
        Account account = new Account("eve.holt@reqres.in", "pistol");
        ValidatableResponse response = RestAssured.given().header("Content-Type", "application/json")
                .body(account).when()
                .post(endPoint).then().log().body()
                .assertThat().statusCode(200)
                .body("id", Matchers.notNullValue())
                .body("token", Matchers.equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    public void testRegister_unsuccessful(){
        String endPoint = "https://reqres.in/api/register";
        Account account = new Account("sydney@fife");
        ValidatableResponse response = RestAssured.given().body(account).when()
                .post(endPoint).then()
                .assertThat().statusCode(400)
                .body("error", Matchers.equalTo("Missing email or username"));
        response.log().body();
    }

    @Test
    public void testLogin_successful(){
        String endPoint = "https://reqres.in/api/login";
        Account account = new Account("eve.holt@reqres.in", "cityslicka");
        ValidatableResponse response = RestAssured.given().header("Content-Type", "application/json")
                .body(account).when()
                .post(endPoint).then()
                .assertThat().statusCode(200)
                .body("token", Matchers.equalTo("QpwL5tke4Pnpja7X4"));
        response.log().body();

    }

    @Test
    public void testLogin_unsuccessful(){
        String endPoint = "https://reqres.in/api/login";
        Account account = new Account("peter@klaven");
        ValidatableResponse response = RestAssured.given().body(account).when()
                .post(endPoint).then()
                .assertThat().statusCode(400)
                .body("error", Matchers.equalTo("Missing email or username"));
        response.log().body();
    }

    @Test
    public void testGetDelayedResponse(){
        String endPoint = "https://reqres.in/api/users?delay=3";
        ValidatableResponse response = RestAssured.given().when()
                .get(endPoint).then()
                .assertThat().statusCode(200)
                .body("page", Matchers.equalTo(1))
                .body("per_page", Matchers.equalTo(6))
                .body("total", Matchers.equalTo(12))
                .body("total_pages", Matchers.equalTo(2))
                .body("data.size()", Matchers.greaterThan(0))
                .body("data.id", Matchers.everyItem(Matchers.notNullValue()))
                .body("data.email", Matchers.everyItem(Matchers.notNullValue()))
                .body("data.first_name", Matchers.everyItem(Matchers.notNullValue()))
                .body("data.last_name", Matchers.everyItem(Matchers.notNullValue()))
                .body("data.avatar", Matchers.everyItem(Matchers.notNullValue()));
        response.log().body();

    }

}