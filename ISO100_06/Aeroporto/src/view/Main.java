package view;

import controller.ThreadsAeroporto;

public class Main {
    private static final int NUM_AVIOES = 12;

    public static void main(String[] args) {
        Thread[] avioes = new Thread[NUM_AVIOES];

        for (int i = 0; i < NUM_AVIOES; i++) {
            ThreadsAeroporto aviao = new ThreadsAeroporto(i + 1);
            avioes[i] = new Thread(aviao);
        }

        for (Thread aviao : avioes) {
            aviao.start();
        }

        for (Thread aviao : avioes) {
            try {
                aviao.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}