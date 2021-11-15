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
@Feature("Register Tests")
public class RegisterTests extends BaseTest {

    @Test(priority = 0, description="Register account successful")
    @Severity(SeverityLevel.MINOR)
    @Description("Test Description: Register account successful")
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

    @Test(priority = 0, description="Register account unsuccessful")
    @Severity(SeverityLevel.MINOR)
    @Description("Test Description: Register account unsuccessful")
    public void testRegister_unsuccessful(){
        String endPoint = "https://reqres.in/api/register";
        Account account = new Account("sydney@fife");
        ValidatableResponse response = RestAssured.given().body(account).when()
                .post(endPoint).then()
                .assertThat().statusCode(400)
                .body("error", Matchers.equalTo("Missing email or username"));
        response.log().body();
    }
}
