package client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


public class Client
{
    // URL сайта Самокат
    public static final String BASE_URL = "http://qa-scooter.praktikum-services.ru/";

    public RequestSpecification getSpecification()
    {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .build();
    }


}
