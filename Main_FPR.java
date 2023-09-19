import java.io.File;
import java.io.FileNotFoundException;

public class Main_FPR {
    public static void main(String[] args) throws FileNotFoundException {
        File wk1_17 = new File("scores");
        File wk10_17 = new File("scoresWk10AndLater");
        File nextWeek = new File("nextWeek");
        File games;
        if(args.length > 0) {
            games = new File(args[0]);
        }
        else {
            games = wk10_17;
        }


        FPRCalculator fprCalculator = new FPRCalculator(games);
        fprCalculator.printAll();
        if(args.length > 1) {
            File predictors = new File(args[1]);
            fprCalculator.predict(predictors);
        }
    }
}
