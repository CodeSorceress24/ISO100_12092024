package controller;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Atleta implements Runnable {
    private static final Random random = new Random();
    private static final int DISTANCIA_CORRIDA = 3000; 
    private static final int DISTANCIA_CICLISMO = 5000; 
    private static final Semaphore SEMAFORO_ARMA = new Semaphore(5);

    private final int id;
    private int tempoCorrida;
    private int tempoCiclismo;
    private int pontosTiros;
    private int ordemChegada;

    public Atleta(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
 
            tempoCorrida = simulaCorrida();
            System.out.println("Atleta #" + id + " completou a corrida em " + tempoCorrida + " ms.");

            pontosTiros = realizaTiros();

            tempoCiclismo = simulaCiclismo();
            System.out.println("Atleta #" + id + " completou o ciclismo em " + tempoCiclismo + " ms.");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int simulaCorrida() throws InterruptedException {
        int velocidade = random.nextInt(6) + 20; 
        int tempo = (DISTANCIA_CORRIDA / velocidade) * 30; 
        Thread.sleep(tempo);
        return tempo;
    }

    private int realizaTiros() throws InterruptedException {
        int pontos = 0;
        for (int i = 0; i < 3; i++) {
            SEMAFORO_ARMA.acquire();
            int tempoTiro = random.nextInt(2500) + 500; 
            Thread.sleep(tempoTiro);
            pontos += random.nextInt(11);
            SEMAFORO_ARMA.release();
        }
        return pontos;
    }

    private int simulaCiclismo() throws InterruptedException {
        int velocidade = random.nextInt(11) + 30;
        int tempo = (DISTANCIA_CICLISMO / velocidade) * 40;
        return tempo;
    }

    public int getId() {
        return id;
    }

    public int getTempoTotal() {
        return tempoCorrida + tempoCiclismo;
    }

    public int getPontosTotal() {
        return 250 - (ordemChegada - 1) * 10 + pontosTiros;
    }

    public void setOrdemChegada(int ordem) {
        this.ordemChegada = ordem;
    }
}