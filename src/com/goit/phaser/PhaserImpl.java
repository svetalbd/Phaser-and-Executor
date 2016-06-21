package com.goit.phaser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * Created by Svitlana on 21.06.2016.
 */
public class PhaserImpl implements SquareSum {

    public long getSquareSum(int[] values, int numberOfThreads) {
        Long result = 0L;
        int countOfElements = values.length / numberOfThreads;

        Phaser phaser = new Phaser (numberOfThreads);

        List<Callable<Long>> callable = new ArrayList<>();
        IntStream.range(0, numberOfThreads).forEach (i -> callable.add(() -> {
            String name = Thread.currentThread().getName();
            System.out.println (name + " is beginning calculation ");

            long resultOfThread = 0L;
            int firstIndex = i * countOfElements;
            int lastIndex;
            if (i == (numberOfThreads - 1)) {
                lastIndex = values.length;
            } else{
                lastIndex = (i + 1) * countOfElements;
            }

            for (int j = firstIndex; j < lastIndex; j++) {
                resultOfThread += Math.pow (values[j], 2);
            }
            System.out.println("First index is " + firstIndex);
            System.out.println("Last index is " + (lastIndex - 1));

            System.out.println ("Result of counting of " + name + " = " + resultOfThread);
            phaser.arriveAndAwaitAdvance ();
            return resultOfThread;
        }));


        ExecutorService executor = Executors.newCachedThreadPool();
        try {
            List<Future<Long>> sum = executor.invokeAll(callable);
            for (Future<Long> f: sum) {
                result = result + f.get ();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace ();
        } finally {
            executor.shutdown ();
        }
        return result;
    }
}
