package com.phuong.reqres.apitesting;

import com.phuong.reqres.BaseTest;
import com.phuong.reqres.testListener.TestListener;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({ TestListener.class })
@Epic("Regression Tests")
@Feature("Manage resource Tests")
public class ManageResourceTests extends BaseTest {

    @Test(priority = 0, description="Get list resources")
    @Severity(SeverityLevel.MINOR)
    @Description("Test Description: Get list resources")
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

    @Test(priority = 0, description="Get single resource")
    @Severity(SeverityLevel.MINOR)
    @Description("Test Description: Get single resource")
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

    @Test(priority = 0, description="Get single resource not found")
    @Severity(SeverityLevel.MINOR)
    @Description("Test Description: Get single resource not found")
    public void testGetSingleResourceNotFound(){
        String endPoint = "https://reqres.in/api/unknown/23";
        ValidatableResponse response = RestAssured.given().when().get(endPoint).then()
                .assertThat().statusCode(404);
        response.log().body();

    }
}
