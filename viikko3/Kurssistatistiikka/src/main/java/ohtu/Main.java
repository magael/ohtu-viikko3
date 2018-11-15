package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.client.fluent.Request;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

        JsonParser parser = new JsonParser();

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

            System.out.println("\nYhteensä: " + tehtyYhteensa + "/" + kurssiTehtävia);

            JsonObject tilastoData = parsiData("https://studies.cs.helsinki.fi/courses/" + c + "/stats");
            int palautuksia = 0;
            int tehtavia = 0;
            int tunteja = 0;

            for (String avain : tilastoData.keySet()) {
                JsonObject tilastoAvain = tilastoData.getAsJsonObject(avain);
                palautuksia += tilastoAvain.get("students").getAsInt();
                tehtavia += tilastoAvain.get("exercise_total").getAsInt();
                tunteja += tilastoAvain.get("hour_total").getAsInt();
            }

            System.out.println("\nKurssilla yhteensä " + palautuksia 
                    + " palautusta. Palautettuja tehtäviä " + tehtavia + " kpl."
                    + " Aikaa käytetty yhteensä " + tunteja + " tuntia.");
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

    private static JsonObject parsiData(String url) throws IOException {
        String statsResponse = Request.Get(url).execute().returnContent().asString();

        JsonParser parser = new JsonParser();
        return parser.parse(statsResponse).getAsJsonObject();
    }
}
