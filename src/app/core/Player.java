package app.core;

/**
 * Classe Player
 * Contem os métodos para ações do player que vai fazer o labirinto
 * @author Matheus Pedroni (18079020) - github.com/pedr0ni
 * @author Amanda
 * @since 2019
 */
public class Player {
	
	/* Atributos de Player */
	private Coordenada posicao;
	private Mapa mapaAtual;
	
	/**
	 * Coordenada de entrada do mapa
	 * @param @Coordenada entrada
	 */
	public Player(Coordenada entrada) {
		this.posicao = entrada;
	}

	/**
	 * 
	 * @return @Coordenada posicao
	 */
	public Coordenada getPosicao() {
		return posicao;
	}

	/**
	 * @param @Coordenada posicao
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
		this.mapaAtual = mapaAtual;
	}
	
	/**
	 * Incrementa 1 na coluna da matriz (Y da coordenada)
	 */
	public void moveFoward() {
		this.posicao.setX(this.posicao.getY() + 1);
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

}
