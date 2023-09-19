import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class OPRCalculator {

    public static void findAndPrintOPR(ArrayList<ArrayList<Integer>> matrix, ArrayList<Integer> scores, ArrayList<Key<Integer,Integer>> teamsList) {
        System.out.println("Scores:");
        MatrixOperations.printArray(scores);
        System.out.println("Matrix:");
        MatrixOperations.printMatrix(matrix);
//        System.out.println("MatrixWithScores:");
//        MatrixSolver.printMatrix(matrixWithScores);
        ArrayList<ArrayList<Integer>> transposedMatrix = new ArrayList<ArrayList<Integer>>(MatrixOperations.transposeMatrix(matrix));
        System.out.println("Transposed matrix:");
        MatrixOperations.printMatrix(transposedMatrix);
        ArrayList<ArrayList<Integer>> multipiedMatrix = new ArrayList<ArrayList<Integer>>(MatrixOperations.matrixMultiplyication(transposedMatrix, matrix));
        System.out.println("Multiplied matrix:");
        MatrixOperations.printMatrix(multipiedMatrix);
        ArrayList<Integer> multipiedVector = new ArrayList<Integer>(MatrixOperations.vectorMultiplication(transposedMatrix, scores));
        System.out.println("Multiplied vector:");
        MatrixOperations.printArray(multipiedVector);
        System.out.println("List of teams:");
        MatrixOperations.printArray(teamsList);
        ArrayList<ArrayList<Integer>> augmentedMatrix = new ArrayList<ArrayList<Integer>>(multipiedMatrix);
        for (int i = 0; i < multipiedVector.size(); i++) {
            augmentedMatrix.get(i).add(multipiedVector.get(i));
        }
        System.out.println("Augmented matrix:");
        MatrixOperations.printMatrix(augmentedMatrix);
        ArrayList<ArrayList<Integer>> rowreducedMatrix = new ArrayList<ArrayList<Integer>>();
        double[][] aug = new double[augmentedMatrix.size()][augmentedMatrix.get(0).size()];
        for (int i = 0; i < augmentedMatrix.size(); i++) {
            for (int j = 0; j < augmentedMatrix.get(i).size(); j++) {
                aug[i][j] = augmentedMatrix.get(i).get(j);
            }
        }

        double[] solutions= MatrixOperations.printSolvedAndTheRREF(aug);
        System.out.println();
        System.out.println("Just Team Numbers and OPR for easy copy paste:");
        for (int i = 0; i < teamsList.size(); i++) {
            System.out.println(teamsList.get(i).getValue()+" "+solutions[i]);
        }
        System.out.println();
        for (int i = 0; i < teamsList.size(); i++) {
            System.out.println("Team "+ teamsList.get(i).getValue()+" got an OPR of "+solutions[i]);
        }
    }

    public static void readFile(int teams, File file,ArrayList<ArrayList<Integer>> matrix,ArrayList<Integer> score,ArrayList<ArrayList<Integer>> matrixWithScore,ArrayList<Key<Integer,Integer>> teamsList) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        ArrayList<Integer> data = new ArrayList<>();
//        ArrayList<Key<Integer>> teamsList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String next = scanner.nextLine();
            String[] nexts = next.split("\t");
//            System.out.println("reading, next is: "+next);
//            int i = 0;
//            if(next.split(" ")[0].equalsIgnoreCase("quals")){
//                System.out.println("New Section");
//                i+=2;
//            }
            for (int i = 0; i < nexts.length; i++) {
                try {
                    int value = Integer.parseInt(nexts[i]);
//                System.out.println("value is: "+value);
                    data.add(value);
                }
                catch (Exception e) {
//                i++;
//                System.out.println("caught new Section");
                }
            }
        }
