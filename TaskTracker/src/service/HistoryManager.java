package service;

import model.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);
    void remove(int id); // добавлен новый метод для удаления задачи из истории
    List<Task> getHistory();
}