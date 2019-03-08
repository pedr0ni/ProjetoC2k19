package app;

import java.io.IOException;
import java.util.Scanner;

import core.Mapa;

public class App {

    public static void main(String[] args) {

        System.out.println("Bem-vindo ao jogo de labirinto!\nEscolha o nome do arquivo de mapa:");

        Scanner scanner = new Scanner(System.in); // Cria o Scanner

        String nomeDigitado = scanner.nextLine(); // Recebe um valor
        Mapa mapa = new Mapa(nomeDigitado); // Cria um novo Mapa

        try {
            mapa.loadMapa(); // Carrega o mapa
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(mapa);

        scanner.close(); // Fecha o Scanner
        
    }

}