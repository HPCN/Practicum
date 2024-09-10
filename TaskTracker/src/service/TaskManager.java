package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.List;

public interface TaskManager {
    Task addTask(Task task);
    Subtask addSubtask(Subtask subtask);
    Epic addEpic(Epic epic);
    void updateTask(Task task);
    void updateSubtask(Subtask subtask);
    void updateEpic(Epic epic);
    Task getTaskById(int id);
    Subtask getSubtaskById(int id);
    Epic getEpicById(int id);
    void deleteTaskById(int id);
    void deleteSubtaskById(int id);
    void deleteEpicById(int id);
    List<Task> getHistory();
    List<Subtask> getSubtasksByEpicId(int epicId);
    List<Task> getAllTasks();
    List<Subtask> getAllSubtasks();
    List<Epic> getAllEpics();
}