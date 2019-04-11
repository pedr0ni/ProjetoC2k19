package app.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import app.exceptions.MapaException;
import app.exceptions.PilhaException;
import app.utils.Pilha;

/**
 * Classe Mapa do Labirinto
 * Projeto Integrado C
 * @author Matheus Pedroni 18079020 (github.com/pedr0ni)
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
    private Player jogador;

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
     * @throws IOException (Caso o arquivo não seja encontrado)
     * @throws NumberFormatException (Caso a primeira linha não seja um número)
     * @throws MapaException (Caso ocorra algum problema no formato do mapa.)
     * @see https://docs.oracle.com/javase/7/docs/api/java/io/File.html
     */
    public void loadMapa() 
    throws IOException, NumberFormatException, MapaException
    {
    
    	/* Verifica se o arquivo é nulo */
    	if (this.file == null) {
    		throw new IOException("Arquivo inválido.");
    	}
    	
    	/* Verifica se o arquivo existe */
        if (!this.file.exists()) 
        {
            throw new IOException("O arquivo " + this.file.getName() + " não existe.");
        }
        
        /* Verifica se é um arquivo '.txt' */
        if (!this.file.getName().endsWith(".txt")) {
        	throw new IOException("O arquivo precisa ser do tipo .txt");
        }

        /* Cria o leitor de arquivos em buffer */
        BufferedReader reader = new BufferedReader(new FileReader(this.file));

        String line; // Cada linha lida do arquivo

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
                	reader.close();
                    throw new NumberFormatException("A primeira linha precisa ser o número da altura");
                }	
            }
            /* Linha 1: Apenas lê o tamanho da string para definir o limite do tamanho das colunas da matriz */
            if (index == 1) 
            {
                this.largura = line.length();

                /* Tendo definido os dois limites (altura e largurra [linhas e colunas])
                 * da matriz é possível reservar o espaço para receber os valores do mapa 
                 */
                this.labirinto = new char[this.altura][this.largura];
            }

            /* Recebe o valor do arquivo de texto e coloca dentro da matriz do mapa */
            if (index >= 1) 
            {

                /* Verifica se a linha está dentro do limite de colunas da matriz */
                if (line.length() != this.largura) 
                {
                	reader.close();
                    throw new MapaException("A largura é diferente de " + this.largura);
                }

                /* Para cada linha é preciso correr cada char da string e adicionar na matriz */
                for (int i = 0; i < line.length(); i++) 
                {
                	/* Instancia uma coordenada para a posição atual (indexLabirinto e i) */
                	Coordenada currentPos = Coordenada.valueOf(indexLabirinto, i);
                	char here = line.charAt(i); // Pega o char na posição
    
                	/* Verifica se o char é valido */
                	if (!isValid(here)) {
                		reader.close();
                		throw new MapaException("O char (" + here +") não é valido.", currentPos);
                	}
                	
                	/* Verifica se a parede � valida */
                	if (isWall(currentPos) && here == ' ') {
                		reader.close();
                		throw new MapaException("A parede não pode estar aberta (Conter espaços em branco).", currentPos);
                	}
                	
                	/* Procura por uma entrada (char == 'E') */
                	if (Character.toLowerCase(here) == 'e' && isWall(currentPos)) {
                		if (this.entrada != null)
                		{
                			reader.close();
                			throw new MapaException("O mapa possui mais de uma entrada.");	
                		}
                		this.entrada = currentPos;
                	} else if (Character.toLowerCase(here) == 'e' && !isWall(currentPos)) {
                		reader.close();
                		/* Se a entrada não for em uma parede lança uma exception */
                		throw new MapaException("Entrada localizada fora de uma parede.", currentPos);
                	}
                	
                	/* Procura por uma saida (char == 'S') */
                	if (Character.toLowerCase(here) == 's' && isWall(currentPos)) {
                		if (this.saida != null) {
                			reader.close();
                			throw new MapaException("O mapa possui mais de uma saida.");
                		}
                		this.saida = currentPos;
                	} else if (Character.toLowerCase(here) == 's' && !isWall(currentPos)) {
                		reader.close();
                		/* Caso a saida não for em uma parede lança uma exception */
                		throw new MapaException("Saida localizada fora de uma parede.", currentPos);
                	}
                    this.labirinto[currentPos.getX()][currentPos.getY()] = here; // Salva o char na mem�ria
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
        	reader.close();
            throw new MapaException("O número de linhas é diferente de " + this.altura);
        }
        
        /* Verifica se está faltando uma entrada */
        if (this.entrada == null) {
        	reader.close();
        	throw new MapaException("Está faltando uma entrada no mapa.");
        }
        
        /* Verifica se está faltando uma saida */
        if (this.saida == null) {
        	reader.close();
        	throw new MapaException("Está faltando uma saida no mapa.");
        }

        reader.close(); // Fecha o leitor do arquivo
    }
    
    /**
     * Setter
     * @param jogador
     */
    public void setPlayer(Player jogador) {
    	jogador.setMapa(this);
    	jogador.setPosicao(getEntrada());
    	this.jogador = jogador;
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
     * p@return int Largura
     */
    public int getLargura() {
    	return this.largura;
    }
    
    /**
     * Getter
     * @return
     */
    public Coordenada getEntrada() {
    	return this.entrada;
    }
    
    /**
     * Getter
     * @return
     */
    public Coordenada getSaida() {
    	return this.saida;
    }
    
    /**
     * Getter
     * @return
     */
    public Player getPlayer() {
    	return this.jogador;
    }

    /**
     * Mostra as infos. do mapa do labirinto
     * @return String Infos.
     */
    @Override
    public String toString() 
    {
        String mapa = "Mapa: "+this.file.getName()+" \nTotal de Linhas: " + this.altura + "\nTotal de colunas: " + this.largura + "\n \n";
        for (int i = 0; i < this.altura; i++) {
        	for (int j = 0; j < this.largura; j++) {
        		mapa += this.labirinto[i][j];
        	}
        	mapa += "\n";
        }
        mapa += "\nEntrada: "+ this.entrada + "\nSaida: " + this.saida;
        return mapa;
    }
    
    /**
     * 
     * @param stream
     */
    public void printMapa(PrintStream stream) {
    	String mapa = "";
    	for (int i = 0; i < this.altura; i++) {
        	for (int j = 0; j < this.largura; j++) {
        		Coordenada current = Coordenada.valueOf(i, j);
        		if (getPlayer().getPosicao().equals(current)) 
        			mapa += "*";
        		else
        			mapa += this.labirinto[i][j];
        	}
        	mapa += "\n";
        }
    	stream.println(mapa);
    }
    
    public String printMapa() {
    	String mapa = "";
    	for (int i = 0; i < this.altura; i++) {
        	for (int j = 0; j < this.largura; j++) {
        		Coordenada current = Coordenada.valueOf(i, j);
        		if (getPlayer().getPosicao().equals(current)) 
        			mapa += "*";
        		else
        			mapa += this.labirinto[i][j];
        	}
        	mapa += "\n";
        }
    	return mapa;
    }
 
    /**
     * Verifica se o caractere � um item valido do mapa
     * @param char c
     * @return boolean Se o caractere for valido
     */
    private boolean isValid(char c) {
    	c = Character.toLowerCase(c);
    	return c == '#' || c == 'e' || c == 's' || c == ' ';
    }
    
    /**
     * Verifica se a Coordenada � uma parede
     * @param pos Coordenada
     * @return boolean Se for uma paredes
     */
    private boolean isWall(Coordenada pos) {
    	return pos.getY() == 0 || pos.getX() == 0 || pos.getX() == this.altura - 1 || pos.getY() == this.largura - 1;
    }

    /**
     * Getter
     * @return char[][] Estrutura
     */
    public char[][] getStructure() {
    	return this.labirinto;
    }
    
    /**
     * Verifica e retorna os adjacentes (espaços em branco)
     * @param pos
     * @return
     * @throws PilhaException
     */
    public Pilha<Coordenada> getAdjacentes(Coordenada pos) throws PilhaException {
    	Pilha<Coordenada> res = new Pilha<Coordenada>();
   	 	
    	try {
    		
    		Coordenada cima = Coordenada.valueOf(pos.getX() - 1, pos.getY());
        	if (checkAdjacente(this.labirinto[cima.getX()][cima.getY()])) // Verifica cima
        		res.empilhar(cima);
        	
        	Coordenada baixo = Coordenada.valueOf(pos.getX() + 1, pos.getY());
        	if (checkAdjacente(this.labirinto[baixo.getX()][baixo.getY()])) // Verifica embaixo
        		res.empilhar(baixo);
        	
        	Coordenada frente = Coordenada.valueOf(pos.getX(), pos.getY() + 1);
        	if (checkAdjacente(this.labirinto[frente.getX()][frente.getY()])) // Verifica frente
        		res.empilhar(frente);
    		
    		Coordenada tras = Coordenada.valueOf(pos.getX(), pos.getY() - 1);
        	if (checkAdjacente(this.labirinto[tras.getX()][tras.getY()]))
        		res.empilhar(tras);
        	
    	} catch (ArrayIndexOutOfBoundsException e) {
    		// N acontece nada (Esta exception é lancada quando pega um caractere fora do mapa (posicao inicial)
    	}
    	
    	return res;
    }
    
    /**
     * 
     * @param filename
     * @throws IOException 
     * @throws PilhaException 
     */
    public void dumpToFile(String filename) throws IOException, PilhaException {
    	File f = new File(this.file.getParent() + "/" + filename);
    	if (f.exists()) {
    		f.delete();
    	}
    	f.createNewFile();
    	BufferedWriter writer = new BufferedWriter(new FileWriter(f));
    	String res = "";
    	for (int i = 0; i < this.altura; i++) {
    		for (int j = 0; j < this.largura; j++) {
    			Coordenada current = Coordenada.valueOf(i, j);
    			if (getPlayer().getCaminhoRaw().contains(current) && this.labirinto[i][j] != 'S') {
    				res += "*";
    			} else {
    				res += this.labirinto[i][j];
    			}
    		}
    		res += "\n";
    	}
    	writer.write(res);
    	writer.close();
    }
    
    /**
     * 
     * @param c
     * @return
     */
    private boolean checkAdjacente(char c) {
    	return c == ' ' || c == 'S';
    }

}