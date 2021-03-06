package br.android.olhai;

import java.util.ArrayList;

public class CardapioDia {
	private ArrayList<ItemCardapio> cardapio;
	private ArrayList<String> dscNomePrato;
	private ArrayList<String> dscTipoPrato;
	private String data;

	public CardapioDia() {
		cardapio = new ArrayList<ItemCardapio>();
		dscNomePrato = new ArrayList<String>();
		dscTipoPrato = new ArrayList<String>();
		data = new String();
	}

	public void inserirItem(ItemCardapio itemCardapio) {
		cardapio.add(new ItemCardapio(itemCardapio.getNome(), itemCardapio
				.getTipo()));
		dscNomePrato.add(new String(itemCardapio.getNome()));
		dscTipoPrato.add(new String(itemCardapio.getTipo()));
	}

	public ArrayList<String> getDscNomePrato() {
		return dscNomePrato;
	}

	public ArrayList<String> getDetalhesPrato() {
		return dscTipoPrato;
	}

	public void limparCardapio() {
		cardapio.clear();
		dscNomePrato.clear();
		dscTipoPrato.clear();
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDataFormatada() {
		return data;
	}
}
