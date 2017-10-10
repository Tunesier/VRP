package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ServiceMatrix {

    public static void Service() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File("testS.csv"));
        StringBuilder sb = new StringBuilder();
        float [][] serviceMatrix = generateServiceMatrix();
        printMatrixToCSV(serviceMatrix, pw, sb);
        pw.write(sb.toString());
        pw.close();
    }

   // public static int fillServiceMatrix() {
    //    Random rand = new Random();
    //    int Result;
    //    int low = 10;
    //   int High = 20;
    //   for (Result = 10; Result < 20; Result += 10){
    //      Result = rand.nextInt(High, low);

    //   IntStream stream = random.ints(High, low);
    //  stream.fillServiceMatrix(System.out::println);

    //   return (Result);
    //   }

    //public static int fillServiceMatrix(int Low, int High) {
    //    Low = 10;
    //    High = 20;
    //   return (
     //           new Random().nextInt(11)+10
     //  );
    //}
         public static int fillServiceMatrix() {
             int randomNum = (ThreadLocalRandom.current().nextInt(1, 2)+ 1)*10;
            return randomNum;
    }





    //Random rand = new Random();
    //float Result = rand.nextInt();
    //       return rand.nextFloat();





    public static void printMatrixToCSV(float[][] matrix, PrintWriter pw, StringBuilder sb) {
        for(int k = 0; k < matrix.length; k++) {
            for(int l = 0; l < matrix[k].length; l++) {
                sb.append(matrix[l][k] + ";");
            }
            sb.append('\n');
        }
    }

    private static float[][] generateServiceMatrix () {
        float[][] matrix= new float[10][10];

        for (int k = 0; k < matrix.length; k++) {
            for (int l = 0; l < matrix[k].length; l++) {
                matrix[k][l] = fillServiceMatrix();
                //matrix[l][k] = matrix[k][l];
            }
        }
        return matrix;
    }

}