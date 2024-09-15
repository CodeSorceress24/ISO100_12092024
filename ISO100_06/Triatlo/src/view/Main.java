package view;

import controller.Atleta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    private static final int NUM_ATLETAS = 25;

    public static void main(String[] args) {
        List<Atleta> atletas = new ArrayList<>();
        for (int i = 1; i <= NUM_ATLETAS; i++) {
            atletas.add(new Atleta(i));
        }

        List<Thread> threads = new ArrayList<>();
        for (Atleta atleta : atletas) {
            Thread thread = new Thread(atleta);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(atletas, Comparator.comparingInt(Atleta::getTempoTotal));

        for (int i = 0; i < atletas.size(); i++) {
            atletas.get(i).setOrdemChegada(i + 1);
        }

        Collections.sort(atletas, Comparator.comparingInt(Atleta::getPontosTotal).reversed());

        System.out.println("Classificação Final:");
        for (Atleta atleta : atletas) {
            System.out.println("Atleta #" + atleta.getId() + " - Pontuação Total: " + atleta.getPontosTotal());
        }
    }
}