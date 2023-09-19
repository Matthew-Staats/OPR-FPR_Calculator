import java.util.ArrayList;

public class MatrixOperations {
    public static ArrayList<ArrayList<Integer>> matrixMultiplyication(ArrayList<ArrayList<Integer>> mat1, ArrayList<ArrayList<Integer>> mat2) {
        int rows1 = mat1.size();
        int columns1 = mat1.get(0).size();
        int rows2 = mat2.size();
        int columns2 = mat2.get(0).size();

        if (rows2 != columns1) {
            System.out.println("This multiplication is undefined");
            throw new RuntimeException("R2 was "+rows2+" and C1 was "+columns1+" which doesn't work");
        }
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        for (int i = 0; i < rows1; i++) { //For each row in the result we will make it here.
            result.add(new ArrayList<Integer>());
            for (int j = 0; j < columns2; j++) { //For each column in row in the result we will make it here.
                int individualResult = 0;
                for (int k = 0; k < columns1; k++) { //For each part of each square, if it has more than 1 component, add them here.
                    individualResult += mat1.get(i).get(k) * mat2.get(k).get(j);
                }
                result.get(i).add(individualResult);
            }
        }
        return result;
    }
    public static ArrayList<Integer> vectorMultiplication(ArrayList<ArrayList<Integer>> mat1, ArrayList<Integer> mat2) {
        int rows1 = mat1.size();
        int columns1 = mat1.get(0).size();
        int rows2 = mat2.size();
        int columns2 = 1;

        if (rows2 != columns1) {
            System.out.println("This multiplication is undefined");
            throw new RuntimeException("R2 was "+rows2+" and C1 was "+columns1+" which doesn't work");
        }
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < rows1; i++) { //For each row in the result we will make it here.
            for (int j = 0; j < columns2; j++) { //For each column in row in the result we will make it here.
                int individualResult = 0;
                for (int k = 0; k < columns1; k++) { //For each part of each square, if it has more than 1 component, add them here.
                    individualResult += mat1.get(i).get(k) * mat2.get(k);
                }
                result.add(individualResult);
            }
        }
        return result;
    }
    public static <E> ArrayList<ArrayList<E>> transposeMatrix(ArrayList<ArrayList<E>> matrix){
        int rows = matrix.size();
        int columns = matrix.get(0).size();
        ArrayList<ArrayList<E>> result = new ArrayList<ArrayList<E>>();
        for (int i = 0; i < columns; i++) {
            result.add(new ArrayList<E>());
            for (int j = 0; j < rows; j++) {
                result.get(i).add(matrix.get(j).get(i));
            }
        }
        return result;
    }
    public static double[][] RREF(double[][] matrix) {
        return RREFfromEFForm(EF_matrix(matrix));
    }
    public static void printRREF(double[][] matrix) {
        printMatrix(RREFfromEFForm(EF_matrix(matrix)));
    }
    public static double[][] RREFfromEFForm(double[][] EFmatrix)
    {
        int rows = EFmatrix.length;
        int columns = EFmatrix[0].length;
        for (int i = rows-1; i > (0); i--) {//i represents the current row
            for (int k = (i-1); k > -1; k--) {//k represents the current row being subtracted
                subtractArray(EFmatrix[k], EFmatrix[i], EFmatrix[k][i]);
            }
        }
        return EFmatrix;
    }
    public static double[] printSolved(double[][] matrix){
        double[] solution = solvedfromEFForm(EF_matrix(matrix));
        printArray(solution);
        return solution;
    }
    public static double[] printSolvedAndTheRREF(double[][] matrix){
        double[][] RREF = RREF(matrix);
        System.out.println("RREF of the matrix is:");
        printMatrix(RREF);
        System.out.println();
        double[] solution = solvedfromEFForm(RREF);
        System.out.println("Solution of the matrix is:");
        printArray(solution);
        return solution;
    }
    public static double[] solved(double[][] matrix){
        return solvedfromEFForm(EF_matrix(matrix));
    }
    public static double[] solvedfromEFForm(double[][] matrix)
    {
        int rows = matrix.length;
        int columns = matrix[0].length;
        double[] answers = new double[rows];
        for (int i = rows-1; i > (-1); i--) {
            double answer=0;
            double currentAnswer =matrix[i][columns-1];
//            System.out.println(currentAnswer+" row: "+i+" collumn: "+(columns-1));
            //j is the current column (starting at bottom right (but not answer one)
            for (int j = columns-2; j > (i); j--) {
                currentAnswer-=answers[j]*matrix[i][j]; //used to be answers[i+1]
//                System.out.println("j= "+(j)+" "+"Minusing: "+answers[j]+" times "+matrix[i][j]);
            }
            answer=currentAnswer;
            answers[i]=answer;
        }
        return answers;
    }
    public static double[][] EF_matrix(double[][] matrix)
    {
        int rows = matrix.length;
        int columns = matrix[0].length;
        for (int i = 0; i < (rows-1); i++) {
            //i is the current row (starting at 0)
            if(matrix[i][i]!=0) {
                divArray(matrix[i], matrix[i][i]); //Make the right most non zero constant 1;
            }
            //j is the current column (starting at 0)
            for (int j = 0; j < (i+1); j++) {
                subtractArray(matrix[i+1],matrix[j],matrix[i+1][j]);
            }
        }
        if(matrix[rows-1][rows-1]!=0) {
            divArray(matrix[rows - 1], matrix[rows - 1][rows - 1]); //Make the right most non zero constant 1;
        }
        return matrix;
    }
    public static double[] AddArray(double[] firstArray,double[] subtractingArray, double Constant)
    {
        for (int i = 0; i < firstArray.length; i++) {
            firstArray[i]+=subtractingArray[i]*Constant;
        }
        return firstArray;
    }
    public static double[] subtractArray(double[] firstArray,double[] subtractingArray, double Constant)
    {
        for (int i = 0; i < firstArray.length; i++) {
            firstArray[i]-=subtractingArray[i]*Constant;
        }
        return firstArray;
    }
    public static double[] divArray(double[] array,double div)
    {
        if (div==0.0)
        {
            //should throw an exception which will be caught and then do a row swap
            System.out.println("Diving by 0!!");
//            return new double[] bad;
        }
        for (int i = 0; i < array.length; i++) {
            array[i]/=div;
        }
        return array;
    }
    public static double[] MultiplyArray(double[] array,double Constant)
    {
        if (Constant==0.0) {
            System.out.println("Multiplying by 0!!");
        }
        for (int i = 0; i < array.length; i++) {
            array[i]*=Constant;
        }
        return array;
    }
    public static void printMatrix(double[][] matrix)
    {
        for (int i = 0; i < matrix.length; i++) {
            printArray(matrix[i]);
        }
    }
    public static void printArray(double[] array)
    {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]+" ");
        }
        System.out.println();

    }
    public static void printArray(int[] array)
    {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]+" ");
        }
        System.out.println();
    }
    public static void printMatrix(int[][] matrix)
    {
        for (int i = 0; i < matrix.length; i++) {
            printArray(matrix[i]);
        }
    }
    public static <E> void printArray(ArrayList<E> array)
    {
        for (int i = 0; i < array.size(); i++) {
            System.out.print(array.get(i)+" ");
        }
        System.out.println();
    }
    public static <E> void printMatrix(ArrayList<ArrayList<E>> matrix)
    {
        for (int i = 0; i < matrix.size(); i++) {
            printArray(matrix.get(i));
        }
        System.out.println();
    }
}
