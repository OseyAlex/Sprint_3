package praktikumservices.qascooter.methods;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import praktikumservices.qascooter.EndPoints;
import praktikumservices.qascooter.entities.TrackOrder;
import praktikumservices.qascooter.entities.Order;

import static io.restassured.RestAssured.given;

public class MethodsToCreateCancelGetTrackGetOrdersAcceptOrder {

    @Step("Создать заказ")
    public ValidatableResponse sendPostRequestNewOrder(Order order) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(order)
                .when()
                .post(EndPoints.createOrder)
                .then();
    }

    @Step("Отменить заказ")
    public ValidatableResponse cancelOrder(TrackOrder orderTrack) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(orderTrack)
                .when()
                .put(EndPoints.cancelOrder)
                .then();
    }

    @Step("Принять заказ")
    public ValidatableResponse acceptOrder(int courierId, int trackOrder) {
        return given()
                .when()
                .queryParam("courierId", courierId)
                .put(EndPoints.acceptOrder + trackOrder)
                .then();
    }

    @Step("Получить список заказов")
    public ValidatableResponse getOrder(int courierId) {
        return given()
                .when()
                .queryParam("courierId", courierId)
                .get(EndPoints.getOrders)
                .then();
    }

    @Step("Получить заказ по номеру")
    public ValidatableResponse getOrderByTrackId(int firstOrderTrack) {
        return given()
                .when()
                .queryParam("t", firstOrderTrack)
                .get(EndPoints.getOrdersByTrackId)
                .then();
    }
}
