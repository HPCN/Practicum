import model.Task;
import model.Status;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTests {

    @Test
    void testTaskEqualityById() {
        Task task1 = new Task("Task 1", "Description 1", 1, Status.NEW);
        Task task2 = new Task("Task 2", "Description 2", 1, Status.NEW);

        assertEquals(task1, task2, "Задачи с одинаковым ID равны.");
    }

    @Test
    void testTaskInequalityByDifferentId() {
        Task task1 = new Task("Task 1", "Description 1", 1, Status.NEW);
        Task task2 = new Task("Task 2", "Description 2", 2, Status.NEW);

        assertNotEquals(task1, task2, "Задачи с разными ID не равны.");
    }
}
