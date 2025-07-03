package org.example;

public class SimpleTask extends Task {
    protected String title;

    public SimpleTask(int id, String title) {
        super(id);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean matches(String query) {
        if (query == null || query.isEmpty()) {
            return false;
        }
        return title != null && title.contains(query);
    }
}