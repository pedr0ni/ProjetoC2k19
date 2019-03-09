package app.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

/**
 * Classe Mapa do Labirinto
 * Projeto Integrado C
 * @author Matheus Pedroni 18079020 (github.com/pedr0ni)
 * @author Amanda
 * @since 2019
 */
public class Mapa {

    /* Atributos do arquivo */
    private String nome;
    private int altura;
    private int largura;

    /* Atributos do mapa */
    private char[][] labirinto;

    /**
     * Construtor da classe
     * @param String Nome do arquivo do mapa
     */
    public Mapa (String nome) 
    {
        this.nome = nome;
    }

    /**
     * Carrega o mapa do labirinto
     * @throws IOException (Caso o arquivo não seja encontrado)
     * @throws NumberFormatException (Caso a primeira linha não seja um número)
     * @throws Exception (Caso o número da largura ou altura do arquivo esteja errado)
     * @see https://docs.oracle.com/javase/7/docs/api/java/io/File.html
     */
    public void loadMapa() 
    throws IOException, NumberFormatException, Exception 
    {
    	
    	URL resource = this.getClass().getResource(this.nome.toLowerCase());
    	
    	/* Verifica se o arquivo existe */
        if (resource == null) 
        {
            throw new IOException("O arquivo " + this.nome + " não existe.");
        }
        
        /* Carrega as informações do arquivo (Não o conteudo) */
        File file = new File(resource.getPath());

        /* Cria o leitor de arquivos em buffer */
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;

        int index = 0; // Número da linha do arquivo
        int indexLabirinto = 0; // Número da linha da matriz do mapa

        while ((line = reader.readLine()) != null) 
        {
            /* Linha 0: Tem que ser o número total de linhas do mapa */
            if (index == 0) 
            {
                try {
                    this.altura = Integer.parseInt(line); // Recebe o valor da linha e converte para int
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("A primeira linha precisa ser o número da altura");
                }
            }
            /* Linha 1: Apenas vê o tamanho da string para definir o limite do tamanho das colunas da matriz */
            if (index == 1) 
            {
                this.largura = line.length();

                /* Tendo definido os dois limites (altura e largurra [linhas e colunas]) da matriz é possível reservar o espaço para receber os valores do mapa */
                this.labirinto = new char[this.altura][this.largura];
            }

            /* Recebe o valor do arquivo de texto e coloca dentro da matriz do mapa */
            if (index >= 1) 
            {

                /* Verifica se a linha está dentro do limite de colunas da matriz */
                if (line.length() != this.largura) 
                {
                    throw new Exception("A largura é diferente de " + this.largura);
                }

                /* Para cada linha é preciso correr cada char da string e adicionar na matriz */
                for (int i = 0; i < line.length(); i++) 
                {
                    this.labirinto[indexLabirinto][i] = line.charAt(i);
                }

                indexLabirinto++; // Incrementa a linha da matriz
            }

            index++; // Incrementa a linha do arquivo
        }

        /**
         * Verifica se o total de linhas ultrapassa o limite definido de altura
         * O número da linha (index) tem que ser subtraido 1 pois ele conta a linha 0 (que define o tamanho da altura)
         */
        if (index - 1 != this.altura) 
        {
            throw new Exception("O número de linhas é diferente de " + this.altura);
        }

        reader.close(); // Fecha o leitor do arquivo
    }
    
    /**
     * Altura do Labirinto
     * @return int Altura
     */
    public int getAltura() {
    	return this.altura;
    }
    
    /**
     * Largura do Labirinto
     * @return int Largura
     */
    public int getLargura() {
    	return this.largura;
    }

    /**
     * Nome do Labirinto
     * @return String Nome
     */
    public String getNome() 
    {
        return this.nome;
    }

    /**
     * Mostra as infos. do mapa do labirinto
     * @return String Infos.
     */
    public String toString() 
    {
        System.out.println("Labirinto " + this.nome);
        String mapa = "Total de Linhas: " + this.altura + "\nTotal de colunas: " + this.largura;
        return mapa;
    }

}