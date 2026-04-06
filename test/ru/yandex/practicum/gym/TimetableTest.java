package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        assertEquals(1, mondaySessions.size()); //Проверить, что за понедельник вернулось одно релевантное занятие
        assertTrue(mondaySessions.contains(singleTrainingSession));

        assertEquals(0, tuesdaySessions.size()); //Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayIfSeveralSessionsStartAtSameTime() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayChildTrainingSession);

        // Проверить, что за четверг вернулось два занятия в одно время
        List<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(2, thursdaySessions.size());
        assertTrue(thursdaySessions.contains(thursdayAdultTrainingSession));
        assertTrue(thursdaySessions.contains(thursdayChildTrainingSession));
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, mondaySessions.size());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        List<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(2, thursdaySessions.size());
        assertEquals(thursdayChildTrainingSession,thursdaySessions.get(0));
        assertEquals(thursdayAdultTrainingSession, thursdaySessions.get(1));

        // Проверить, что за вторник не вернулось занятий
        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertEquals(0, tuesdaySessions.size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно релевантное занятие
        List<TrainingSession> monday13 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        assertEquals(1, monday13.size());
        assertEquals(singleTrainingSession, monday13.get(0));

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        List<TrainingSession> monday14 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        assertEquals(0, monday14.size());
    }

    //Проверить, что за четверг в 20:00 вернулось два занятия
    @Test
    void testGetTrainingSessionsForDayAndTimeIfSeveralSessionsStartAtSameTime() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayChildTrainingSession);

        // Проверить, что за четверг вернулось два занятия в одно время
        List<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        assertEquals(2, thursdaySessions.size());
        assertTrue(thursdaySessions.contains(thursdayAdultTrainingSession));
        assertTrue(thursdaySessions.contains(thursdayChildTrainingSession));
    }

    @Test
    void testGetCountersOfTrainingsIfEmpty() {
        Timetable timetable = new Timetable();

        //Проверить, что возвращается пустой список, если тренировок нет
        List<CounterOfTrainings> listOfCoaches = timetable.getCountByCoaches();
        assertEquals(0, listOfCoaches.size());
    }

    @Test
    void testGetCountersOfTrainingsForSingleSession() {
        Timetable timetable = new Timetable();
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что возвращается список из одного тренера и количество тренировок для него
        List<CounterOfTrainings> listOfCoaches = timetable.getCountByCoaches();
        assertEquals(1, listOfCoaches.size());
        assertEquals(coach, listOfCoaches.get(0).getCoach());
        assertEquals(1, listOfCoaches.get(0).getCounter());
    }

    @Test
    void testGetCountersOfTrainingsForMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach1 = new Coach("Шварценеггер", "Арнольд", "Иванович");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        Group groupChildIron = new Group("Штанга для самых маленьких", Age.CHILD, 60);
        TrainingSession sundayChildIronTrainingSession = new TrainingSession(groupChildIron, coach1,
                DayOfWeek.SUNDAY, new TimeOfDay(12, 0));

        timetable.addNewTrainingSession(sundayChildIronTrainingSession);

        // Проверить, что появился список из двух тренеров, проверить порядок вывода и количество тренировок
        List<CounterOfTrainings> listOfCoaches = timetable.getCountByCoaches();

        assertEquals(2, listOfCoaches.size());

        assertEquals(coach, listOfCoaches.get(0).getCoach());
        assertEquals(4, listOfCoaches.get(0).getCounter());

        assertEquals(coach1, listOfCoaches.get(1).getCoach());
        assertEquals(1, listOfCoaches.get(1).getCounter());
    }

}