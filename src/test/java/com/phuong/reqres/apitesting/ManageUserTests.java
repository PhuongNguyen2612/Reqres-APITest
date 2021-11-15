package com.phuong.reqres.apitesting;

import com.phuong.reqres.BaseTest;
import com.phuong.reqres.testListener.TestListener;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import model.User;
import org.hamcrest.Matchers;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({ TestListener.class })
@Epic("Regression Tests")
@Feature("Manage user Tests")
public class ManageUserTests extends BaseTest {

    @Test(priority = 0, description="Get list users")
    @Severity(SeverityLevel.MINOR)
    @Description("Test Description: Get list users")
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

    @Test(priority = 0, description="Get single user")
    @Severity(SeverityLevel.MINOR)
    @Description("Test Description: Get single user")
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

    @Test(priority = 0, description="Get single user not found")
    @Severity(SeverityLevel.MINOR)
    @Description("Test Description: Get single user not found")
    public void testGetSingleUserNotFound() {
        String endPoint = "https://reqres.in/api/users/23";
        ValidatableResponse response = RestAssured.given().when().get(endPoint).then()
                .assertThat().statusCode(404);
        response.log().body();
    }

    @Test(priority = 0, description="Create user successful")
    @Severity(SeverityLevel.MINOR)
    @Description("Test Description: Create user successful")
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

    @Test(priority = 0, description="Update user successful")
    @Severity(SeverityLevel.MINOR)
    @Description("Test Description: Update user successful")
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

    @Test(priority = 0, description="Update user successful")
    @Severity(SeverityLevel.MINOR)
    @Description("Test Description: Update user successful")
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

    @Test(priority = 0, description="Delete user successful")
    @Severity(SeverityLevel.MINOR)
    @Description("Test Description: Delete user successful")
    public void testDeleteUser() {
        String endPoint = "https://reqres.in/api/users/2";
        ValidatableResponse response = RestAssured.given().when()
                .delete(endPoint).then()
                .assertThat().statusCode(204);
        response.log().body();

    }

    @Test(priority = 0, description="Get delayed response")
    @Severity(SeverityLevel.MINOR)
    @Description("Test Description: Get delayed response")
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