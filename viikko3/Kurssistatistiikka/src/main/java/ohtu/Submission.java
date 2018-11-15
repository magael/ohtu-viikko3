package ohtu;

import java.util.ArrayList;

public class Submission {
    private int week;
    private int hours;
    private ArrayList<Integer> exercises;
    private String course;

    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeek() {
        return week;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getHours() {
        return hours;
    }

    public void setExercises(ArrayList<Integer> exercises) {
        this.exercises = exercises;
    }

    public ArrayList<Integer> getExercises() {
        return exercises;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return course + ", viikko" + week
                + ": Tehtyjä tehtäviä yhteensä " + exercises.size()
                + ". Aikaa kului " + hours + " tuntia. "
                + "Tehdyt tehtävät: " + exercisesAsString();
    }

    public String exercisesAsString() {
        String exerciceString = "";
        for (int i = 0; i < exercises.size() - 1; i++) {
            exerciceString += exercises.get(i).toString() + ", ";
        }
        exerciceString += exercises.get(exercises.size() - 1) + ".";
        return exerciceString;
    }
    
}