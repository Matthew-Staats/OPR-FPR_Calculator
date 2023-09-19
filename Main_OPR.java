import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main_OPR {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
        ArrayList<Integer> scores = new ArrayList<>();
        ArrayList<ArrayList<Integer>> matrixWithScores = new ArrayList<>();
        ArrayList<Key<Integer,Integer>> teamsList = new ArrayList<>();


        int teams; //ex: 2
        File file; //ex: "ftc_state"
        /* *
         * Teams = 2 for FTC and 3 for FRC because it's just the amount of teams per alliance per match
         * Enter the File you want to analyze the data for.
         * matches2 & matches2_2 are FRC files, ftc_state, matches1 & matches1_2.txt are FTC files
         * */
        if(args.length > 0) {
            teams = Integer.parseInt(args[0]); //ex: 2
            file = new File(args[1]); //ex: "ftc_state"
        }
        else {
            teams = 2;
            file = new File("ftc_state");
        }
//        oprCalculator.readFile(2,file,matrix,scores,matrixWithScores,teamsList);
        OPRCalculator.readFile(teams,file,matrix,scores,matrixWithScores,teamsList);
        OPRCalculator.findAndPrintOPR(matrix, scores, teamsList);
    }
}
