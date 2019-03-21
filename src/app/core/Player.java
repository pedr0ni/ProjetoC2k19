package app.core;

import java.io.PrintStream;

import app.core.enums.PlayerMode;
import app.exceptions.PilhaException;
import app.utils.Pilha;

/**
 * Classe Player
 * Contem os metodos para ações do player que vai fazer o labirinto
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
	
	private Coordenada checkCima() {
		return Coordenada.valueOf(this.posicao.getX() - 1, this.posicao.getY());
	}
	/**
	 * 
	 * @param stream
	 * @throws InterruptedException
	 * @throws PilhaException
	 */
	public void startMoving(PrintStream stream) throws InterruptedException, PilhaException {
		stream.println("Start moving...");
		while (!isSaida()) {
			getMapa().printMapa(stream);
			switch (this.mode) {
			
				case PROGRESSIVE:
					Pilha<Coordenada> adj = getMapa().getAdjacentes(this.posicao);
					Coordenada proxima = null;
					
					try {
						System.out.println("Player: " + this);
						System.out.println("POSSIVEIS: " + adj);
						proxima = adj.desempilhar();
						while (isCaminho(proxima) && !adj.isEmpty()) {
							proxima = adj.desempilhar();
						}
						setPosicao(proxima);
						
						this.caminho.empilhar(proxima);
						this.possibilidades.empilhar(adj);
						
					} catch (PilhaException e) {
						setMode(PlayerMode.REGRESSIVE);
						System.out.println("Modo regressivo!");
					}
					break;
				case REGRESSIVE:
					Coordenada mover = this.caminho.desempilhar();
					Pilha<Coordenada> adjt = this.possibilidades.desempilhar();
					System.out.println(adjt);
					if (adjt.isEmpty()) {
						setPosicao(mover); // Se não tiver possibilidades volta no caminho
					} else {
						Coordenada aprox = adjt.desempilhar();
						System.out.println("Indo para " + aprox);
						setPosicao(aprox);
						setMode(PlayerMode.PROGRESSIVE);
						System.out.println("Modo progressivo!");
					}
					break;
			}
			Thread.sleep(1000L); // Aguarda 1 segundo
		}
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
	
	/**
	 * Verifica se o player ja passou pela {@link Coordenada}
	 * @param pos {@link Coordenada}
	 * @return boolean
	 * @throws PilhaException
	 */
	public boolean isCaminho(Coordenada pos) throws PilhaException {
		Pilha<Coordenada> aux = new Pilha<Coordenada>(this.caminho.getTamanho());
		boolean res = false;
		while (!this.caminho.isEmpty()) {
			Coordenada check = this.caminho.desempilhar();
			if (check.equals(pos)) {
				res = true;
				break;
			}
			aux.empilhar(check);
		}
		// Restaura a pilha
		while (!aux.isEmpty()) {
			this.caminho.empilhar(aux.desempilhar());
		}
		return res;
	}
	
	public boolean isSaida() {
		return this.mapaAtual.getStructure()[this.posicao.getX()][this.posicao.getY()] == 'S';
	}
	
	@Override
	public String toString() {
		return "Player " + this.name + " at" + this.posicao;
	}

}