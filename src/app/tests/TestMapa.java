package app.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import app.core.Mapa;

public class TestMapa {
	
	private Mapa mapa = new Mapa("mapa.txt");
	
	/**
	 * Teste para passar um mapa que n�o existe
	 * @throws Exception
	 */
	@Test(expected = IOException.class)
	public void arquivoInvalido() throws Exception {
		Mapa m = new Mapa("MapaErrado.txt");
		m.loadMapa();
	}
	
	@Test
	public void testMapa() {
		try {
			mapa.loadMapa();
			assertEquals("A altura est� incorreta", 3, mapa.getAltura());
			assertEquals("A largura est� incorreta", 5, mapa.getLargura());
		} catch (Exception e) {
			fail("Este � um mapa v�lido mas causou uma exeption: " + e.getMessage());
		}
	}

}