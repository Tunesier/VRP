package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;


/**
 * @author Jihed Draouil Jiheddraouil@gmail.de
 */


public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        new test(new int[]{1200, 500}, 20, 10).setVisible(true);
        ServiceMatrix.Service();
        DistanceMatrix.Distance();
        Random rand = new Random();
    }
}