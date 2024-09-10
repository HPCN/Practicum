package service;

import model.Task;
import model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTests {
    private HistoryManager historyManager;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void testAddHistory() {
        Task task1 = new Task("Task 1", "Description 1", 1, Status.NEW);
        historyManager.add(task1);

        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не должна быть пустой.");
        assertEquals(1, history.size(), "В истории должна быть хотя бы одна задача");
        assertEquals(task1, history.get(0), "История должна содержать задачу 1");
    }

    @Test
    void testHistoryMaxSize() {
        for (int i = 0; i < 15; i++) {
            historyManager.add(new Task("Task " + i, "Description", i, Status.NEW));
        }
        List<Task> history = historyManager.getHistory();
        assertEquals(10, history.size(), "Размер истории ограничен 10");
    }

    @Test
    void testRemoveFromHistory() {
        Task task1 = new Task("Task 1", "Description 1", 1, Status.NEW);
        Task task2 = new Task("Task 2", "Description 2", 2, Status.NEW);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(1); // удалить task1

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "После удаления в истории должна остаться только одна задача.");
        assertEquals(task2, history.get(0), "В истории должна остаться только задача 2.");
    }
}
