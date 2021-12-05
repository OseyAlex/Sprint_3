package praktikumservices.qascooter.helpers;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import praktikumservices.qascooter.EndPoints;
import praktikumservices.qascooter.entities.Courier;
import praktikumservices.qascooter.entities.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierHelper {

    @Step("Удаляем созданного курьера")
    public boolean deleteCourierById(int courierId) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .delete(EndPoints.deleteCourier + courierId)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().path("ok");
    }

    @Step("Создать курьера")
    public ValidatableResponse create(Courier courier) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .when()
                .post(EndPoints.createCourier)
                .then();
    }

    @Step("Логиним курьера")
    public ValidatableResponse loginCourier(CourierCredentials credentials) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(credentials)
                .when()
                .post(EndPoints.loginCourier)
                .then();
    }
}
