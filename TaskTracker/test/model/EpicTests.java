package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EpicTests {

    @Test
    void testEpicCannotAddSelfAsSubtask() {
        Epic epic = new Epic("Epic 1", "Description of Epic 1", 1, Status.NEW);
        assertThrows(IllegalArgumentException.class, () -> {
            epic.addSubtaskId(epic.getId());
        }, "Эпик не может добавлять себя в качестве подзадачи.");
    }
}