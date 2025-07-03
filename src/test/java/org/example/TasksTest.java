package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TasksTest {

    @Test
    public void testSimpleTaskMatches() {
        SimpleTask task = new SimpleTask(1, "Купить хлеб и молоко");
        boolean[] actual = { // Проверка различных сценариев поиска
                task.matches("Купить"),
                task.matches("хлеб"),
                task.matches("молоко"),
                task.matches(" "),
                task.matches("хлеб и молоко"),

                task.matches("Хлеб"),
                task.matches("сыр"),
                task.matches("хле"),
                task.matches(""),
                task.matches(null)
        };
        boolean[] expected = {
                true, true, true, true, true,
                false, false, false, false, false
        };
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testEpicMatches() {
        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(2, subtasks);

        boolean[] actual = {
                epic.matches("Молоко"),
                epic.matches("Яйца"),
                epic.matches("Хлеб"),
                epic.matches(" "),

                epic.matches("молоко"),
                epic.matches("Сыр"),
                epic.matches("Яйцо"),
                epic.matches(""),
                epic.matches(null)
        };
        boolean[] expected = {
                true, true, true, true,
                false, false, false, false, false
        };
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testEpicWithEmptySubtasks() {
        Epic epic = new Epic(3, new String[0]);

        boolean[] actual = {
                epic.matches("Любой"),
                epic.matches(""),
                epic.matches(null)
        };
        boolean[] expected = {false, false, false};
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testMeetingMatches() {
        Meeting meeting = new Meeting(
                4,
                "Выкатка 3й версии приложения",
                "Приложение НетоБанка",
                "Во вторник после обеда"
        );

        boolean[] actual = {
                // Должны совпадать (topic)
                meeting.matches("Выкатка"),
                meeting.matches("3й"),
                meeting.matches(" "),

                meeting.matches("Приложение"),
                meeting.matches("НетоБанка"),

                meeting.matches("13:00"),
                meeting.matches("во"),
                meeting.matches("посл"),
                meeting.matches(""),
                meeting.matches(null)
        };

        boolean[] expected = {
                true,   // "Выкатка"
                true,   // "3й"
                true,   // " "
                true,   // "Приложение"
                true,   // "НетоБанка"
                false,  // "13:00"
                false,  // "во"
                false,  // "посл"
                false,  // ""
                false   // null
        };
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testMeetingWithEmptyFields() {
        Meeting meeting = new Meeting(1, "", "", "2025-07-03");

        boolean[] actual = {
                meeting.matches("Любой"),
                meeting.matches(""),
                meeting.matches(null),
                meeting.matches("2026")
        };

        boolean[] expected = {false, false, false, false};
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testAllTasksWithSpecialCharacters() {
        SimpleTask simpleTask = new SimpleTask(6, "Email: dimmakarovdv89@mail.ru");
        boolean[] simpleActual = {
                simpleTask.matches("dimmakarovdv89@mail.ru"),
                simpleTask.matches("Email:"),
                simpleTask.matches("@")
        };
        boolean[] simpleExpected = {true, true, true};
        Assertions.assertArrayEquals(simpleExpected, simpleActual);

        Epic epic = new Epic(7, new String[]{"$10", "50% discount"});
        boolean[] epicActual = {
                epic.matches("$10"),
                epic.matches("50%"),
                epic.matches("%")
        };
        boolean[] epicExpected = {true, true, true};
        Assertions.assertArrayEquals(epicExpected, epicActual);

        Meeting meeting = new Meeting(8, "C#", "C++", "Now");
        boolean[] meetingActual = {
                meeting.matches("C#"),
                meeting.matches("C++"),
                meeting.matches("#")
        };
        boolean[] meetingExpected = {true, true, true};
        Assertions.assertArrayEquals(meetingExpected, meetingActual);
    }
}