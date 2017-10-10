package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class DistanceMatrix {

    public static void Distance() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File("testD.csv"));
        StringBuilder sb = new StringBuilder();
        float[][] distanceMatrix = generateDistanceMatrix();
        printMatrixToCSV(distanceMatrix, pw, sb);
        pw.write(sb.toString());
        pw.close();
    }


    public static float fillDistanceMatrix() {
        Random rand = new Random();
        float Result = rand.nextInt();
        return rand.nextFloat();
    }


    public static void printMatrixToCSV(float[][] matrix, PrintWriter pw, StringBuilder sb) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sb.append(matrix[i][j] + ";");
            }
            sb.append('\n');
        }
    }

    public static float[][] generateDistanceMatrix() {
        float[][] matrix = new float[100][100];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = fillDistanceMatrix();
                matrix[j][i] = matrix[i][j] ;
            }
        }
        return matrix;
    }
    




}