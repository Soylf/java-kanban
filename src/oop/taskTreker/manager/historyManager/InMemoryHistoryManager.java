package oop.taskTreker.manager.historyManager;

import oop.taskTreker.task.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Long,Node> nodeMap = new HashMap<>();
    private Node first;
    private Node last;


    @Override
     public ArrayList<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void add(Task task) {
        if (task != null) {
            remove(task.getId());
            customLinkLast(task);
            nodeMap.put(task.getId(), last);
        }
    }

    private void customLinkLast(Task task) {
        Node node = new Node(task, last, null);
        if (first == null) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
    }
    @Override
    public void remove(long id) {
        Node node = nodeMap.remove(id);
        if(node == null) {
            return;
        }
        removeNode(node);
    }

    public void removeNode(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
            if (node.next == null) {
                last = node.prev;
            } else {
                node.next.prev = node.prev;
            }
        } else {
            first = node.next;
            if (first == null) {
                last = null;
            } else {
                first.prev = null;
            }
        }
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        Node node = first;
        while (node != null) {
            tasks.add(node.task);
            node = node.next;
        }
        return tasks;
    }


    public static class Node {
        Task task;
        Node prev; // предыдущий
        Node next; // следующий

        public Node(Task task, Node first, Node last) {
            this.task = task;
            this.prev = first;
            this.next = last;
        }
    }
}
