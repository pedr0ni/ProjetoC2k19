package app;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import app.core.Mapa;

public class App {

    public static void main(String[] args) throws Exception {

        System.out.println("Bem-vindo ao jogo de labirinto!"
        		+ "\nEscolha o arquivo txt na janela:");
        
        File toLoad = null;
        
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Seta o Look do Chooser para Windows
        
        JFileChooser pick = new JFileChooser();
        pick.setFileFilter(new FileNameExtensionFilter("Arquivos de Texto", "txt")); // Seta um filtro para apenas .txt
        
        int action = pick.showOpenDialog(null); // Abre o dialog de file chooser
        if (action == JFileChooser.APPROVE_OPTION)
        	toLoad = pick.getSelectedFile(); // Pega o arquivo selecionado pelo usuario
        
        Mapa mapa = new Mapa(toLoad); // Cria um novo Mapa

        try {	
            mapa.loadMapa(); // Carrega o mapa
            System.out.println(mapa);
        } catch (Exception e) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
        
    }

}