import courier.*;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
public class TestForLoginCourier
{
    private final GeneratorForCourier generatorForCourier = new GeneratorForCourier();
    private LoginDetails loginDetails;
    private CourierToClient courierToClient;
    private Courier courier;
    private StatusForCourier statusForCourier;
    int idCourier;

    @Before
    @Step("Preconditions for login tests with courier creation")
    public void setUp()
    {
        courierToClient = new CourierToClient();
        courier = generatorForCourier.getRandomValue();
        courierToClient.createCourier(courier);
        loginDetails = LoginDetails.from(courier);
        statusForCourier = new StatusForCourier();
    }

    @Test
    @DisplayName("Successful authorization with receipt id")
    @Description("Status Code = 200")
    public void courierSuccess()
        {
            ValidatableResponse responseLoginCourier = courierToClient.loginCourier(loginDetails);
            statusForCourier.loginSuccessForCourier(responseLoginCourier);
            idCourier = responseLoginCourier.extract().path("id");
        }

    @Test
    @DisplayName("Request without login")
    @Description("Status Code = 400")
    public void badRequestWithoutLogin()
    {
        LoginDetails loginDetailsWithoutLogin = new LoginDetails("", courier.getPassword());
        ValidatableResponse responseBadRequestLogin = courierToClient.loginCourier(loginDetailsWithoutLogin).statusCode(400);
        responseBadRequestLogin.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Request without password")
    @Description("Status Code = 400")
    public void badRequestWithoutPassword()
    {
        LoginDetails loginDetailsWithoutPassword = new LoginDetails(courier.getLogin(), "");
        ValidatableResponse responseBadRequestPassword = courierToClient.loginCourier(loginDetailsWithoutPassword).statusCode(400);
        responseBadRequestPassword.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Request without login and password")
    @Description("Status Code = 400")
    public void badRequestWithoutFieldAllValues()
    {
        LoginDetails loginDetailsWithoutAllValue = new LoginDetails("", "");
        ValidatableResponse responseBadRequestAllValue = courierToClient.loginCourier(loginDetailsWithoutAllValue).statusCode(400);
        responseBadRequestAllValue.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Login by courier with non-existent login")
    @Description("Status Code = 404")
    public void notFoundCourier()
    {
        LoginDetails loginDetailsWithNotExistingLogin = new LoginDetails(RandomStringUtils.randomAlphanumeric(6), courier.getPassword());
        ValidatableResponse responseWithNotExistingLogin = courierToClient.loginCourier(loginDetailsWithNotExistingLogin).statusCode(404);
        responseWithNotExistingLogin.assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    @Step("Deleting a created courier")
    public void deleteCourier() {
        if (idCourier != 0) {
            courierToClient.deleteCourier(idCourier);
        }
    }

}
