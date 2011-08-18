package br.android.olhai;

import java.util.ArrayList;

public class CardapioSemana {

	private ArrayList<CardapioDia> cardapio = new ArrayList<CardapioDia>();
	
	public CardapioSemana() {
		for (int i = 0; i < 5; i++) {
			cardapio.add(new CardapioDia());
		}
	}

	/*
	 * @param nomeDoArquivo nome do arquivo com os dados
	 */
	public void AdicionarDeArquivo(String nomeDoArquivo) {
		// TODO Implementar método de parse do arquivo
		limparCardapio();
		if (nomeDoArquivo == null)
			return;
		if (nomeDoArquivo == "UECE") {
			cardapio.get(0).inserirItem(new ItemCardapio("Arroz", "Guarnição"));
			cardapio.get(0)
					.inserirItem(new ItemCardapio("Feijão", "Guarnição"));
			cardapio.get(0).inserirItem(
					new ItemCardapio("Bife Bovino", "Carne"));
			cardapio.get(0).inserirItem(
					new ItemCardapio("Cenoura, Batata, Alface", "Salada"));
			cardapio.get(0).inserirItem(
					new ItemCardapio("Laranja", "Sobremesa"));
			cardapio.get(1).inserirItem(new ItemCardapio("Arroz", "Guarnição"));
			cardapio.get(1)
					.inserirItem(new ItemCardapio("Feijão", "Guarnição"));
			cardapio.get(1).inserirItem(new ItemCardapio("Lasanha", "Massa"));
			cardapio.get(1).inserirItem(
					new ItemCardapio("Gelatina", "Sobremesa"));
			cardapio.get(2).inserirItem(new ItemCardapio("Baião", "Guarnição"));
			cardapio.get(2).inserirItem(
					new ItemCardapio("Macarrão", "Guarnição"));
			cardapio.get(2).inserirItem(
					new ItemCardapio("Peixe a Delicia", "Peixe"));
			cardapio.get(2)
					.inserirItem(new ItemCardapio("Banana", "Sobremesa"));
			cardapio.get(3).inserirItem(new ItemCardapio("Baião", "Guarnição"));
			cardapio.get(3).inserirItem(
					new ItemCardapio("Macarrão", "Guarnição"));
			cardapio.get(3).inserirItem(new ItemCardapio("Paçoca", "Carne"));
			cardapio.get(3).inserirItem(
					new ItemCardapio("Cenoura, Batata, Alface", "Salada"));
			cardapio.get(3).inserirItem(
					new ItemCardapio("Laranja", "Sobremesa"));
			cardapio.get(4).inserirItem(new ItemCardapio("Arroz", "Guarnição"));
			cardapio.get(4)
					.inserirItem(new ItemCardapio("Feijão", "Guarnição"));
			cardapio.get(4).inserirItem(
					new ItemCardapio("Linguiça", "Guarnição"));
			cardapio.get(4).inserirItem(
					new ItemCardapio("Cenoura, Batata, Alface", "Salada"));
		}
	}

	private void limparCardapio() {
		for (CardapioDia cardapioTmp : cardapio) {
			cardapioTmp.limparCardapio();
		}
	}

	public ArrayList<String> getDscCardapio(int diaDaSemana) {
		return (ArrayList<String>) cardapio.get(diaDaSemana).getDscCardapio().clone();
	}

}