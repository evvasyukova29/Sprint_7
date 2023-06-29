import courier.*;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class TestForCreateCourier
{
    private GeneratorForCourier generatorForCourier = new GeneratorForCourier();
    private CourierToClient courierToClient;
    private Courier courier;
    private StatusForCourier statusForCourier;
    private LoginDetails loginDetails;
    int idCourier;

    @Test
    @DisplayName("Creating a new courier")
    @Description("Successful creation with status code check")
    public void successCreatedCourier()
    {
        ValidatableResponse responseCreatedCourier = courierToClient.createCourier(courier);
        statusForCourier.createdStatusForCourier(responseCreatedCourier);
        LoginDetails loginDetails = LoginDetails.from(courier);
        ValidatableResponse responseLoginCourier = courierToClient.loginCourier(loginDetails);
        idCourier = responseLoginCourier.extract().path("id");

    }

    @Test
    @DisplayName("Creating a courier without login")
    @Description("Checking the creation of a new courier without login")
    public void courierCanNotBeCreatedWithoutLoginField() {
        courier.setLogin(null);
        ValidatableResponse responseNullLogin = courierToClient.createCourier(courier);
        statusForCourier.badRequestForCourier(responseNullLogin);
    }

    @Test
    @DisplayName("Creating a courier without password")
    @Description("Checking the creation of a new courier without a password")
    public void courierCanNotBeCreatedWithoutPasswordField()
    {
        courier.setPassword(null);
        ValidatableResponse responseNullPassword = courierToClient.createCourier(courier);
        statusForCourier.badRequestForCourier(responseNullPassword);
    }

    @Test
    @DisplayName("Creating a courier without login and password")
    @Description("Checking the creation of a new courier without login and password")
    public void courierCanNotBeCreatedWithoutLoginAndPasswordFields()
    {
        courier.setLogin(null);
        courier.setPassword(null);
        ValidatableResponse responseNullFields = courierToClient.createCourier(courier);
        statusForCourier.badRequestForCourier(responseNullFields);
    }

    @Test
    @DisplayName("Creating a courier with existing data")
    @Description("Checking the creation of a courier with already existing data")
    public void courierCanNotBeCreatedWithTheSameData()
    {
        courierToClient.createCourier(courier);
        ValidatableResponse responseCreateCourier = courierToClient.createCourier(courier);
        statusForCourier.conflictForCourier(responseCreateCourier);
    }

    @After
    @Step("Delete courier")
    public void deleteCourier() {
        if (idCourier != 0) {
            courierToClient.deleteCourier(idCourier);
        }
    }

}
