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
    private File file;
    private int altura;
    private int largura;

    /* Atributos do mapa */
    private char[][] labirinto;

    private Coordenada entrada;
    private Coordenada saida;

    /**
     * Construtor da classe
     * @param String Nome do arquivo do mapa
     */
    public Mapa (File file) 
    {
        this.file = file;
    }

    /**
     * Carrega o mapa do labirinto
     * @throws IOException (Caso o arquivo n√£o seja encontrado)
     * @throws NumberFormatException (Caso a primeira linha n√£o seja um n√∫mero)
     * @throws Exception (Caso o n√∫mero da largura ou altura do arquivo esteja errado)
     * @see https://docs.oracle.com/javase/7/docs/api/java/io/File.html
     */
    public void loadMapa() 
    throws IOException, NumberFormatException, Exception 
    {
    	
    	if (this.file == null) {
    		throw new Exception("Arquivo inv·lido.");
    	}
    	
    	/* Verifica se o arquivo existe */
        if (!this.file.exists()) 
        {
            throw new IOException("O arquivo " + this.file.getName() + " n„o existe.");
        }
        
        if (!this.file.getName().endsWith(".txt")) {
        	throw new Exception("O arquivo precisa ser do tipo .txt");
        }

        /* Cria o leitor de arquivos em buffer */
        BufferedReader reader = new BufferedReader(new FileReader(this.file));

        String line;

        int index = 0; // N˙mero da linha do arquivo
        int indexLabirinto = 0; // N˙mero da linha da matriz do mapa

        while ((line = reader.readLine()) != null) 
        {
            /* Linha 0: Tem que ser o n√∫mero total de linhas do mapa */
            if (index == 0) 
            {
                try {
                    this.altura = Integer.parseInt(line); // Recebe o valor da linha e converte para int
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("A primeira linha precisa ser o n√∫mero da altura");
                }
            }
            /* Linha 1: Apenas lÍ o tamanho da string para definir o limite do tamanho das colunas da matriz */
            if (index == 1) 
            {
                this.largura = line.length();

                /* Tendo definido os dois limites (altura e largurra [linhas e colunas])
                 * da matriz È possÌvel reservar o espaÁo para receber os valores do mapa 
                 */
                this.labirinto = new char[this.altura][this.largura];
            }

            /* Recebe o valor do arquivo de texto e coloca dentro da matriz do mapa */
            if (index >= 1) 
            {

                /* Verifica se a linha est√° dentro do limite de colunas da matriz */
                if (line.length() != this.largura) 
                {
                    throw new Exception("A largura È diferente de " + this.largura);
                }

                /* Para cada linha È preciso correr cada char da string e adicionar na matriz */
                for (int i = 0; i < line.length(); i++) 
                {
                	/* Instancia uma coordenada para a posiÁ„o atual (indexLabirinto e i) */
                	Coordenada currentPos = Coordenada.valueOf(indexLabirinto, i);
                	char here = line.charAt(i); // Pega o char na posiÁ„o
                	
                	/* Verifica se o char È valido */
                	if (!isValid(here) || (isWall(currentPos) && here == ' ')) {
                		throw new Exception("Char invalido em (" + currentPos.getX() + "," + currentPos.getY() + ")");
                	}
                	
                	/* Procura por uma entrada (char == 'E') */
                	if (Character.toLowerCase(here) == 'e' && this.entrada == null && isWall(currentPos)) {
                		this.entrada = currentPos;
                	} else if (Character.toLowerCase(here) == 'e' && !isWall(currentPos)) {
                		/* Se a entrada n„o for em uma parede lanÁa uma exception */
                		throw new Exception("Entrada localizada fora de uma parede. (" + currentPos.getX() + "," + currentPos.getY() + ")");
                	}
                	
                	/* Procura por uma saida (char == 'S') */
                	if (Character.toLowerCase(here) == 's' && this.saida == null && isWall(currentPos)) {
                		this.saida = currentPos;
                	} else if (Character.toLowerCase(here) == 's' && !isWall(currentPos)) {
                		/* Caso a saida n„o for em uma parede lanÁa uma exception */
                		throw new Exception("Saida localizada fora de uma parede.");
                	}
                    this.labirinto[currentPos.getX()][currentPos.getY()] = here; // Salva o char na memÛria
                }

                indexLabirinto++; // Incrementa a linha da matriz
            }

            index++; // Incrementa a linha do arquivo
        }

        /**
         * Verifica se o total de linhas ultrapassa o limite definido de altura
         * O n√∫mero da linha (index) tem que ser subtraido 1 pois ele conta a linha 0 (que define o tamanho da altura)
         */
        if (index - 1 != this.altura) 
        {
            throw new Exception("O n˙mero de linhas È diferente de " + this.altura);
        }
        
        /* Verifica se est· faltando uma entrada */
        if (this.entrada == null) {
        	throw new Exception("Est· faltando uma entrada no mapa.");
        }
        
        /* Verifica se est· faltando uma saida */
        if (this.saida == null) {
        	throw new Exception("Est· faltando uma saida no mapa.");
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
     * Mostra as infos. do mapa do labirinto
     * @return String Infos.
     */
    public String toString() 
    {
        String mapa = "Mapa: "+this.file.getName()+" \nTotal de Linhas: " + this.altura + "\nTotal de colunas: " + this.largura + "\n \n";
        for (int i = 0; i < this.altura; i++) {
        	for (int j = 0; j < this.largura; j++) {
        		mapa += this.labirinto[i][j];
        	}
        	mapa += "\n";
        }
        mapa += "\nEntrada: (" +this.entrada.getX() + ","+this.entrada.getY()+")\nSaida: ("+this.saida.getX()+","+this.saida.getY()+")";
        return mapa;
    }
 
    /**
     * Verifica se o caractere È um item valido do mapa
     * @param char c
     * @return boolean Se o caractere for valido
     */
    private boolean isValid(char c) {
    	c = Character.toLowerCase(c);
    	return c == '#' || c == 'e' || c == 's' || c == ' ';
    }
    
    /**
     * Verifica se a Coordenada È uma parede
     * @param pos Coordenada
     * @return boolean Se for uma paredes
     */
    private boolean isWall(Coordenada pos) {
    	return pos.getY() == 0 || pos.getX() == 0 || pos.getX() == this.altura - 1 || pos.getY() == this.largura - 1;
    }

}