package courier;
import io.restassured.response.ValidatableResponse;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
public class StatusForCourier
{

    public void createdStatusForCourier (ValidatableResponse response)
    {
        response.assertThat()
                .statusCode(201)
                .body("ok", is(true));
    }

    public void badRequestForCourier (ValidatableResponse response)
    {
        response.assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    public void conflictForCourier (ValidatableResponse response)
    {
        response.assertThat()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    public void loginSuccessForCourier (ValidatableResponse response)
    {
        response.assertThat()
                .statusCode(200)
                .body("id", greaterThan(0));
    }
}
