package core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Mapa {

    private String nome;

    private int altura;
    private int largura;

    private char[][] labirinto;

    public Mapa (String nome) {
        this.nome = nome;
    }

    public void loadMapa() throws IOException,NumberFormatException,Exception {
        
        File file = new File(this.nome);
        if (!file.exists()) { // Verifica se o arquivo existe
            throw new IOException("O arquivo " + this.nome + " não existe.");
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        int index = 0;
        int indexLabirinto = 0;
        while ((line = reader.readLine()) != null) {
            if (index == 0) {
                try {
                    this.altura = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("A primeira linha precisa ser o número da altura");
                }
            }
            if (index == 1) {
                this.largura = line.length();
                this.labirinto = new char[this.altura][this.largura];
            }
            if (line.length() != this.largura) {
                throw new Exception("A largura é diferente de " + this.largura);
            }
            if (index >= 1) {
                for (int i = 0; i < line.length(); i++) {
                    this.labirinto[indexLabirinto][i] = line.charAt(i);
                }
                indexLabirinto++;
            }

            index++;
        }

        if (index != this.altura) {
            throw new Exception("O número de linhas é diferente de " + this.altura);
        }

        reader.close();
    }

    public String getNome() {
        return this.nome;
    }

    public String toString() {
        System.out.println("Labirinto " + this.nome);
        String mapa = "";
        
        return mapa;

    }

}