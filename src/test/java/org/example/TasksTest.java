package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TasksTest {

    @Test
    public void testSimpleTaskMatches() {
        SimpleTask task = new SimpleTask(1, "Купить хлеб и молоко");

        boolean[] actual = {
                task.matches("Купить"),
                task.matches("хлеб"),
                task.matches("молоко"),
                task.matches(" "),
                task.matches("хлеб и"),

                task.matches("Хлеб"),
                task.matches("сыр"),
                task.matches(""),
                task.matches(null)
        };
        boolean[] expected = {
                true, true, true, true, true,
                false, false, false, false
        };
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testSimpleTaskWithNullTitle() {
        SimpleTask task = new SimpleTask(1, null);

        boolean[] actual = {
                task.matches("Любой"),
                task.matches(""),
                task.matches(null)
        };

        boolean[] expected = {false, false, false};
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

                epic.matches("МАлоко"),
                epic.matches("Сыр"),
                epic.matches(""),
                epic.matches(null)
        };
        boolean[] expected = {
                true, true, true,
                false, false, false, false
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
    public void testEpicWithNullArray() {
        Epic epic = new Epic(1, null);

        boolean[] actual = {
                epic.matches("Любой"),
                epic.matches(""),
                epic.matches(null)
        };

        boolean[] expected = {false, false, false};
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testEpicWithMixedSubtasks() {
        String[] subtasks = {null, "Задача", ""};
        Epic epic = new Epic(1, subtasks);

        Assertions.assertTrue(epic.matches("Задача"));
        Assertions.assertFalse(epic.matches(""));
        Assertions.assertFalse(epic.matches(null));
        Assertions.assertFalse(epic.matches("Пусто"));
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
    public void testMeetingWithNullFields() {
        Meeting meeting = new Meeting(1, null, null, "2025-07-03");

        boolean[] actual = {
                meeting.matches("Любой"),
                meeting.matches("Тема"),
                meeting.matches("Проект")
        };

        boolean[] expected = {false, false, false};
        Assertions.assertArrayEquals(expected, actual);
    }
    @Test
    public void testMeetingWithPartialNullFields() {
        Meeting meeting1 = new Meeting(1, "Тема", null, "2025-07-03");
        Meeting meeting2 = new Meeting(2, null, "Проект", "2025-07-03");

        Assertions.assertTrue(meeting1.matches("Тема"));
        Assertions.assertFalse(meeting1.matches("Проект"));

        Assertions.assertTrue(meeting2.matches("Проект"));
        Assertions.assertFalse(meeting2.matches("Тема"));
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
    @Test
    public void testEpicWithNullSubtasks() {
        String[] subtasks = {"Молоко", null, "Яйца"};
        Epic epic = new Epic(1, subtasks);

        boolean[] actual = {
                epic.matches("Молоко"),
                epic.matches("Хлеб"),
                epic.matches("Яйца"),
                epic.matches(null)
        };

        boolean[] expected = {true, false, true, false};
        Assertions.assertArrayEquals(expected, actual);
    }
    @Test
    public void testTodosWithDuplicateTasks() {
        SimpleTask task = new SimpleTask(1, "Задача");
        Todos todos = new Todos();
        todos.add(task);
        todos.add(task);

        Task[] result = todos.findAll();
        Assertions.assertEquals(2, result.length);
        Assertions.assertEquals(task, result[0]);
        Assertions.assertEquals(task, result[1]);
    }

    @Test
    public void testSearchWithPartialMatchInEpic() {
        String[] subtasks = {"Частичное совпадение"};
        Epic epic = new Epic(1, subtasks);
        Todos todos = new Todos();
        todos.add(epic);

        Task[] result = todos.search("части");
        Assertions.assertEquals(1, result.length);
    }

    @Test
    public void testSearchReturnsMultipleTasks() {
        // Создаем задачи
        SimpleTask task1 = new SimpleTask(1, "Купить молоко и хлеб");
        Epic task2 = new Epic(2, new String[]{"Молоко", "хлеб"});
        Meeting task3 = new Meeting(3, "Обсуждение хлеба", "Продукты", "13:00");

        // Создаем менеджер и добавляем задачи
        Todos todos = new Todos();
        todos.add(task1);
        todos.add(task2);
        todos.add(task3);

        // Ищем по запросу
        Task[] result = todos.search("хлеб");

        // Проверяем результаты
        Assertions.assertEquals(3, result.length, "Должны найти 3 задачи");
        Assertions.assertArrayEquals(new Task[]{task1, task2, task3}, result);
    }

    @Test
    public void testSearchReturnsSingleTask() {

        SimpleTask task1 = new SimpleTask(1, "Купить хлеб");
        Epic task2 = new Epic(2, new String[]{"Молоко", "сметана"});
        Meeting task3 = new Meeting(3, "Обсуждение хлеба", "Проект", "13:00");

        Todos todos = new Todos();
        todos.add(task1);
        todos.add(task2);
        todos.add(task3);

        Task[] result = todos.search("сметана");

        Assertions.assertEquals(1, result.length, "Должна найтись 1 задача");
        Assertions.assertEquals(task2, result[0]);
    }

    @Test
    public void testSearchReturnsNoTasks() {
        // Создаем задачи
        SimpleTask task1 = new SimpleTask(1, "Купить хлеб");
        Epic task2 = new Epic(2, new String[]{"Молоко", "хлеб"});
        Meeting task3 = new Meeting(3, "Совещание хлеба", "Проект", "13:00");

        Todos todos = new Todos();
        todos.add(task1);
        todos.add(task2);
        todos.add(task3);

        Task[] result = todos.search("сыр");

        Assertions.assertEquals(0, result.length, "Не должно быть найденных задач");
    }
}