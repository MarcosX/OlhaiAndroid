package br.android.olhai;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	CardapioSemana cardapioDaSemana;
	ArrayList<String> dscCardapioDoDia;
	ArrayAdapter<String> adaptadorCardapio;
	String[] listaDeUniversidades = { "UECE", "---" };
	ArrayAdapter<String> adaptadorUniversidade;
	Spinner spinnerUniversidade;
	int diaDaSemana = 0;
	private ListView lista;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		cardapioDaSemana = new CardapioSemana();
		dscCardapioDoDia = new ArrayList<String>();

		adaptadorCardapio = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, dscCardapioDoDia);
		lista = ((ListView) findViewById(R.id.listCardapio));
		lista.setAdapter(adaptadorCardapio);

		lista.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> adapter, View view, int posicao,long id) {
					final ProgressDialog progress = ProgressDialog.show(MainActivity.this, "Aguarde...", "Pegando dados da web",true);
		    		
		    		final Toast aviso = Toast.makeText(MainActivity.this, "Dados Enviados com sucesso!!!", Toast.LENGTH_LONG);
		    			
		    		new Thread(new Runnable() {
						public void run() {
							String retorno = "";
							try{
								Thread.sleep(2000);
								Sincronizer s = new Sincronizer();
								retorno = s.getJSONFromAplication();
								
								Log.i("Retorno: ", retorno);
							}catch (ClientProtocolException e) {
								Log.e("Erro", "JSON", e);
							}catch (IOException e) {
								Log.e("Erro", "JSON", e);
							}catch (InterruptedException e) {
								Log.e("Erro", "JSON", e);
							}
							aviso.show();
							progress.dismiss();
						}
					}).start();
					
				}
	        	 
			});
		
		adaptadorUniversidade = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listaDeUniversidades);
		spinnerUniversidade = ((Spinner) findViewById(R.id.spinnerUniversidade));
		spinnerUniversidade.setAdapter(adaptadorUniversidade);

		carregarCardapio();
		exibirCardapio();

		((Button) findViewById(R.id.btnVerCardapio))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						carregarCardapio();
						exibirCardapio();
					}
				});

		((Button) findViewById(R.id.btnAmanha))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						alterarData(true);
						exibirCardapio();
					}
				});
		((Button) findViewById(R.id.btnOntem))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						alterarData(false);
						exibirCardapio();
					}
				});
	}

	private void alterarData(boolean isAmanha) {
		if (isAmanha == true) {
			if (diaDaSemana == 4)
				return;
			diaDaSemana++;
		} else {
			if (diaDaSemana == 0)
				return;
			diaDaSemana--;
		}
	}

	public void exibirCardapio() {
		dscCardapioDoDia.clear();
		for (int i = 0; i < cardapioDaSemana.getDscCardapio(diaDaSemana).size(); i++) {
			dscCardapioDoDia.add(cardapioDaSemana.getDscCardapio(diaDaSemana)
					.get(i));
		}
		adaptadorCardapio.notifyDataSetChanged();
		mostrarData();
	}

	public void mostrarData() {
		TextView dia = ((TextView) findViewById(R.id.textViewHoje));
		Button ontem = ((Button) findViewById(R.id.btnOntem));
		Button amanha = ((Button) findViewById(R.id.btnAmanha));
		switch (diaDaSemana) {
		case 0:
			dia.setText("Segunda-feira");
			ontem.setEnabled(false);
			ontem.setText("");
			amanha.setText("Terça");
			break;
		case 1:
			dia.setText("Terça-feira");
			ontem.setEnabled(true);
			ontem.setText("Segunda");
			amanha.setText("Quarta");
			break;
		case 2:
			dia.setText("Quarta-feira");
			ontem.setText("Terça");
			amanha.setText("Quinta");
			break;
		case 3:
			dia.setText("Quinta-feira");
			ontem.setText("Quarta");
			amanha.setEnabled(true);
			amanha.setText("Sexta");
			break;
		case 4:
			dia.setText("Sexta-feira");
			ontem.setText("Quinta");
			amanha.setEnabled(false);
			amanha.setText("");
			break;
		default:
			break;
		}
	}

	public void carregarCardapio() {
		try {
			if (spinnerUniversidade.getSelectedItemPosition() == 0) {
				cardapioDaSemana.AdicionarDeArquivo("UECE");
			} else {
				cardapioDaSemana.AdicionarDeArquivo(null);
			}
		} catch (ClientProtocolException e) {
			Log.e("Erro", "JSON", e);
		} catch (JSONException e) {
			Log.e("Erro", "JSON", e);
		} catch (IOException e) {
			Log.e("Erro", "JSON", e);
		} catch(Exception e){
			Log.e("Erro", "JSON", e);
		}
	}
}