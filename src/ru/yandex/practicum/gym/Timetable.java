package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        //сначала сохраняем в нужный день, если нет - создаём
        TreeMap<TimeOfDay, List<TrainingSession>> mapOfSessionsPerDay =
                timetable.computeIfAbsent(trainingSession.getDayOfWeek(), k -> new TreeMap<>());
        //потом в нужное время, если нет списка - создаём
        mapOfSessionsPerDay.computeIfAbsent(trainingSession.getTimeOfDay(),
                k -> new ArrayList<>()).add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> mapOfTrainingsToday = timetable.get(dayOfWeek);
        List<TrainingSession> listOfTrainingsToday = new ArrayList<>();
        if (mapOfTrainingsToday != null) {
            for (Map.Entry<TimeOfDay, List<TrainingSession>> entry : mapOfTrainingsToday.entrySet()) {
                listOfTrainingsToday.addAll(entry.getValue());
            }
        }
        return listOfTrainingsToday;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> mapOfTrainingsToday = timetable.get(dayOfWeek);
        List<TrainingSession> listOfTrainingsTodayOnTime = new ArrayList<>();
        if (mapOfTrainingsToday != null) {
            List<TrainingSession> temporaryListOfTrainingsTodayOnTime = mapOfTrainingsToday.get(timeOfDay);
            if (temporaryListOfTrainingsTodayOnTime != null) {
                //для безопасности возвращаем копию списка, а не сам список
                listOfTrainingsTodayOnTime = new ArrayList<>(temporaryListOfTrainingsTodayOnTime);
            }
        }
        return listOfTrainingsTodayOnTime;
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        HashMap<Coach, Integer> listOfCoachesWithCounter = new HashMap<>();
        for (Map.Entry<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> setOfSessionsPerDay : this.timetable.entrySet()) {
            TreeMap<TimeOfDay, List<TrainingSession>> mapOfTrainingsToday = setOfSessionsPerDay.getValue();
            for (Map.Entry<TimeOfDay, List<TrainingSession>> setOfSessionsAtTime : mapOfTrainingsToday.entrySet()) {
                List<TrainingSession> temporaryListOfTrainingsTodayOnTime = setOfSessionsAtTime.getValue();
                for (TrainingSession trainingSession : temporaryListOfTrainingsTodayOnTime) {
                    Coach currentCoach = trainingSession.getCoach();
                    int count = listOfCoachesWithCounter.getOrDefault(currentCoach, 0);
                    listOfCoachesWithCounter.put(currentCoach, count + 1);
                }
                }
            }
        List<CounterOfTrainings> countersOfTrainings = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : listOfCoachesWithCounter.entrySet()) {
            countersOfTrainings.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }
        Collections.sort(countersOfTrainings);
        return countersOfTrainings;
    }
}