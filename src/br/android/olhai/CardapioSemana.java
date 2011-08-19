package br.android.olhai;

import java.util.ArrayList;

/*
 * A classe CardapioSemana agrega cinco cardapios, um para cada dia da semana. As informações
 * dos cardápios de cada dia são obtidas através de um arquivo.
 */

public class CardapioSemana {

	/*
	 * Conjunto de cardapios de um dia
	 */
	private ArrayList<CardapioDia> cardapio = new ArrayList<CardapioDia>();

	/*
	 * Cria 5 cardapios de um dia
	 */
	public CardapioSemana() {
		for (int i = 0; i < 5; i++) {
			cardapio.add(new CardapioDia());
		}
	}

	/*
	 * Lê as informações do cardápio de um arquivo e insere na lista de
	 * cardápios
	 * 
	 * @param nomeDoArquivo nome do arquivo com os dados
	 */
	public void carregarDeArquivo(String nomeDoArquivo) {
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

	/*
	 * Limpa o conteúdo dos cinco cardápios
	 */
	
	private void limparCardapio() {
		for (CardapioDia cardapioTmp : cardapio) {
			cardapioTmp.limparCardapio();
		}
	}

	
	/*
	 * @return descrição do cardápio de um dia
	 */
	public ArrayList<String> getDscCardapio(int diaDaSemana) {
		return (ArrayList<String>) cardapio.get(diaDaSemana).getDscCardapio()
				.clone();
	}

}