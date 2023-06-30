import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.Order;
import order.OrderClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.*;
@RunWith(Parameterized.class)
public class TestForCreateOrder
{
        private OrderClient orderClient;
        private final String[] color;
        int track;

        public TestForCreateOrder(String[] color)
        {
            this.color = color;
        }

        @Before
        public void setUp() {
            orderClient = new OrderClient();
        }

        @Parameterized.Parameters(name = "Цвет: {0}")
        public static Object[] getData()
        {
            return new Object[][]
            {
                    {new String[]{"GREEN"}},
                    {new String[]{"GRAY"}},
                    {new String[]{"BLACK", "GRAY"}},
                    {new String[]{}}
            };
        }

        @Test
        @DisplayName("Creating an order with a different color")
        @Description("Checking order creation with different color")
        public void createOrderWithDifferentDataColor() {
            Order order = new Order(color);
            ValidatableResponse responseCreateOrder = orderClient.createNewOrder(order);
            track = responseCreateOrder.extract().path("track");
            responseCreateOrder.assertThat()
                    .statusCode(201)
                    .body("track", is(notNullValue()));
        }

        @After
        @Step("Cancel order")
        public void deleteTestOrder() {
            orderClient.cancelOrder(track);
        }
    }