//        System.out.println("finished scanning");
        MatrixOperations.printArray(data);


        //Generate list of all the teams and scores:
        for (int i = 0; i < data.size(); i+=(2*teams+2)) { //each match
            score.add(data.get(i+2*teams));
            score.add(data.get(i+2*teams+1));
            for (int j = i; j < i+teams; j++) {
                Key<Integer,Integer> team = findTeam(data, teamsList, j);
//                System.out.println("Team: "+team+" played with score "+score.get(game));
            }
            for (int j = i+teams; j < i+2*teams; j++) {
                Key<Integer,Integer> team = findTeam(data, teamsList, j);
//                System.out.println("Team: "+team+" played with score "+score.get(game+1));
            }
        }
        MatrixOperations.printArray(teamsList);


        int game = 0;
        for (int i = 0; i < data.size(); i+=(2*teams+2)) { //each match
            ArrayList<Key<Integer,Integer>> blueteamsForAMatch = new ArrayList<>();
            ArrayList<Key<Integer,Integer>> redTeamsForAMatch = new ArrayList<>();
            for (int j = i; j < i+teams; j++) {
                Key<Integer,Integer> team = findTeam(data, teamsList, j);
                redTeamsForAMatch.add(team);
                System.out.println("Team: "+team+" played with score "+score.get(game));
            }
            for (int j = i+teams; j < i+2*teams; j++) {
                Key<Integer,Integer> team = findTeam(data, teamsList, j);
                blueteamsForAMatch.add(team);
                System.out.println("Team: "+team+" played with score "+score.get(game+1));
            }

            ArrayList<Integer> row = new ArrayList<Integer>();
            ArrayList<Integer> fullRow = new ArrayList<Integer>();
            outer:
            for (int j = 0; j < teamsList.size(); j++) {
                boolean skip = false;
                for (int k = 0; k < redTeamsForAMatch.size(); k++) {
                    if (row.size() == redTeamsForAMatch.get(k).getIndex()) {
//                        row.add(redTeamsForAMatch.get(k).getValue());
//                        fullRow.add(redTeamsForAMatch.get(k).getValue());
                        row.add(1);
                        fullRow.add(1);
                        continue outer;
//                        skip = true;
                    }
                }
                if(!skip) {
                    row.add(0);
                    fullRow.add(0);
                }
            }
            matrix.add(row);
            fullRow.add(score.get(game));
            matrixWithScore.add(fullRow);


            ArrayList<Integer> row2 = new ArrayList<Integer>(teamsList.size());
            ArrayList<Integer> fullRow2 = new ArrayList<Integer>(teamsList.size()+1);
            outer:
            for (int j = 0; j < teamsList.size(); j++) {
//                System.out.println("teams size "+teamsList.size());
                boolean skip = false;
                for (int k = 0; k < blueteamsForAMatch.size(); k++) {
                    if(row2.size()==blueteamsForAMatch.get(k).getIndex()){
//                      row2.add(blueteamsForAMatch.get(k).getValue());
//                      fullRow2.add(blueteamsForAMatch.get(k).getValue());
                        row2.add(1);
                        fullRow2.add(1);
                        continue outer;
//                      skip = true;
                    }
                }
                if(!skip) {
                    row2.add(0);
                    fullRow2.add(0);
                }
            }
            matrix.add(row2);
            fullRow2.add(score.get(game+1));
            matrixWithScore.add(fullRow2);

            game+=2;
        }
    }

    private static Key<Integer,Integer> findTeam(ArrayList<Integer> data, ArrayList<Key<Integer,Integer>> teamsList, int i) {
        if(teamsList.size()>0) {
            for (int k = 0; k < teamsList.size(); k++) {
//                System.out.println("Checking team with number "+data.get(i)+" with team in list at "+k+" with number "+teamsList.get(k).getValue()+" btw the first minus the second is "+(data.get(i)-teamsList.get(k).getValue())+" and this is whethere they equal each other "+(Objects.equals(data.get(i), teamsList.get(k).getValue())));
                if (Objects.equals(data.get(i), teamsList.get(k).getValue())) {
//                    System.out.println("found team already in the list");
                    return teamsList.get(k);
                }
            }
            teamsList.add(new Key<Integer,Integer>(teamsList.size(),data.get(i)));
            return teamsList.get(teamsList.size()-1);
        }
        else{
            System.out.println("Team list was empty");
            //starting at 1 instead of 0 so Integer doesn't think it's null ever:
            teamsList.add(0,new Key<Integer,Integer>(0,data.get(i)));
            return teamsList.get(0);
        }
    }
}