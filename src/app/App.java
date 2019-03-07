package app;

import java.io.IOException;
import java.util.Scanner;

import core.Mapa;

public class App {

    public static void main(String[] args) {

        System.out.println("Bem-vindo ao jogo de labirinto!\nEscolha o nome do arquivo de mapa:");

        Scanner scanner = new Scanner(System.in);

        String nomeDigitado = scanner.nextLine();

        Mapa mapa = new Mapa(nomeDigitado);

        try {
            mapa.loadMapa();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        scanner.close();
        
    }

}