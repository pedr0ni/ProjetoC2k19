package tests.app.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.io.IOException;
import org.junit.Test;
import app.core.Mapa;

public class TestMapa {
	
	private Mapa mapa = new Mapa("mapa.txt");
	
	/**
	 * Teste para passar um mapa que não existe
	 * @throws Exception
	 */
	@Test(expected = IOException.class)
	public void testArquivoInvalido() throws Exception {
		Mapa m = new Mapa("MapaErrado.txt");
		m.loadMapa();
	}
	
	
	/**
	 * Teste para um mapa que está carregando largura ou altura incorreta(s)
	 * Este teste também verifica se ocorreu alguma exception em um mapa correto
	 */
	@Test
	public void testLoadMapa() {
		try {
			mapa.loadMapa();
			assertEquals("A altura está incorreta", 3, mapa.getAltura());
			assertEquals("A largura está incorreta", 5, mapa.getLargura());
		} catch (Exception e) {
			fail("Este é um mapa válido mas causou uma exeption: " + e.getMessage());
		}
	}

}
