package oop.taskTreker.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import oop.taskTreker.task.Data;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class DataServer {
    /**
     * <ul>
     *     <li>/data/main?Key=KEY - Главная</li>
     *     <li>/data/said?Key=KEY - Вторичная</li>
     * </ul>
     */
    public static final int PORT = 8055;
    private final String MAIN = "main";
    private final String SIDE = "side";

    private final HttpServer server;
    private final DataManager dataManager;

    public DataServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/register", this::handle);
        dataManager = new DataManager();
    }

    //обработка запросов от пользователя
    public void handle(HttpExchange exchange) {
        try {
            String path;
            String key;
            Data data = null;

            switch (exchange.getRequestMethod()) {
                case "GET":
                    key = exchangeKey(exchange);
                    path = extractPath(exchange);

                    if(MAIN.equals(path)) {
                        data = dataManager.getMainKey(key);
                    }else if(SIDE.equals(path)) {
                        data = dataManager.getSideKey(key);
                    }else {
                        exchange.sendResponseHeaders(404,0);
                    }
                    sendData(exchange,data);
                    break;
                case "POST":
                    path = extractPath(exchange);
                    data = extractData(exchange);

                    if(MAIN.equals(path)) {
                        dataManager.saveMain(data);
                    }else if(SIDE.equals(path)) {
                        dataManager.saveSide(data);
                    }else {
                        exchange.sendResponseHeaders(404,0);
                        return;
                    }

                    exchange.sendResponseHeaders(201,0);
                    break;
                default:
                    exchange.sendResponseHeaders(404, 0);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            exchange.close();
        }
    }

    private Data extractData(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        return new Gson().fromJson(body,Data.class);
    }

    private String exchangeKey(HttpExchange exchange) {
        return exchange.getRequestURI().getQuery().substring(4);
    }

    private void sendData(HttpExchange exchange, Data data) throws IOException {
        if(data == null) {
            exchange.sendResponseHeaders(404,0);
            return;
        }

        String json = new Gson().toJson(data);
        byte[] resp = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type","application/json;charset=utf-8");
        exchange.sendResponseHeaders(200,resp.length);
        exchange.getResponseBody().write(resp);
    }

    private String extractPath(HttpExchange exchange) {
        return exchange.getRequestURI().getPath().substring(6);
    }
    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        server.start();
    }
}
