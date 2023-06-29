package courier;

import client.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

public class CourierToClient extends Client
{
    //POST: создание курьера
    private static final String COURIER_CREATE = "/api/v1/courier";
    //POST: авторизация курьера в системе
    public static final String COURIER_LOGIN = "/api/v1/courier/login";
    //DELETE: удаление курьера из системы
    public static final String DELETE_COURIER = "/api/v1/courier/";

    @Step("Create courier in the system")
    public ValidatableResponse createCourier(Courier courier)
    {
        return
                given().log().all()
                        .spec(getSpecification())
                        .body(courier)
                        .when()
                        //.post("/api/v1/courier").then();
                        .post(COURIER_CREATE).then();

    }
    @Step("Authorization courier in the system")
    public ValidatableResponse loginCourier (LoginDetails loginDetails)
    {
        return
                given().log().all()
                        .spec(getSpecification())
                        .body(loginDetails)
                        .when()
                        .post(COURIER_LOGIN).then();
    }

    @Step("Delete courier in the system")
    public ValidatableResponse deleteCourier(int idCourier)
    {
        return given().log().all()
                .spec(getSpecification())
                .when()
                .delete(DELETE_COURIER + idCourier)
                .then();
    }

}
