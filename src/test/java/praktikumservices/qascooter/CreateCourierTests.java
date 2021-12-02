package praktikumservices.qascooter;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikumservices.qascooter.entities.Courier;
import praktikumservices.qascooter.entities.CourierCredentials;
import praktikumservices.qascooter.methods.MethodsToCreateDeleteLoginCourier;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateCourierTests {
    private MethodsToCreateDeleteLoginCourier methodsToCreateDeleteLoginCourier;
    private int courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = EndPoints.baseURI;
        methodsToCreateDeleteLoginCourier = new MethodsToCreateDeleteLoginCourier();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @After
    @Step("Удалить курьера")
    public void tearDown() {
        if (courierId != 0)
            methodsToCreateDeleteLoginCourier.deleteCourierById(courierId);
    }

    @Test
    @DisplayName("Создаём 1 курьера")
    public void createOneCourier() {
        Courier courier = Courier.getWithLoginPasswordAndFirstName();

        ValidatableResponse response = new MethodsToCreateDeleteLoginCourier().create(courier);

        int statusCode = response.extract().statusCode();
        boolean isCourierCreated = response.extract().path("ok");

        assertThat(statusCode, equalTo(201));
        assertTrue(isCourierCreated);

        //узнаём Id, чтобы удалить
        ValidatableResponse responseLogin = methodsToCreateDeleteLoginCourier.loginCourier(new CourierCredentials().setCourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = responseLogin.extract().path("id");
    }

    @Test
    @DisplayName("Создаём 1 курьера c минимальным кол-вом обяз. полей")
    public void createOneCourierWithRequiredFieldsTest() {
        Courier courier = Courier.getWithLoginAndPassword();

        ValidatableResponse response = new MethodsToCreateDeleteLoginCourier().create(courier);

        int statusCode = response.extract().statusCode();
        boolean isCourierCreated = response.extract().path("ok");

        assertThat(statusCode, equalTo(201));
        assertTrue(isCourierCreated);

        //узнаём Id, чтобы удалить
        ValidatableResponse responseLogin = methodsToCreateDeleteLoginCourier.loginCourier(new CourierCredentials().setCourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = responseLogin.extract().path("id");
    }

    @Test
    @DisplayName("Создаём 1 курьера c логином, проверяем что будет ошибка")
    public void createOneCourierWithNotEnoughFieldsTest() {
        Courier courier = Courier.getWithLoginOnly();

        ValidatableResponse response = new MethodsToCreateDeleteLoginCourier().create(courier);

        int statusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");

        String expectedErrorMessage = "Недостаточно данных для создания учетной записи";
        assertThat(statusCode, equalTo(400));
        assertEquals("Сообщение отличается", expectedErrorMessage, actualMessage);
    }

    @Test
    @DisplayName("Создаём 2 курьера c одинаковыми данными")
    public void createTwoCourierTest() {
        Courier courier = Courier.getWithLoginPasswordAndFirstName();

        new MethodsToCreateDeleteLoginCourier().create(courier);
        ValidatableResponse responseNegative = methodsToCreateDeleteLoginCourier.create(courier);

        int statusCode = responseNegative.extract().statusCode();
        String actualErrorMessage = responseNegative.extract().path("message");
        String expectedErrorMessage = "Этот логин уже используется. Попробуйте другой.";

        assertThat(statusCode, equalTo(409));
        assertEquals("Сообщение отличается", expectedErrorMessage, actualErrorMessage);

        //узнаём Id, чтобы удалить
        ValidatableResponse responseLogin = methodsToCreateDeleteLoginCourier.loginCourier(new CourierCredentials().setCourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = responseLogin.extract().path("id");
    }
}
