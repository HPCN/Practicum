package model;

import java.util.HashSet;
import java.util.Set;

public class Epic extends Task {
    private Set<Integer> subtaskIds;

    public Epic(String name, String description, int id, Status status) {
        super(name, description, id, status);
        this.subtaskIds = new HashSet<>();
    }

    public void addSubtaskId(int subtaskId) {
        subtaskIds.add(subtaskId);
    }

    public Set<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void update(String name, String description) {
        setName(name);
        setDescription(description);
    }
}