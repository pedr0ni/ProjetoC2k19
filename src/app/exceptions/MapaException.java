package app.exceptions;

import app.core.Coordenada;

@SuppressWarnings("serial")
public class MapaException extends Exception {
	
	private final Coordenada pos;
	
	public MapaException(String message, Coordenada pos) {
		super(message);
		this.pos = pos;
	}
	
	public MapaException(String message) {
		super(message);
		this.pos = null;
	}

	public Coordenada getPos() {
		return this.pos;
	}
	
	@Override
	public String getMessage() {
		return (this.pos != null) ? super.getMessage() + "\nna coordenada " + this.pos : super.getMessage();
	}
	
}
