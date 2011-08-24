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
	private ArrayList<String> dscNomePrato;
	/*
	 * ArrayList de String contendo a descrição do cardápio do dia
	 */
	private ArrayList<String> dscTipoPrato;

	public CardapioDia() {
		cardapio = new ArrayList<ItemCardapio>();
		dscNomePrato = new ArrayList<String>();
		dscTipoPrato = new ArrayList<String>();
	}

	/*
	 * Insere um novo item no cardápio de um dia.
	 * 
	 * @param itemCardapio novo item a ser inserido no cardápio
	 */
	public void inserirItem(ItemCardapio itemCardapio) {
		cardapio.add(new ItemCardapio(itemCardapio.getNome(), itemCardapio
				.getTipo()));
		dscNomePrato.add(new String(itemCardapio.getNome()));
		dscTipoPrato.add(new String(itemCardapio.getTipo()));
	}

	/*
	 * @return ArrayList de String contendo a descrição dos pratos do cardápio
	 */
	public ArrayList<String> getDscNomePrato() {
		return dscNomePrato;
	}

	/*
	 * @return ArrayList de String contendo a descrição dos tipo dos pratos do
	 * cardápio
	 */
	public ArrayList<String> getDetalhesPrato() {
		return dscTipoPrato;
	}

	/*
	 * Limpa o cardápio do dia.
	 */
	public void limparCardapio() {
		cardapio.clear();
		dscNomePrato.clear();
		dscTipoPrato.clear();
	}
}
