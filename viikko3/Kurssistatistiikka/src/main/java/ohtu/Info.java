package ohtu;

import java.util.ArrayList;

public class Info {
    
    private String name;
    private String fullName;
    private ArrayList<Integer> exercises;

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getFullName() {
        return fullName;
    }

    public void setCourse(String course) {
        this.name = course;
    }

    public String getCourse() {
        return name;
    }

    public void setExercises(ArrayList<Integer> exercises) {
        this.exercises = exercises;
    }

    public ArrayList<Integer> getExercises() {
        return exercises;
    }
}
