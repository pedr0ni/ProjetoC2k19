package app.utils;

import app.exceptions.PilhaException;

public class Pilha<E> {

	/* Atributos da pilha */
	private E[] vetor;
	private int tamanho;
	private int topo;
	
	public Pilha(int tamanho) {
		this.tamanho = tamanho;	
		this.vetor = (E[]) new Object[tamanho]; // Aloca espa�o para o vetor da pilha
		this.topo = -1;
	}
	
	/**
	 * Verifica se a pilha est� vazia
	 * @return boolean Se a pilha estiver vazia
	 */
	public boolean isEmpty() {
		return this.topo == -1;
	}
	
	/**
	 * Verifica se a pilha est� cheia
	 * @return boolean Se a pilha estiver cheia
	 */
	public boolean isFull() {
		return this.topo == this.tamanho - 1;
	}
	
	/**
	 * Empilha um elemento
	 * @param valor
	 * @throws PilhaException Se a pilha estiver cheia
	 */
	public void empilhar(E valor) throws PilhaException {
		if (isFull())
			throw new PilhaException();
		this.vetor[++this.topo] = valor;
	}
	
	/**
	 * Desempilha um elemento
	 * @return E elemento desempilhado
	 * @throws PilhaException Se a pilha estiver vazia
	 */
 	public E desempilhar() throws PilhaException {
 		if (isEmpty())
 			throw new PilhaException();
 		return this.vetor[this.topo--];
 	}
 	
 	/**
 	 * Pega o valor no topo da pilha
 	 * @return E Valor no topo
 	 * @throws PilhaException Se a pilha estiver vazia
 	 */
 	public E valorTopo() throws PilhaException {
 		if (isEmpty())
 			throw new PilhaException();
 		return this.vetor[this.topo];
 	}
 	
 	/**
 	 * Pega o total de elementos empilhados
 	 * @return int Total de elementos
 	 */
 	public int totalElementos() {
 		return this.topo + 1;
 	}
 	
 	@Override
 	public String toString() {
 		String res = "Pilha (" + totalElementos() + ") --> ";
 		for (int i = 0; i < totalElementos(); i++) {
 			res += this.vetor[i] + ", ";
 		}
 		return res;
 	}
	
}