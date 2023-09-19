import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class FPRCalculator {
    File readingFile;
    ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
    ArrayList<Integer> scores = new ArrayList<>();
//    ArrayList<ArrayList<Integer>> matrixWithScore = new ArrayList<>();
//    ArrayList<Key<Integer>> teamsList = new ArrayList<>();
    private ArrayList<Key<String,Double>> OPR = new ArrayList<>();
    private ArrayList<Key<String,Double>> DPR = new ArrayList<>();
    private ArrayList<Key<String,Double>> FPR = new ArrayList<>();

    static ArrayList<String> teams = new ArrayList<>(Arrays.asList(
            "Minnesota Vikings","Detroit Lions","Baltimore Ravens","Pittsburgh Steelers","Cleveland Browns","Cincinnati Bengals","New York Jets","Buffalo Bills","Houston Texans","Dallas Cowboys",
            "Philadelphia Eagles","New York Giants","Jacksonville Jaguars","Tennessee Titans","Kansas City Chiefs","Denver Broncos","Carolina Panthers","Seattle Seahawks","Tampa Bay Buccaneers", "San Francisco 49ers",
            "Miami Dolphins","Los Angeles Chargers","New England Patriots","New Orleans Saints","Atlanta Falcons","Green Bay Packers","Washington Commanders","Las Vegas Raiders","Arizona Cardinals", "Indianapolis Colts",
            "Los Angeles Rams","Chicago Bears"));

    public FPRCalculator(File readingFile) throws FileNotFoundException {
        this.readingFile = readingFile;
        readFile(readingFile);
        findOPR();
    }
    public void readFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
//        ArrayList<Key<Integer>> teamsList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String next = scanner.nextLine();
            String[] nexts = next.split("\t");
//            System.out.println("reading, next is: "+next);
            int score1 = 0;
            int score2 = 0;
            int[] game1;
            int[] game2;
            String team1 = "";
            String team2 = "";
            if(nexts.length>5) {
                try {
                    team1 = nexts[1];
//                    System.out.println(nexts[0]);
//                    System.out.println(nexts[1]);
//                    System.out.println(nexts[2]);
//                    System.out.println(nexts[3]);
                    score1 = Integer.parseInt(nexts[2]);
                    team2 = nexts[3];
                    score2 = Integer.parseInt(nexts[4]);
                    //        System.out.println("finished scanning");
                    //        MatrixOperations.printArray(data);
                    System.out.println("team 1: " + team1 + " team 2: " + team2 + " score 1: " + score1 + " score 2: " + score2);
                    game1 = new int[65];
                    game1[teams.indexOf(team1)*2] = 1;
                    game1[teams.indexOf(team2)*2+1] = -1;
                    game1[64] = score1;
                    game2 = new int[65];
                    game2[teams.indexOf(team2)*2] = 1;
                    game2[teams.indexOf(team1)*2+1] = -1;
                    game2[64] = score2;
                    ArrayList<Integer> game1list = new ArrayList<>();
                    ArrayList<Integer> game2list = new ArrayList<>();
                    ArrayList<Integer> game1listNoScore = new ArrayList<>();
                    ArrayList<Integer> game2listNoScore = new ArrayList<>();
                    for (int i = 0; i < 65; i++) {
                        if(i==64) {
                            game1list.add(game1[i]);
                            game2list.add(game2[i]);
                            scores.add(game1[i]);
                            scores.add(game2[i]);
                        }
                        else {
                            game1list.add(game1[i]);
                            game2list.add(game2[i]);
                            game1listNoScore.add(game1[i]);
                            game2listNoScore.add(game2[i]);
                        }
                    }
//                    matrixWithScore.add(game1list);
//                    matrixWithScore.add(game2list);
                    matrix.add(game1listNoScore);
                    matrix.add(game2listNoScore);
                }
                catch(Exception e){
                    System.out.println("This game: "+"team 1: " + team1 + " team 2: " + team2 + " score 1: " + score1 + " score 2: " + score2 + "through this exception:");
                    System.out.println(e);
                    System.out.println();


                }
            }
            else {
                System.out.println("Wrong length");
            }
        }
        System.out.println("Done reading file");
    }


    public void findOPR(){
        //        System.out.println("Scores:");
//        MatrixOperations.printArray(scores);
//        System.out.println("Matrix:");
//        MatrixOperations.printMatrix(matrix);
//        System.out.println("MatrixWithScores:");
//        MatrixSolver.printMatrix(matrixWithScores);
        ArrayList<ArrayList<Integer>> transposedMatrix = new ArrayList<ArrayList<Integer>>(MatrixOperations.transposeMatrix(matrix));
//        System.out.println("Transposed matrix:");
//        MatrixOperations.printMatrix(transposedMatrix);
        ArrayList<ArrayList<Integer>> multipiedMatrix = new ArrayList<ArrayList<Integer>>(MatrixOperations.matrixMultiplyication(transposedMatrix, matrix));
//        System.out.println("Multiplied matrix:");
//        MatrixOperations.printMatrix(multipiedMatrix);
        ArrayList<Integer> multipiedVector = new ArrayList<Integer>(MatrixOperations.vectorMultiplication(transposedMatrix, scores));
//        System.out.println("Multiplied vector:");
//        MatrixOperations.printArray(multipiedVector);
        ArrayList<ArrayList<Integer>> augmentedMatrix = new ArrayList<ArrayList<Integer>>(multipiedMatrix);
        for (int i = 0; i < multipiedVector.size(); i++) {
            augmentedMatrix.get(i).add(multipiedVector.get(i));
        }

        System.out.println("Augmented matrix:");
        MatrixOperations.printMatrix(augmentedMatrix);

        double[][] aug = new double[augmentedMatrix.size()][augmentedMatrix.get(0).size()];
        for (int i = 0; i < augmentedMatrix.size(); i++) {
            for (int j = 0; j < augmentedMatrix.get(i).size(); j++) {
                aug[i][j] = augmentedMatrix.get(i).get(j);
            }
        }

        double[] sol= MatrixOperations.printSolvedAndTheRREF(aug);

        //Leveling off solutions to the lowest Defense is 0:
        double min = sol[0];
        for (int i = 1; i < sol.length; i++) {
            if(min>sol[i]){
                min = sol[i];
            }
        }
        for (int i = 0; i < sol.length; i++) {
            sol[i]-=min;
        }
        ArrayList<Double> solutions = new ArrayList<>();
        for (double solution : sol) {
            solutions.add(solution);
        }
//        LabeledSorts.bubblesort(solutionsList,teams);

        for (int i = 0; i < teams.size()*2; i++) {
            if(i%2==0){
//                System.out.println(teams.get(i/2)+" Offense\t"+sol[i]);
                Key<String,Double> offenseScore = new Key<>(teams.get(i/2),sol[i]);
                OPR.add(offenseScore);
            }
            else {
                Key<String,Double> defensiveScore = new Key<>(teams.get(i/2),sol[i]);
                DPR.add(defensiveScore);
//                System.out.println(teams.get(i/2)+" Defense\t"+sol[i]);
            }
        }
        System.out.println();
        for (int i = 0; i < teams.size()*2; i+=2) {
            Key<String,Double> totalScore = new Key<>(teams.get(i/2),sol[i]+sol[i+1]);
            FPR.add(totalScore);
//            System.out.println(teams.get(i/2)+" Total\t"+(sol[i]+sol[i+1]));
        }

        //Sorting
        LabeledSorts.bubblesort(OPR);
        LabeledSorts.bubblesort(DPR);
        LabeledSorts.bubblesort(FPR);
    }

    public void findAndPrintFPR() {
        findOPR();
        printAll();
//        System.out.println();
//        for (int i = 0; i < teams.size()*2; i++) {
//            if(i%2==0){
//                System.out.println("Team "+ teams.get(i/2)+" Offense got an OPR of "+sol[i]);
//            }
//            else {
//                System.out.println("Team "+ teams.get(i/2)+" Defense got an OPR of "+sol[i]);
//            }
//        }
//        System.out.println();
//        for (int i = 0; i < teams.size()*2; i+=2) {
//            System.out.println("Team "+ teams.get(i/2)+" Total got an OPR of "+(sol[i]+sol[i+1]));
//        }
//        System.out.println();
    }

    public void printAll(){
        System.out.println();
        printOPR();
        System.out.println();
        printDPR();
        System.out.println();
        printFPR();
    }

    public void printOPR(){
        for (Key<String,Double> team:OPR) {
            System.out.println(team.getIndex()+" Offense\t"+team.getValue());
        }
    }

    public void printDPR(){
        for (Key<String,Double> team:DPR) {
            System.out.println(team.getIndex()+" Defense\t"+team.getValue());
        }
    }

    public void printFPR(){
        for (Key<String,Double> team:FPR) {
            System.out.println(team.getIndex()+" Total\t"+team.getValue());
        }
    }

    public double findOPR(String team){
        for (Key<String,Double> score:OPR) {
            if(score.getIndex().equalsIgnoreCase(team)) return score.getValue();
        }
        throw new RuntimeException("Didnt Find Team");
    }
    public double findDPR(String team){
        for (Key<String,Double> score:DPR) {
            if(score.getIndex().equalsIgnoreCase(team)) return score.getValue();
        }
        throw new RuntimeException("Didnt Find Team");
    }


    public void predict(File games) throws FileNotFoundException {
        System.out.println("Predicting:");
        Scanner scanner = new Scanner(games);
        while(scanner.hasNextLine()){
            String next = scanner.nextLine();
            String[] nexts = next.split("\t");
//            System.out.println("reading, next is: "+next);
            if(nexts.length>5) {
                try {
                    double score1 = findOPR(nexts[1])-findDPR(nexts[3]);
                    double score2 = findOPR(nexts[3])-findDPR(nexts[1]);
                    System.out.println(nexts[1]+"\t"+score1+"\t"+nexts[3]+"\t"+score2);
                }
                catch (Exception ignored){

                }
            }
        }
    }

    /*private static Key<Integer> findTeam(ArrayList<Integer> data, ArrayList<Key<Integer>> teamsList, int i) {
        if(teamsList.size()>0) {
            for (int k = 0; k < teamsList.size(); k++) {
//                System.out.println("Checking team with number "+data.get(i)+" with team in list at "+k+" with number "+teamsList.get(k).getValue()+" btw the first minus the second is "+(data.get(i)-teamsList.get(k).getValue())+" and this is whethere they equal each other "+(Objects.equals(data.get(i), teamsList.get(k).getValue())));
                if (Objects.equals(data.get(i), teamsList.get(k).getValue())) {
//                    System.out.println("found team already in the list");
                    return teamsList.get(k);
                }
            }
            teamsList.add(new Key<Integer>(teamsList.size(),data.get(i)));
            return teamsList.get(teamsList.size()-1);
        }
        else{
            System.out.println("Team list was empty");
            teamsList.add(0,new Key<Integer>(data.get(i)));
            return teamsList.get(0);
        }
    }*/

}
