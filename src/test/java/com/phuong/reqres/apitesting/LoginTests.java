package com.phuong.reqres.apitesting;

import com.phuong.reqres.BaseTest;
import com.phuong.reqres.testListener.TestListener;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import model.Account;
import org.hamcrest.Matchers;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({ TestListener.class })
@Epic("Regression Tests")
@Feature("Login Tests")
public class LoginTests extends BaseTest {

    @Test(priority = 0, description="Login successful")
    @Severity(SeverityLevel.MINOR)
    @Description("Test Description: Login successful")
    public void testLogin_successful(){
        String endPoint = "https://reqres.in/api/login";
        Account account = new Account("eve.holt@reqres.in", "cityslicka");
        ValidatableResponse response = RestAssured.given().header("Content-Type", "application/jsonTestListener")
                .body(account).when()
                .post(endPoint).then()
                .assertThat().statusCode(200)
                .body("token", Matchers.equalTo("QpwL5tke4Pnpja7X4"));
        response.log().body();

    }

    @Test(priority = 0, description="Login unsuccessful")
    @Severity(SeverityLevel.MINOR)
    @Description("Test Description: Login unsuccessful")
    public void testLogin_unsuccessful(){
        String endPoint = "https://reqres.in/api/login";
        Account account = new Account("peter@klaven");
        ValidatableResponse response = RestAssured.given().body(account).when()
                .post(endPoint).then()
                .assertThat().statusCode(400)
                .body("error", Matchers.equalTo("Missing email or username"));
        response.log().body();
    }
}
