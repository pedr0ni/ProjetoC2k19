package tests.app.core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import app.exceptions.PilhaException;
import app.utils.Pilha;

public class TestPilha {

	private Pilha<Integer> minhaPilha;
	
	@Before
	public void resetPilha() {
		minhaPilha = new Pilha<Integer>();
	}
	
	@Test(expected = PilhaException.class)
	public void testCheia() throws PilhaException {
		for (int i = 0; i < 6; i++) {
			minhaPilha.empilhar(i);
		}
	}
	
	@Test(expected = PilhaException.class)
	public void testVazia() throws PilhaException {
		minhaPilha.desempilhar();
	}
	
	@Test
	public void testSize() throws PilhaException {
		for (int i = 0; i < 3; i++) {
			minhaPilha.empilhar(i);
		}
		minhaPilha.desempilhar();

		assertEquals("Contagem de elementos erradas", 2, 2);
	}
	
	@Test
	public void testTopo() throws PilhaException {
		minhaPilha.empilhar(55);
		assertEquals("Elemento incorreto", 55, (int) minhaPilha.desempilhar());
	}

}
