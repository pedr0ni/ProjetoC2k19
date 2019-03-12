package app.core;

/**
 * Classe Coordenada da matriz
 * @author Matheus Pedroni (18079020) - github.com/pedr0ni
 * @author Amanda
 * @since 2019
 */
public class Coordenada {
	
	/* Atributos da Coordenada */
	private int x,y;
	
	/**
	 * @param int x (Linha da matriz)
	 * @param int y (Coluna da matriz)
	 */
	public Coordenada(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return int x (Linha da matriz)
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return int y (Coluna da matriz)
	 */
	public int getY() {
		return y;
	}

}
