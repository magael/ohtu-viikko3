package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.client.fluent.Request;

public class Main {

    public static void main(String[] args) throws IOException {
        // ÄLÄ laita githubiin omaa opiskelijanumeroasi
        String studentNr = "012345678";
        if (args.length > 0) {
            studentNr = args[0];
        }

        String palautusUrl = "https://studies.cs.helsinki.fi/courses/students/" + studentNr + "/submissions";
        String infoUrl = "https://studies.cs.helsinki.fi/courses/courseinfo";

        String palautusBodyText = Request.Get(palautusUrl).execute().returnContent().asString();
        String infoBodyText = Request.Get(infoUrl).execute().returnContent().asString();

        Gson mapper = new Gson();
        Submission[] subs = mapper.fromJson(palautusBodyText, Submission[].class);
        Info[] info = mapper.fromJson(infoBodyText, Info[].class);

        System.out.println("Opiskelijanumero: " + studentNr);

        ArrayList<String> courses = getCourses(subs);

        for (String c : courses) {
            ArrayList<Integer> tehtaviaYhteensa = new ArrayList<>();
            int tehtyYhteensa = 0;
            
            for (Info i : info) {
                if (i.getCourse().equals(c)) {
                    System.out.println('\n' + i.getFullName() + '\n');
                    tehtaviaYhteensa = i.getExercises();
                    break;
                }
            }
            
            for (Submission submission : subs) {
                if (submission.getCourse().equals(c)) {
                    System.out.println("Viikko" + submission.getWeek() + ": ");
                    System.out.println("Tehtyjä tehtäviä yhteensä "
                            + submission.getExercises().size()
                            + "/" + tehtaviaYhteensa.get(submission.getWeek())
                            + ". Aikaa kului " + submission.getHours() + " tuntia. "
                            + "Tehdyt tehtävät: " + submission.exercisesAsString());
                    tehtyYhteensa += submission.getExercises().size();
                }
            }
            
            int kurssiTehtävia = 0;
            for (Integer i : tehtaviaYhteensa) {
                kurssiTehtävia += i;
            }
            
            // Mallitulostuksessa otettu huomioon vain ne viikot, joista ko.
            //  opiskelija on tehnyt palautuksen. Mielestäni kuitenkin 
            //  kokonaismäärä tulisi laskea mukaanlukien viikot, joilta ei ole 
            //  vielä palautusta.
            System.out.println("\nYhteensä: " + tehtyYhteensa + "/" + kurssiTehtävia);
        }
    }

    private static ArrayList<String> getCourses(Submission[] subs) {
        ArrayList<String> courses = new ArrayList<>();

        for (Submission submission : subs) {
            if (!courses.contains(submission.getCourse())) {
                courses.add(submission.getCourse());
            }
        }

        return courses;
    }
}
