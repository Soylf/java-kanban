package oop.taskTreker.server;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import oop.taskTreker.manager.InMemoryTaskManager;
import oop.taskTreker.manager.TaskManager;
import oop.taskTreker.manager.historyManager.InMemoryHistoryManager;
import oop.taskTreker.task.Epic;
import oop.taskTreker.task.Subtask;
import oop.taskTreker.task.Task;
import oop.taskTreker.task.typeAndStatus.TaskType;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static oop.taskTreker.manager.Managers.getDefault;
import static oop.taskTreker.server.HttpTaskServer.Endpoint.*;


public class HttpTaskServer {
    private static final Gson gson = new Gson();
    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = UTF_8;
    private static final InMemoryTaskManager managerTask = new InMemoryTaskManager();
    private static final KVClient client = new KVClient();


    public void startServer() throws IOException {
        HttpServer httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TaskHandler());
        httpServer.start();
    }

    //public void stopServer() {}

    static class TaskHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

            switch(endpoint) {
                case GET_TASKS:
                    handleGetTasks(exchange);


                    break;
                case GET_TASK_BY_ID:
                    handleGetAnyTypeOfTaskByGivenId(exchange);
                    client.put(exchangeKey(exchange), extractPath(exchange));
                    break;
                case GET_EPICS:
                    handleGetEpics(exchange);
                    client.put(exchangeKey(exchange), extractPath(exchange));
                    break;
                case GET_SUBTASKS:
                    handleGetSubtasks(exchange);
                    client.put(exchangeKey(exchange), extractPath(exchange));
                    break;
                case POST_TASK:
                    handlePostTask(exchange);
                    client.load(exchangeKey(exchange));
                    break;
                case DELETE_TASKS:
                    handleDeleteAllTasks(exchange);
                    client.load(exchangeKey(exchange));
                    break;
                case DELETE_TASK_BY_ID:
                    handleDeleteAnyTypeOfTaskByGivenId(exchange);
                    client.load(exchangeKey(exchange));
                    break;
                case GET_SUBTASKS_OF_EPIC_BY_EPIC_ID:
                    handleGetSubtasksOfEpicByGivenId(exchange);
                    client.put(exchangeKey(exchange), extractPath(exchange));
                    break;
                case GET_HISTORY:
                    handleGetHistory(exchange);
                    client.put(exchangeKey(exchange), extractPath(exchange));
                    break;
                case GET_PRIORITIZED_TASKS:
                    handleGetPrioritized(exchange);
                    client.put(exchangeKey(exchange), extractPath(exchange));
                    break;
                default:
                    writeResponse(exchange, "no such endpoint to get", 404);
            }
        }
        private void writeResponse(HttpExchange exchange,
                                   String responseString,
                                   int responseCode) throws IOException {
            if (responseString.isBlank()) {
                exchange.sendResponseHeaders(responseCode, 0);
            } else {
                byte[] bytes = responseString.getBytes(DEFAULT_CHARSET);
                exchange.sendResponseHeaders(responseCode, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            }
            exchange.close();
        }

        private Endpoint getEndpoint(String path, String requestMethod) {
            String[] tokens = "/".split(path);
            if (tokens.length == 2 && requestMethod.equals("GET")) {
                return GET_PRIORITIZED_TASKS;
            } else if (tokens[1].equals("tasks") && tokens[2].equals("task") && requestMethod.equals("GET")) {
                return GET_TASKS;
            } else if (tokens[1].equals("tasks") && tokens[3].startsWith("?") && requestMethod.equals("GET")) {
                return GET_TASK_BY_ID;
            } else if (tokens[1].equals("tasks") && requestMethod.equals("POST")) {
                return POST_TASK;
            } else if (tokens[1].equals("tasks") && tokens[3].startsWith("?") && requestMethod.equals("DELETE")) {
                return DELETE_TASK_BY_ID;
            } else if (tokens[1].equals("tasks") && requestMethod.equals("DELETE")) {
                return DELETE_TASKS;
            } else if (tokens[1].equals("tasks") && tokens[2].equals("epic") && requestMethod.equals("GET")) {
                return GET_EPICS;
            } else if (tokens[1].equals("tasks") && tokens[2].equals("subtask") && requestMethod.equals("GET")) {
                return GET_SUBTASKS;
            } else if (tokens[4].endsWith("?") && requestMethod.equals("GET")) {
                return GET_SUBTASKS_OF_EPIC_BY_EPIC_ID;
            } else if (tokens[2].equals("history") && requestMethod.equals("GET")) {
                return GET_HISTORY;
            } else {
                return UNKNOWN;
            }
        }

        private void handleGetAnyTypeOfTaskByGivenId(HttpExchange exchange) throws IOException {
            Optional<Integer> intOpt = idFinder(exchange);
            if (intOpt.isEmpty()) {
                writeResponse(exchange, "wrong id format", 400);
                return;
            }
            int id = intOpt.get();
            try {
                for (Task task : managerTask.getTasks()) {
                    if (Objects.equals(task.getId(), id)) {
                        writeResponse(exchange, gson.toJson(task), 200);
                        return;
                    }
                }
                for (Epic epic : managerTask.getEpics()) {
                    if (Objects.equals(epic.getId(), id)) {
                        writeResponse(exchange, gson.toJson(epic), 200);
                        return;
                    }
                }
                for (Subtask subtask : managerTask.getSubTasks()) {
                    if (Objects.equals(subtask.getId(), id)) {
                        writeResponse(exchange, gson.toJson(subtask), 200);
                        return;
                    }
                }
            } catch (RuntimeException e) {
                writeResponse(exchange, "wrong " + id + " given", 404);
            }
        }

        private Optional<Integer> idFinder(HttpExchange exchange) {
            String[] tokens = exchange.getRequestURI().getPath().split("/");
            try {
                return Optional.of(Integer.parseInt(tokens[3].replaceAll("\\D+", "")));
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }

        private void handleGetTasks(HttpExchange exchange) throws IOException {
            writeResponse(exchange, gson.toJson(managerTask.getTasks()), 0);
        }

        private void handleGetEpics(HttpExchange exchange) throws IOException {
            writeResponse(exchange, gson.toJson(managerTask.getEpics()), 0);
        }

        private void handleGetSubtasks(HttpExchange exchange) throws IOException {
            writeResponse(exchange, gson.toJson(managerTask.getSubTasks()), 0);
        }

        private void handlePostTask(HttpExchange exchange) throws IOException {
            try (InputStream is = exchange.getRequestBody()) {
                String taskFromInputStream = new String(is.readAllBytes(), DEFAULT_CHARSET);
                Task task = gson.fromJson(taskFromInputStream, Task.class);
                TaskType typeOfTask = task.getType();
                switch (typeOfTask) {
                    case TASK -> managerTask.addNewTask(task);
                    case EPIC -> managerTask.addEpicId((Epic) task);
                    case SUBTASK -> {
                        Subtask subtask = (Subtask) task;
                        for (Epic epic : managerTask.getEpics()) {
                            if (Objects.equals(epic.getId(), subtask.getEpicId())) {
                                managerTask.addSubtaskId((Subtask) task);
                                return;
                            } else {
                                writeResponse(exchange, "no epic to add subtask", 400);
                            }
                        }
                    }
                    default -> throw new IllegalStateException("unexpected value: " + typeOfTask);
                }
            } catch (JsonSyntaxException e) {
                writeResponse(exchange, "wrong Json given", 400);
            }
        }

        private void handleDeleteAllTasks(HttpExchange exchange) throws IOException {
            managerTask.deleteAll();
            if (managerTask.getTasks().isEmpty()
                    && managerTask.getEpics().isEmpty()
                    && managerTask.getSubTasks().isEmpty()) {
                writeResponse(exchange, "all type of tasks are successfully deleted", 200);
            } else {
                writeResponse(exchange, "something wrong happened here, cant delete all tasks",
                        400);
            }
        }

        private void handleDeleteAnyTypeOfTaskByGivenId(HttpExchange exchange) throws IOException {
            Optional<Integer> intOpt = idFinder(exchange);
            if (intOpt.isEmpty()) {
                writeResponse(exchange, "wrong id format", 400);
                return;
            }
            long id = intOpt.get();
            try {
                for (Task task : managerTask.getTasks()) {
                    if (Objects.equals(task.getId(), id)) {
                        managerTask.removeTaskById(id);
                        writeResponse(exchange, "task with " + id + " was deleted", 200);
                        return;
                    }
                }
                for (Epic epic : managerTask.getEpics()) {
                    if (Objects.equals(epic.getId(), id)) {
                        managerTask.removeEpicById(managerTask.getEpicId(id));
                        writeResponse(exchange, "epic with " + id + " was deleted", 200);
                        return;
                    }
                }
                for (Subtask subtask : managerTask.getSubTasks()) {
                    if (Objects.equals(subtask.getId(), id)) {
                        managerTask.removeSubtaskById(managerTask.getSubtaskId(id));
                        writeResponse(exchange, "subtask with " + id + " was deleted", 200);
                        return;
                    }
                }
            } catch (RuntimeException e) {
                writeResponse(exchange, "wrong " + id + " given", 404);
            }
        }

        private void handleGetSubtasksOfEpicByGivenId(HttpExchange exchange) throws IOException {
            Optional<Integer> intOpt = idFinder(exchange);
            if (intOpt.isEmpty()) {
                writeResponse(exchange, "wrong id format", 400);
                return;
            }
            int id = intOpt.get();
            try {
                for (Epic epic : managerTask.epicsFull().values()) {
                    if (Objects.equals(epic.getId(), id)) {
                        writeResponse(exchange, gson.toJson(epic.getSubTaskIds()), 200);
                        return;
                    }
                }
            } catch (JsonSyntaxException e) {
                writeResponse(exchange, "wrong " + id + " given", 404);
            }
        }

        private void handleGetHistory(HttpExchange exchange) throws IOException {
            if (new InMemoryHistoryManager().getHistory().isEmpty()) {
                writeResponse(exchange, "history is empty", 200);
                return;
            }
            try {
                writeResponse(exchange, gson.toJson(new InMemoryHistoryManager().getHistory()), 200);
            } catch (JsonSyntaxException e) {
                writeResponse(exchange, "something going wrong" + e.getMessage(), 404);
            }
        }

        private void handleGetPrioritized(HttpExchange exchange) throws IOException {
            try {
                writeResponse(exchange, gson.toJson(managerTask.getPrioritizedTasks()), 200);
            } catch (JsonSyntaxException e) {
                writeResponse(exchange, "something going wrong" + e.getMessage(), 404);
            }
        }


        private String exchangeKey(HttpExchange exchange) {
            return exchange.getRequestURI().getQuery().substring(4);
        }

        private String extractPath(HttpExchange exchange) {
            return exchange.getRequestURI().getPath().substring(6);
        }
    }


    enum Endpoint {
        GET_PRIORITIZED_TASKS,
        GET_TASK_BY_ID,
        GET_TASKS,
        GET_EPICS,
        GET_SUBTASKS,
        POST_TASK,
        DELETE_TASKS,
        DELETE_TASK_BY_ID,
        GET_SUBTASKS_OF_EPIC_BY_EPIC_ID,
        GET_HISTORY,
        UNKNOWN
    }
}