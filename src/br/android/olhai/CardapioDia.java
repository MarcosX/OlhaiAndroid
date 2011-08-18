package br.android.olhai;

import java.util.ArrayList;

public class CardapioDia {
	private ArrayList<ItemCardapio> cardapio;
	private ArrayList<String> dscCardapio;

	public CardapioDia() {
		cardapio = new ArrayList<ItemCardapio>();
		dscCardapio = new ArrayList<String>();
	}

	public void inserirItem(ItemCardapio itemCardapio) {
		// TODO Auto-generated method stub
		cardapio.add(new ItemCardapio(itemCardapio.getNome(), itemCardapio
				.getTipo()));
		dscCardapio.add(new String(itemCardapio.getNome() + "/"
				+ itemCardapio.getTipo()));
	}

	public ArrayList<String> getDscCardapio() {
		return (ArrayList<String>) dscCardapio.clone();
	}

	public void limparCardapio() {
		cardapio.clear();
		dscCardapio.clear();
	}
}
