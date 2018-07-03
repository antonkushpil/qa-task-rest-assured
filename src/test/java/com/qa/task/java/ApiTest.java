package com.qa.task.java;

import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;

public class ApiTest {
    private final String APP_ID_PARAM = "APPID";
    private final String CITY_ID_PARAM = "id";
    private final String CITY_NAME_PARAM = "q";
    private String appId = "e76722b44c84e031e93d1ba898f6a905";
    private String cityId = "3104324";
    private String cityName = "Zaragoza";
    private String countryCode = "es";
    private String cityQuery = cityName + "," + countryCode;
    private String latitude = "41.65";
    private String request = "http://api.openweathermap.org/data/2.5/weather";

    @Test
    public void cityNameTest() {
        String actualCityName =
                given().
                        param(APP_ID_PARAM, appId).
                        param(CITY_ID_PARAM, cityId).
                when().
                        get(this.request).
                then().
                        assertThat().contentType(JSON).
                and().extract().path("name");

        assertThat(cityName, equalTo(actualCityName));
    }

    @Test
    public void coordinateTest() {
        String actualLatitude =
                given().
                        param(APP_ID_PARAM, appId).
                        param(CITY_NAME_PARAM, cityQuery).
                when().
                        get(this.request).
                then().
                        assertThat().statusCode(200).
                and().
                        body("coord.lon", equalTo(-0.88f)).extract().path("coord.lat").toString();

        assertThat(latitude, equalTo(actualLatitude));
    }

    @Test
    public void authTest() {
        given().
                param(CITY_ID_PARAM, cityId).
        when().
                get(this.request).
        then().
                assertThat().statusCode(401);
    }
}
