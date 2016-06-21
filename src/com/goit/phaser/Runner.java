package com.goit.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.stream.IntStream;

public class Runner {

    public static void main(String[] args) {
        long result = 0L;
        Random random = new Random();
        int countOfElements = 773;
        int countOfThreads = 13;
        int[] array = new int[countOfElements];

        IntStream.range(0, countOfElements).forEach (i -> array[i] = random.nextInt(100) );

        result = new PhaserImpl().getSquareSum(array, countOfThreads);
        System.out.println (" Total result = " + result);
    }
}
