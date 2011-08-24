package br.android.olhai;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/*
 * A classe CardapioSemana agrega cinco cardapios, um para cada dia da semana. As informações
 * dos cardápios de cada dia são obtidas através de um arquivo.
 */
public class CardapioSemana {

	private static final int DIAS_DA_SEMANA = 5;
	/*
	 * Conjunto de cardapios de um dia
	 */
	public ArrayList<CardapioDia> cardapio;

	/*
	 * Lê as informações do cardápio de um arquivo e insere na lista de
	 * cardápios
	 * 
	 * @param nomeDoArquivo nome do arquivo com os dados
	 */
	public void carregarDeArquivo(String nomeDoArquivo)
			throws ClientProtocolException, JSONException, IOException {
		cardapio = new ArrayList<CardapioDia>();
		for (int i = 0; i < DIAS_DA_SEMANA; i++) {
			cardapio.add(new CardapioDia());
		}
		limparCardapio();
		if (nomeDoArquivo == null)
			return;
		if (nomeDoArquivo == "UECE") {

			Sincronizer sincronizer = new Sincronizer();
			JSONObject jsonObject = (JSONObject) new JSONTokener(
					sincronizer.getJSONFromAplication()).nextValue();
			String[] diasDaSemana = { "segunda", "terca", "quarta", "quinta",
					"sexta" };
			for (int j = 0; j < 5; j++) {
				JSONObject jsonCardapioDoDia = jsonObject
						.getJSONObject(diasDaSemana[j]);
				JSONArray jsonArray = jsonCardapioDoDia.getJSONArray("itens");
				for (int i = 0; i < jsonArray.length(); i++) {
					cardapio.get(j).inserirItem(
							new ItemCardapio(jsonArray.getString(i), ""));
				}
			}
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
	@SuppressWarnings("unchecked")
	public ArrayList<String> getDscNomePrato(int diaDaSemana) {
		return (ArrayList<String>) cardapio.get(diaDaSemana).getDscNomePrato()
				.clone();
	}

	/*
	 * @return descrição do cardápio de um dia
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> getDetalhesPrato(int diaDaSemana) {
		return (ArrayList<String>) cardapio.get(diaDaSemana).getDetalhesPrato()
				.clone();
	}

}