package praktikumservices.qascooter;

public final class EndPoints {
    // путь до базового URL
    public static final String baseURI = "https://qa-scooter.praktikum-services.ru";
    // путь до Courier - Создание курьера
    public static final String createCourier = "/api/v1/courier";
    // путь до Courier - Логин курьера в системе
    public static final String loginCourier = "/api/v1/courier/login";
    // путь до Courier - Удаление курьера
    public static final String deleteCourier = "/api/v1/courier/";
    // путь до Заказа - Создание заказа
    public static final String createOrder = "/api/v1/orders";
    // путь до Заказа - Отмена заказа
    public static final String cancelOrder = "/api/v1/orders/cancel";
    // путь до Заказа - Принять заказ
    public static final String acceptOrder = "/api/v1/orders/accept/";
    // путь до Заказа - Получение списка заказов
    public static final String getOrders = "/api/v1/orders/";
    // путь до Заказа - Получение заказа по номеру
    public static final String getOrdersByTrackId = "/api/v1/orders/track";
}
