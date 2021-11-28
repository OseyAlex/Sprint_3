package praktikumservices.qascooter;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class OrderMethods {

    @Step("Создать заказ")
    public int sendPostRequestNewOrder(Order order) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(order)
                .when()
                .post(EndPoints.createOrder)
                .then()
                .assertThat()
                .statusCode(201)
                .extract().path("track");
    }

    @Step("Отменить заказ")
    public boolean cancelOrder(TrackOrder orderTrack) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(orderTrack)
                .when()
                .put(EndPoints.cancelOrder)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().path("ok");
    }

    @Step("Принять заказ")
    public boolean acceptOrder(int courierId, int trackOrder) {
        return given()
                .when()
                .queryParam("courierId", courierId)
                .put(EndPoints.acceptOrder + trackOrder)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().path("ok");
    }

    @Step("Получить список заказов")
    public int getOrder(int courierId) {
        return given()
                .when()
                .queryParam("courierId", courierId)
                .get(EndPoints.getOrders)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().body().path("pageInfo.total");
    }

    @Step("Получить заказ по номеру")
    public int getOrderByTrackId(int firstOrderTrack) {
        return given()
                .when()
                .queryParam("t", firstOrderTrack)
                .get(EndPoints.getOrdersByTrackId)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().body().path("order.id");
    }
}
