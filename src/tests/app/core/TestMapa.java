package tests.app.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;
import app.core.Mapa;
import app.exceptions.MapaException;

public class TestMapa {
	
	/**
	 * Teste para passar um mapa que não existe
	 * @throws Exception
	 */
	@Test(expected = IOException.class)
	public void testArquivoInvalido() throws Exception {
		Mapa m = new Mapa(new File("NaoExiste.txt"));
		try {
			m.loadMapa();
		} catch (Exception e) {
			System.out.println("[TEST] [ OK! ] Mapa com arquvio inexistente : " + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Teste para um mapa com File == null
	 * @throws Exception
	 */
	@Test(expected = IOException.class)
	public void testFileNull() throws Exception {
		Mapa m = new Mapa(null);
		try {
			m.loadMapa();
		} catch (Exception e) {
			System.out.println("[TEST] [ OK! ] Mapa com file == null : " + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Teste para um mapa com buracos na parede
	 * @throws Exception
	 */
	@Test(expected = MapaException.class)
	public void testSemParede() throws Exception {
		Mapa m = getMapa("semparede");
		try {
			m.loadMapa();
		} catch (Exception e) {
			System.out.println("[TEST] [ OK! ] Mapa sem parede : " + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Teste para um mapa que está carregando largura ou altura incorreta(s)
	 * Este teste também verifica se ocorreu alguma exception em um mapa correto
	 */
	@Test
	public void testLoadMapa() {
		Mapa mapa = getMapa("mapa");
		try {
			mapa.loadMapa();
			assertEquals("A altura está incorreta", 3, mapa.getAltura());
			assertEquals("A largura está incorreta", 9, mapa.getLargura());
		} catch (Exception e) {
			fail("Este é um mapa válido mas causou uma exeption: " + e.getMessage());
		}
	}
	
	/**
	 * Pega um arquivo de mapa generico para testes
	 * @param fileName String com o nome do arquivo sem '.txt'
	 * @return {@link Mapa} Mapa Generico
	 */
	private Mapa getMapa(String fileName) {
		try {
			return new Mapa(new File(TestMapa.class.getResource(fileName + ".txt").toURI()));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

}
