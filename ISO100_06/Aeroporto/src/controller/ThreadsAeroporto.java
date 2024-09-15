package controller;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class ThreadsAeroporto implements Runnable {
    private static final Random random = new Random();
    private static final Semaphore pistaNorte = new Semaphore(1); 
    private static final Semaphore pistaSul = new Semaphore(1);  
    private static final Semaphore areaDecolagem = new Semaphore(2);
    private final int id;
    private final String pista;

    public ThreadsAeroporto(int id) {
        this.id = id;
        this.pista = random.nextBoolean() ? "Norte" : "Sul";
    }

    @Override
    public void run() {
        try {
            if (pista.equals("Norte")) {
                pistaNorte.acquire();
            } else {
                pistaSul.acquire();
            }

            System.out.println("Avião #" + id + " está na pista " + pista);

            areaDecolagem.acquire();
            System.out.println("Avião #" + id + " está na área de decolagem.");

            fazAcao("taxiar", 500, 1000);

            fazAcao("decolar", 600, 800);

            fazAcao("afastar", 300, 800);

            System.out.println("Avião #" + id + " completou a decolagem e afastamento.");
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (pista.equals("Norte")) {
                pistaNorte.release();
            } else {
                pistaSul.release();
            }
            areaDecolagem.release();
        }
    }

    private void fazAcao(String acao, int minTempo, int maxTempo) throws InterruptedException {
        int tempo = random.nextInt(maxTempo - minTempo + 1) + minTempo;
        System.out.println("Avião #" + id + " está " + acao + " (durante " + tempo + " ms).");
        Thread.sleep(tempo);
    }
}