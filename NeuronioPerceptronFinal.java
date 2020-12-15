package neuronioPerceptron;

import java.util.Scanner;

public class NeuronioPerceptronFinal {

	public static void main(String[] args) {
		int entradaA = 1;
		int entradaB = 1;
		double pesoA = 0.0;
		double pesoB = 0.8;
		double saidaDesejada = 2;
		double taxaAprendizagem = 0.05;
		double saidaNeuronio = 0;
		double erro = 0;

// Treinamento do neur�nio
		for (int i = 0; i < 5; i++) {
			saidaNeuronio = (entradaA * pesoA) + (entradaB * pesoB);
			erro = saidaDesejada - saidaNeuronio;
			pesoA = pesoA + (taxaAprendizagem * erro * entradaA);
			pesoB = pesoB + (taxaAprendizagem * erro * entradaB);

			saidaDesejada = saidaDesejada + 2;
			entradaA++;
			entradaB++;
		}

// Teste do neur�nio
		System.out.println("Vamos testar se o neur�nio aprendeu ap�s uma �poca:");
		float[] arrayTeste = new float[11];
		int entradaTeste = 1;
		float saidaNeuronioTeste = 0;
		int n = 0;

		do {
			saidaNeuronioTeste = (entradaTeste * (float) pesoA) + (entradaTeste * (float) pesoB);
			arrayTeste[n] = entradaTeste;
			arrayTeste[n + 1] = saidaNeuronioTeste;
			System.out.printf("%d + %d = %.2f\n", entradaTeste, entradaTeste, arrayTeste[n + 1]);
			entradaTeste++;
			n++;
		} while (n < 10);

	}

}
