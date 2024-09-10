import model.Epic;
import model.Subtask;
import model.Task;
import model.Status;
import service.TaskManager;
import service.Managers;
import service.InMemoryTaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task("Задача 1", "Описание задачи 1", 0, Status.NEW);
        task1 = taskManager.addTask(task1);

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", 0, Status.NEW, task1.getId());
        subtask1 = taskManager.addSubtask(subtask1);

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1", 0, Status.NEW);
        epic1 = taskManager.addEpic(epic1);

        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", 0, Status.NEW, epic1.getId());
        subtask2 = taskManager.addSubtask(subtask2);

        System.out.println("История просмотров: " + taskManager.getHistory());

        if (task1 != null) {
            System.out.println("Задача: " + task1.getName());
        }
        if (subtask1 != null) {
            System.out.println("Подзадача: " + subtask1.getName());
        }
        if (epic1 != null) {
            System.out.println("Эпик: " + epic1.getName());
        }

        task1.setStatus(Status.IN_PROGRESS);
        taskManager.updateTask(task1);
        taskManager.updateEpic(epic1);

        System.out.println("Обновленный статус задачи: " + taskManager.getTaskById(task1.getId()).getStatus());
        System.out.println("Обновленный статус эпика: " + taskManager.getEpicById(epic1.getId()).getStatus());

        taskManager.deleteTaskById(task1.getId());
        taskManager.deleteSubtaskById(subtask1 != null ? subtask1.getId() : -1);

        System.out.println("После удаления задач, эпик остается: " + taskManager.getEpicById(epic1.getId()).getName());

        System.out.println("История просмотров после операций: " + taskManager.getHistory());
    }
}
// хуйня ебаная