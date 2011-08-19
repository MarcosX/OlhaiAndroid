package br.android.olhai;

import java.util.ArrayList;

/*
 * A classe CardapioDia armazena um ArrayList contendo os itens do cardapio de um dia
 * e outro ArrayList contendo a descrição do cardápio armazenada em strings
 */

public class CardapioDia {
	/*
	 * ArrayList de ItemCardapio, armazena os itens do cardápio do dia
	 */
	private ArrayList<ItemCardapio> cardapio;
	/*
	 * ArrayList de String contendo a descrição do cardápio do dia
	 */
	private ArrayList<String> dscCardapio;

	public CardapioDia() {
		cardapio = new ArrayList<ItemCardapio>();
		dscCardapio = new ArrayList<String>();
	}

	/*
	 * Insere um novo item no cardápio de um dia.
	 * 
	 * @param itemCardapio novo item a ser inserido no cardápio
	 */
	public void inserirItem(ItemCardapio itemCardapio) {
		// TODO Auto-generated method stub
		cardapio.add(new ItemCardapio(itemCardapio.getNome(), itemCardapio
				.getTipo()));
		dscCardapio.add(new String(itemCardapio.getNome() + "/"
				+ itemCardapio.getTipo()));
	}

	/*
	 * @return ArrayList de String contendo a descrição do cardápio
	 */
	public ArrayList<String> getDscCardapio() {
		return (ArrayList<String>) dscCardapio.clone();
	}
	
	/*
	 * Limpa o cardápio do dia. 
	 */
	public void limparCardapio() {
		cardapio.clear();
		dscCardapio.clear();
	}
}
