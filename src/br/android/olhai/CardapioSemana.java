package br.android.olhai;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class CardapioSemana {

	private static final int DIAS_DA_SEMANA = 5;
	public ArrayList<CardapioDia> cardapio;

	public CardapioSemana() {
		cardapio = new ArrayList<CardapioDia>();
		for (int i = 0; i < DIAS_DA_SEMANA; i++) {
			cardapio.add(new CardapioDia());
		}
	}

	public void carregarDeArquivo() throws ClientProtocolException,
			JSONException, IOException {
		limparCardapio();
		Sincronizer sincronizer = new Sincronizer();
		JSONObject jsonObject = (JSONObject) new JSONTokener(
				sincronizer.getJSONFromAplication()).nextValue();
		String[] diasDaSemana = { "segunda", "terca", "quarta", "quinta",
				"sexta" };
		for (int j = 0; j < 5; j++) {
			JSONObject jsonCardapioDoDia = jsonObject
					.getJSONObject(diasDaSemana[j]);
			cardapio.get(j).setData(
					diasDaSemana[j] + "-feira "
							+ jsonCardapioDoDia.getString("data"));
			JSONArray jsonArray = jsonCardapioDoDia.getJSONArray("itens");
			for (int i = 0; i < jsonArray.length(); i++) {
				cardapio.get(j).inserirItem(
						new ItemCardapio(jsonArray.getString(i), ""));
			}
		}
	}

	private void limparCardapio() {
		for (CardapioDia cardapioTmp : cardapio) {
			cardapioTmp.limparCardapio();
		}
	}

	public ArrayList<String> getDscNomePrato(int diaDaSemana) {
		return (ArrayList<String>) cardapio.get(diaDaSemana).getDscNomePrato();
	}

	public ArrayList<String> getDetalhesPrato(int diaDaSemana) {
		return (ArrayList<String>) cardapio.get(diaDaSemana).getDetalhesPrato();
	}

	public String getDataFormatada(int diaDaSemana) {
		return cardapio.get(diaDaSemana).getDataFormatada();
	}

}