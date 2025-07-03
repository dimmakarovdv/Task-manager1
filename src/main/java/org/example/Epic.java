package org.example;

public class Epic extends Task {
    protected String[] subtasks; // Исправлено: массив строк вместо строки

    public Epic(int id, String[] subtasks) {
        super(id);
        this.subtasks = subtasks;
    }

    public String[] getSubtasks() { // Тип возвращаемого значения исправлен
        return subtasks;
    }

    @Override
    public boolean matches(String query) {
        if (subtasks == null) return false;
        for (String subtask : subtasks) {
            if (subtask != null && subtask.contains(query)) {
                return true;
            }
        }
        return false;
    }
}
