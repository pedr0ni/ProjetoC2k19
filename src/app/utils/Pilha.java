package app.utils;

import app.exceptions.PilhaException;

public class Pilha<E> {
	
	private class Node {
		
		private E item;
		private Node next;
		
		public Node(E item, Node next) {
			this.item = item;
			this.next = next;
		}
		
		public E getItem() {
			return this.item;
		}
		
		public Node getProximo() {
			return this.next;
		}
		
	}

	/* Atributos da pilha */
	private Node primeiro;
	private int tamanho = 0;
	
	/**
	 * Verifica se a pilha est� vazia
	 * @return boolean Se a pilha estiver vazia
	 */
	public boolean isEmpty() {
		return this.primeiro == null;
	}
	
	/**
	 * Empilha um elemento
	 * @param valor
	 * @throws PilhaException Se a pilha estiver cheia
	 */
	public void empilhar(E valor) throws PilhaException {
		this.primeiro = new Node(valor, this.primeiro);
		this.tamanho++;
	}
	
	/**
	 * Desempilha um elemento
	 * @return E elemento desempilhado
	 * @throws PilhaException Se a pilha estiver vazia
	 */
 	public E desempilhar() throws PilhaException {
 		if (isEmpty())
 			throw new PilhaException("A pilha esta vazia");
 		E ret = this.primeiro.getItem();
 		this.primeiro = this.primeiro.getProximo();
 		this.tamanho--;
 		return ret;
 	}
 	
 	/**
 	 * Pega o valor no topo da pilha
 	 * @return E Valor no topo
 	 * @throws PilhaException Se a pilha estiver vazia
 	 */
 	public E valorTopo() throws PilhaException {
 		if (isEmpty())
 			throw new PilhaException("Nenhum valor no topo. A pilha esta vazia");
 		return this.primeiro.getItem();
 	}
 	
 	public boolean contains(E valor) throws PilhaException {
 		Pilha<E> aux = new Pilha<E>();
 		boolean res = false;
 		while (!isEmpty()) {
 			E check = desempilhar();
 			aux.empilhar(check);
 			
 			if (check.equals(valor)) {
 				res = true;
 				break;
 			}
 		}
 		while (!aux.isEmpty()) {
 			empilhar(aux.desempilhar());
 		}
 		return res;
 	}
 	
 	@Override
 	public String toString() {
 		String res = "Pilha (" + this.tamanho + ") --> ";
 		
 		return res;
 	}
	
}
