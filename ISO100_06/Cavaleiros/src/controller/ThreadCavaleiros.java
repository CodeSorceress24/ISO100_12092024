package controller;

import java.util.concurrent.Semaphore;


public class ThreadCavaleiros extends Thread {
		
	int idCavaleiro;
	Semaphore tochaPega;
	Semaphore pedraPega;
	static boolean tocha = false;	
	static boolean pedra = false;	
	private int anda;	
	static boolean [] portas = new boolean [4];
	final int PORTA_CORRETA;

	public ThreadCavaleiros (int idCavaleiro, Semaphore tochaPega, Semaphore pedraPega, int portaCorreta) {
		this.idCavaleiro = idCavaleiro;
		this.tochaPega = tochaPega;
		this.pedraPega = pedraPega;
		this.PORTA_CORRETA = portaCorreta;
	}
	
	
	public void run () {
		cavaleirosAndando();
		
	}
	
	
	public void cavaleirosAndando () {
		
		int distanciaTotal = 2000;
		this.anda = (int)((Math.random() * 3) + 2);
		int distanciaPercorrida = 0;
		int tempo = 50;
		
		while (distanciaPercorrida < distanciaTotal) {
			distanciaPercorrida += anda;
			
			if (distanciaPercorrida >= 500 && !tocha) {
				pegarTocha();
				
			}
						
			if (distanciaPercorrida >= 1000 && !pedra) {
				pegarPedra();
				
			}
			
			try {
				sleep(tempo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("O cavaleiro #" + idCavaleiro + " já andou: " + distanciaPercorrida + "m.");
	}
		escolhaPorta(idCavaleiro, PORTA_CORRETA);
}
		
	
	
	public void pegarTocha () {
		try {
			tochaPega.acquire();
			System.out.println(idCavaleiro + " pegou a tocha.");
			this.anda += 2;
			this.tocha = true;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			tochaPega.release();
		}
	}
	
	public void pegarPedra() {
		
		try {
			pedraPega.acquire();
			System.out.println(idCavaleiro + " pegou a pedra.");
			this.anda += 2;
			this.pedra = true;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			pedraPega.release();
		}
	}	
	
	public void escolhaPorta (int idCavaleiro, int PORTA_CORRETA) {
		
		int portaEscolhida = (int)((Math.random() * 4));	
		
		while (portas[portaEscolhida]) {
			portaEscolhida = (int)((Math.random() * 4));	
		}
		
		
			portas [portaEscolhida] = true ;
		
			System.out.println("O #" + idCavaleiro + " cavaleiro está diante das 4 portas e precisa fazer uma escolha!");			
		
			System.out.println("Ele escolheu a porta: " + portaEscolhida);
		
		
		if (portaEscolhida == PORTA_CORRETA) {
			System.out.println("Parabéns! O cavaleiro terminou sua caminhada e sobreviveu.");
		} else {
			System.out.println("O cavaleiro escolheu a porta que havia um monstro dentro e foi devorado... :(");
		}
	}
	
}