package service;

import model.Epic;
import model.Subtask;
import model.Task;
import model.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTests {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    void testAddNewTask() {
        Task task = new Task("Test Task", "Description", 0, Status.NEW);
        final Task savedTask = taskManager.addTask(task);
        assertNotNull(savedTask, "Задача должна сохраниться.");
        assertEquals(task, savedTask, "Задачи должны быть равны.");
    }

    @Test
    void testTaskManagerFindsTasksById() {
        Task task1 = new Task("Task 1", "Description 1", 0, Status.NEW);
        Task task2 = new Task("Task 2", "Description 2", 0, Status.NEW);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        assertNotNull(taskManager.getTaskById(task1.getId()), "Поиск задачи 1");
        assertNotNull(taskManager.getTaskById(task2.getId()), "Поиск задачи 2");
    }

    @Test
    void testIdUniqueness() {
        Task task1 = new Task("Task 1", "Description 1", 0, Status.NEW);
        Task task2 = new Task("Task 2", "Description 2", 0, Status.NEW);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        assertNotEquals(task1.getId(), task2.getId(), "ID должны быть уникальными.");
    }

    @Test
    void testHistoryRecordsTaskChanges() {
        Task task = new Task("Task 1", "Description 1", 0, Status.NEW);
        taskManager.addTask(task);

        task.setStatus(Status.IN_PROGRESS);
        taskManager.updateTask(task);

        List<Task> history = taskManager.getHistory();

        assertEquals(1, history.size(), "В истории должна быть обновленная задача.");
        assertEquals(task, history.get(0), "История должна ссылаться на обновленную задачу.");
    }

    @Test
    void testDeleteTaskUpdatesEpic() {
        Epic epic = new Epic("Epic", "Description", 0, Status.NEW);
        Subtask subtask = new Subtask("Subtask", "Subtask description", 0, Status.NEW, epic.getId());

        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);

        taskManager.deleteSubtaskById(subtask.getId());

        List<Subtask> subtasks = taskManager.getSubtasksByEpicId(epic.getId());
        assertTrue(subtasks.isEmpty(), "Эпик не должен содержать подзадач после удаления.");
    }

    @Test
    void testEpicStatusWithNoSubtasks() {
        Epic epic = new Epic("Epic", "Description", 0, Status.NEW);
        taskManager.addEpic(epic);

        assertEquals(Status.NEW, taskManager.getEpicById(epic.getId()).getStatus(), "Статус эпика должен быть NEW с пустым списком подзадач.");
    }

    @Test
    void testEpicStatusWithAllNewSubtasks() {
        Epic epic = new Epic("Epic", "Description", 0, Status.NEW);
        taskManager.addEpic(epic);

        Subtask subtask1 = new Subtask("Subtask 1", "Description", 0, Status.NEW, epic.getId());
        Subtask subtask2 = new Subtask("Subtask 2", "Description", 0, Status.NEW, epic.getId());
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        assertEquals(Status.NEW, taskManager.getEpicById(epic.getId()).getStatus(), "Статус эпика должен быть NEW, если все подзадачи NEW.");
    }

    @Test
    void testEpicStatusWithInProgressSubtask() {
        Epic epic = new Epic("Epic", "Description", 0, Status.NEW);
        taskManager.addEpic(epic);

        Subtask subtask1 = new Subtask("Subtask 1", "Description", 0, Status.NEW, epic.getId());
        Subtask subtask2 = new Subtask("Subtask 2", "Description", 0, Status.IN_PROGRESS, epic.getId());
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        assertEquals(Status.IN_PROGRESS, taskManager.getEpicById(epic.getId()).getStatus(), "Статус эпика должен быть IN_PROGRESS, если есть хотя бы одна подзадача IN_PROGRESS.");
    }

    @Test
    void testEpicStatusWithNewAndDoneSubtasks() {
        Epic epic = new Epic("Epic", "Description", 0, Status.NEW);
        taskManager.addEpic(epic);

        Subtask subtask1 = new Subtask("Subtask 1", "Description", 0, Status.NEW, epic.getId());
        Subtask subtask2 = new Subtask("Subtask 2", "Description", 0, Status.DONE, epic.getId());
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        assertEquals(Status.IN_PROGRESS, taskManager.getEpicById(epic.getId()).getStatus(), "Статус эпика должен быть IN_PROGRESS, если есть подзадача NEW и подзадача DONE.");
    }

    @Test
    void testEpicStatusWithAllDoneSubtasks() {
        Epic epic = new Epic("Epic", "Description", 0, Status.NEW);
        taskManager.addEpic(epic);

        Subtask subtask1 = new Subtask("Subtask 1", "Description", 0, Status.DONE, epic.getId());
        Subtask subtask2 = new Subtask("Subtask 2", "Description", 0, Status.DONE, epic.getId());
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        assertEquals(Status.DONE, taskManager.getEpicById(epic.getId()).getStatus(), "Статус эпика должен быть DONE, если все подзадачи DONE.");
    }
}