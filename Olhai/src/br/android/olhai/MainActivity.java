package br.android.olhai;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	/*
	 * Cardapios da semana
	 */
	CardapioSemana cardapioDaSemana;
	/*
	 * Descrição do carápio de um dia que será exibido na ListView
	 */
	ArrayList<String> dscPratoDoDia;
	/*
	 * Descrição do carápio de um dia que será exibido na ListView
	 */
	ArrayList<String> detalhesPrato;
	/*
	 * Adaptador para a ListView que exibe o cardápio do dia
	 */

	ArrayAdapter<String> adaptadorCardapio;
	/*
	 * Dia da semana considerado para buscar o cardápio
	 */
	int diaDaSemana = 0;
	/*
	 * Universidade selecionada na configuração
	 */
	int universidadeSelecionada = 0;
	private static final int UECE = 1;
	private static final int NENHUMA_UNIVERSIDADE = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		dscPratoDoDia = new ArrayList<String>();
		detalhesPrato = new ArrayList<String>();
		cardapioDaSemana = new CardapioSemana();
		setAdapters();
		setOnClick();
		carregarCardapio();
	}

	/*
	 * Override para adicionar o menu de configurações
	 * 
	 * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu,
	 * android.view.View, android.view.ContextMenu.ContextMenuInfo)
	 */
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	};

	/*
	 * Override para adicionar os eventos de seleção de universidade Atribui a
	 * universidade seleciona e recarrega o cardápio apenas se a universidade
	 * selecionada mudar
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		int escolha = universidadeSelecionada;
		switch (item.getItemId()) {
		case R.id.opcaoUece:
			universidadeSelecionada = UECE;
			((TextView) findViewById(R.id.textViewUniversidadeEstatico))
					.setText("Visualizando cardápio de UECE");
			break;
		case R.id.opcaoNenhuma:
			universidadeSelecionada = NENHUMA_UNIVERSIDADE;
			((TextView) findViewById(R.id.textViewUniversidadeEstatico))
					.setText("Selecione a Universidade no menu Configurações");
			break;
		case R.id.menuConfiguracoes:
			return true;
		default:
			break;
		}
		if (universidadeSelecionada == escolha) {
			return true;
		}
		carregarCardapio();
		return true;
	};

	/*
	 * Atribui os metodos de chamada aos botões
	 */
	private void setOnClick() {
		((Button) findViewById(R.id.btnOntem))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						alterarData(false);
						exibirCardapio();
					}
				});
		((Button) findViewById(R.id.btnAmanha))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						alterarData(true);
						exibirCardapio();
					}
				});
		((ListView) findViewById(R.id.listCardapio))
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						AlertDialog.Builder alert = new AlertDialog.Builder(
								MainActivity.this);
						alert.setTitle(dscPratoDoDia.get(arg2));
						alert.setMessage(detalhesPrato.get(arg2));
						alert.setNeutralButton("Voltar", null);
						alert.show();
					}
				});
	}

	/*
	 * Cria os adapters e atribui para a ListView do cardápio e o Spinner de
	 * escolha da Universidade
	 */
	private void setAdapters() {
		adaptadorCardapio = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, dscPratoDoDia);
		((ListView) findViewById(R.id.listCardapio))
				.setAdapter(adaptadorCardapio);
	}

	/*
	 * Altera o dia da semana que está sendo visualizado
	 * 
	 * @param isAmanha boolean que indica se o dia deve ser incrementado, caso o
	 * botão clicado seja amanhã, ou decrementado, caso o botão clicado seja
	 * ontem
	 */
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

	/*
	 * altera a descrição do cardápio para o dia da semana de acordo com a
	 * variável diaDaSemana e notifica o adaptador da ListView que o conjunto de
	 * dados mudou
	 */
	private void exibirCardapio() {
		dscPratoDoDia.clear();
		detalhesPrato.clear();
		for (int i = 0; i < cardapioDaSemana.getDscNomePrato(diaDaSemana)
				.size(); i++) {
			dscPratoDoDia.add(cardapioDaSemana.getDscNomePrato(diaDaSemana)
					.get(i));
			detalhesPrato.add(cardapioDaSemana.getDetalhesPrato(diaDaSemana)
					.get(i));
		}
		adaptadorCardapio.notifyDataSetChanged();
		mostrarData();
	}

	/*
	 * Altera o dia na interface de acordo com a variável diaDaSemana. Modifica
	 * o texto do topo da aplicação e os botões de ontem e amanhã
	 */
	private void mostrarData() {
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

	/*
	 * Carrega o cardapio da semana de acordo com a universidade selecionada no
	 * menu de configurações
	 */
	private void carregarCardapio() {
		try {
			switch (universidadeSelecionada) {
			case UECE:
				cardapioDaSemana.carregarDeArquivo("UECE");
				break;
			case NENHUMA_UNIVERSIDADE:
				cardapioDaSemana.carregarDeArquivo(null);
				break;
			default:
				break;
			}
		} catch (ClientProtocolException e) {
			Log.e("Erro", "JSON", e);
		} catch (JSONException e) {
			Log.e("Erro", "JSON", e);
		} catch (IOException e) {
			Log.e("Erro", "JSON", e);
		} catch (Exception e) {
			Log.e("Erro", "JSON", e);
		}
		exibirCardapio();
	}
}
