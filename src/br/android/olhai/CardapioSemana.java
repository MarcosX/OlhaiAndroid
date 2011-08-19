package br.android.olhai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

public class CardapioSemana {

	private static final int DIAS_DA_SEMANA = 5;
	private ArrayList<CardapioDia> cardapio = null;;
	private Sincronizer sicronizer = new Sincronizer();
	public CardapioSemana cardapioSemana = null;

	public CardapioSemana(ArrayList<CardapioDia> cardapio2) {
		cardapio2 = new ArrayList<CardapioDia>();
		for (int i = 0; i < DIAS_DA_SEMANA; i++) {
			Log.i("NULL", cardapio2.toString());
			//cardapio.add(new CardapioDia());
		}
	}
	
	public CardapioSemana(){
		super();
	}

	/*
	 * @param nomeDoArquivo nome do arquivo com os dados
	 */
	public void AdicionarDeArquivo(String nomeDoArquivo) throws ClientProtocolException, JSONException, IOException {
		// TODO Implementar método de parse do arquivo
		cardapio = new ArrayList<CardapioDia>();
		cardapioSemana = new CardapioSemana(cardapio);
		
		limparCardapio();
		if (nomeDoArquivo == null)
			return;
		if (nomeDoArquivo == "UECE") {
			Sincronizer sincronizer = new Sincronizer();
			JSONObject jsonObject = (JSONObject) new JSONTokener(sincronizer.getJSONFromAplication()).nextValue();
//			String query = jsonObject.getString("query");
//			JSONArray locations = jsonObject.getJSONArray("locations");
			
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

	@SuppressWarnings("unchecked")
	public ArrayList<String> getDscCardapio(int diaDaSemana) {
		return (ArrayList<String>) cardapio.get(diaDaSemana).getDscCardapio().clone();
	}

}