package app.utils;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import app.core.Mapa;

public class UIBuilder {
	
	private JFrame frame;
	private JTextArea area;
	private Mapa mapa;
	
	public UIBuilder(Mapa m) {
		this.frame = new JFrame("Labirinto");
		this.frame.setSize(1280, 720);
		this.frame.show();
		
		this.area = new JTextArea();
		this.frame.add(area);
		this.mapa = m;
	}
	
	public void update() {
		this.area.setText(this.mapa.printMapa());
	}

}
