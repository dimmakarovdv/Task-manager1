package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TodosTest {
    @Test
    void shouldAddThreeTasksOfDifferentType() {
        SimpleTask simpleTask = new SimpleTask(5, "Позвонить родителям");
        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(55, subtasks);
        Meeting meeting = new Meeting(
                555,
                "Выкатка 3й версии приложения",
                "Приложение НетоБанка",
                "Во вторник после обеда"
        );

        Todos todos = new Todos();
        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] expected = {simpleTask, epic, meeting};
        Task[] actual = todos.findAll();
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void testSearchWhenFewTasksMatch() {
        SimpleTask simpleTask = new SimpleTask(1, "Купить Хлеб");
        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(2, subtasks);
        Meeting meeting = new Meeting(3, "Хлеб", "Пироги", "Завтра");

        Todos todos = new Todos();
        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] expected = {simpleTask, epic, meeting};
        Task[] actual = todos.search("Хлеб");
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void testSearchWhenNoMatches() {
        SimpleTask simpleTask = new SimpleTask(1, "Купить Хлеб");
        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(2, subtasks);
        Meeting meeting = new Meeting(3, "Выкатка 3й версии приложения", "Приложение НетоБанка", "Во вторник после обеда");

        Todos todos = new Todos();
        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] expected = {};
        Task[] actual = todos.search("Сахар");
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void testSearchInEmptyManager() {
        Todos todos = new Todos();
        Task[] expected = {};
        Task[] actual = todos.search("Запрос");
        Assertions.assertArrayEquals(expected, actual);
    }
    @Test
    void testSearchWithEmptyQuery() {
        SimpleTask simpleTask = new SimpleTask(1, "Купить Хлеб");
        Todos todos = new Todos();
        todos.add(simpleTask);

        Task[] expected = {};
        Task[] actual = todos.search("");
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void testSearchWithNullQuery() {
        SimpleTask simpleTask = new SimpleTask(1, "Купить Хлеб");
        Todos todos = new Todos();
        todos.add(simpleTask);

        Task[] expected = {};
        Task[] actual = todos.search(null);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void testSearchWithSingleMatch() {
        SimpleTask task1 = new SimpleTask(1, "Купить Хлеб");
        SimpleTask task2 = new SimpleTask(2, "Купить Молоко");
        Todos todos = new Todos();
        todos.add(task1);
        todos.add(task2);

        Task[] expected = {task2};
        Task[] actual = todos.search("Молоко");
        Assertions.assertArrayEquals(expected, actual);
    }
}