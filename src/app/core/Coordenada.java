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

	/**
	 * Linha da matriz
	 * @param x 
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Coluna da matriz
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "(X: "+this.x + ", Y: " + this.y+")";
	}
	
	/**
	 * Instancia um novo tipo
	 * @param x int (Linha da matriz)
	 * @param y int (Coluna da matriz)
	 * @return Coordenada
	 */
	public static Coordenada valueOf(int x, int y) {
		return new Coordenada(x, y);
	}
	
}