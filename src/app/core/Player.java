package app.core;

import java.io.PrintStream;
import java.util.Timer;
import java.util.TimerTask;

import app.core.enums.PlayerMode;
import app.exceptions.PilhaException;
import app.utils.Pilha;

/**
 * Classe Player
 * Contem os m�todos para a��es do player que vai fazer o labirinto
 * @author Matheus Pedroni (18079020) - github.com/pedr0ni
 * @author Amanda
 * @since 2019
 */
public class Player {
	
	/* Atributos de Player */
	private Coordenada posicao;
	private Mapa mapaAtual;
	private PlayerMode mode;
	private String name;
	
	/* Caminhos */
	private Pilha<Coordenada> caminho;
	private Pilha<Pilha<Coordenada>> possibilidades;
	
	/**
	 * Coordenada de entrada do mapa
	 * @param @Coordenada entrada
	 */
	public Player(String nome) {
		this.name = nome;
		this.mode = PlayerMode.PROGRESSIVE;
	}
	
	/**
	 * Setter
	 * @param mode {@link PlayerMode}
	 */
	public void setMode(PlayerMode mode) {
		this.mode = mode;
	}
	
	/**
	 * Getters
	 * @return {@link PlayerMode} Current Mode
	 */
	public PlayerMode getMode() {
		return this.mode;
	}

	/**
	 * Getter
	 * @return {@link Coordenada} Posição atual
	 */
	public Coordenada getPosicao() {
		return posicao;
	}

	/**
	 * Setter
	 * @param posicao {@link Coordenada}
	 */
	public void setPosicao(Coordenada posicao) {
		this.posicao = posicao;
	}

	/**
	 * @return @Mapa mapaAtual
	 */
	public Mapa getMapa() {
		return mapaAtual;
	}

	/**
	 * @param @Mapa mapaAtual
	 */
	public void setMapa(Mapa mapaAtual) {
		this.caminho = new Pilha<Coordenada>(mapaAtual.getAltura() * mapaAtual.getLargura());
		this.possibilidades = new Pilha<Pilha<Coordenada>>(mapaAtual.getAltura() * mapaAtual.getLargura());
		this.mapaAtual = mapaAtual;
	}
	
	/**
	 * Incrementa 1 na coluna da matriz (Y da coordenada)
	 */
	public void moveFoward() {
		this.posicao.setY(this.posicao.getY() + 1);
	}
	
	/**
	 * Decremetna 1 na coluna da mateiz (Y da coordenada)
	 */
	public void moveBack() {
		this.posicao.setX(this.posicao.getY() - 1);
	}
	
	/**
	 * Incrementa 1 na linha da matriz (X da coordenada)
	 */
	public void moveUp() {
		this.posicao.setX(this.posicao.getX() + 1);
	}
	
	/**
	 * Decrementa 1 na linha da matriz (X da coordenada)
	 */
	public void moveDown() {
		this.posicao.setX(this.posicao.getX() - 1);
	}
	
	public void startMoving(PrintStream stream) throws InterruptedException, PilhaException {
		stream.println("Start moving...");
		while (!isSaida()) {
			
			switch (this.mode) {
			
				case PROGRESSIVE:
					Pilha<Coordenada> adj = getMapa().getAdjacentes(this.posicao);
					Coordenada proxima = adj.desempilhar();
					setPosicao(proxima);
					
					this.caminho.empilhar(proxima);
					this.possibilidades.empilhar(adj);
				
					break;
				case REGRESSIVE:
					break;
			}
			getMapa().printMapa(stream);
			Thread.sleep(1000L); // Aguarda 1 segundo
		}
		getMapa().printMapa(stream);
		stream.println("\n" + // Msg de fim :D
				"       , , , , , ,\n" + 
				"       |_|_|_|_|_|\n" + 
				"      |~=,=,=,=,=~|         Passou!\n" + 
				"      |~~~~~~~~~~~|\n" + 
				"    |~=,=,=,=,=,=,=~|\n" + 
				"    |~~~~~~~~~~~~~~~|\n" + 
				"  |~=,=,=,=,=,=,=,=,=~|\n" + 
				"  |~~~~~~~~~~~~~~~~~~~|\n" + 
				"(^^^^^^^^^^^^^^^^^^^^^^^)\n" + 
				" `'-------------------'`");
	}
	
	public boolean isSaida() {
		return this.mapaAtual.getStructure()[this.posicao.getX()][this.posicao.getY()] == 'S';
	}
	
	@Override
	public String toString() {
		return "Player " + this.name + " at" + this.posicao;
	}

}