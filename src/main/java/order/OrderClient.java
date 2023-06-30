package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import client.Client;
import static io.restassured.RestAssured.given;
public class OrderClient extends Client
{
    //Orders - Создание заказа (POST) & Orders - Получение списка заказов (GET)
    public static final String ORDER = "/api/v1/orders";
    //Orders - Отменить заказ (PUT)
    public static final String CANCEL_ORDER = "/api/v1/orders/cancel";

    @Step("Create new order")
    public ValidatableResponse createNewOrder(Order order)
    {
        return given().log().all()
                .spec(getSpecification())
                .body(order)
                .when()
                .post(ORDER)
                .then();
    }

    @Step("Getting a list of orders")
    public ValidatableResponse getOrderList(){
        return given().log().all()
                .spec(getSpecification())
                .when()
                .get(ORDER)
                .then();
    }

    @Step("Delete an existing order by track number")
    public ValidatableResponse cancelOrder(int track)
    {
        return given().log().all()
                .spec(getSpecification())
                .when()
                .get(CANCEL_ORDER)
                .then();
    }
}
