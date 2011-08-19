package br.android.olhai;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {
	/*
	 * Cardapios da semana
	 */
	CardapioSemana cardapioDaSemana;
	/*
	 * Descrição do carápio de um dia que será exibido na ListView
	 */
	ArrayList<String> dscCardapioDoDia;
	/*
	 * Adaptador para a ListView que exibe o cardápio do dia
	 */
	ArrayAdapter<String> adaptadorCardapio;
	/*
	 * Lista de Universidades possíveis
	 */
	String[] listaDeUniversidades = { "UECE", "---" };
	/*
	 * Adaptador para o Spinner de seleção de Universidade
	 */
	ArrayAdapter<String> adaptadorUniversidade;
	/*
	 * Dia da semana considerado para buscar o cardápio
	 */
	int diaDaSemana = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		cardapioDaSemana = new CardapioSemana();
		dscCardapioDoDia = new ArrayList<String>();
		setAdapters();
		carregarCardapio();
		exibirCardapio();
		onClickBtnVerCardapio();
		onClickBtnAmanha();
		onClickBtnOntem();
	}

	/*
	 * Cria os adapters e atribui para a ListView do cardápio e o Spinner de
	 * escolha da Universidade
	 */
	private void setAdapters() {
		adaptadorCardapio = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, dscCardapioDoDia);
		((ListView) findViewById(R.id.listCardapio))
				.setAdapter(adaptadorCardapio);

		adaptadorUniversidade = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listaDeUniversidades);
		((Spinner) findViewById(R.id.spinnerUniversidade))
				.setAdapter(adaptadorUniversidade);
	}

	/*
	 * Atribui o método de chamada quando o botão Ontem for clicado
	 */
	private void onClickBtnOntem() {
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

	/*
	 * Atribui o método de chamada quando o botão Amanhã for clicado
	 */
	private void onClickBtnAmanha() {
		((Button) findViewById(R.id.btnAmanha))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						alterarData(true);
						exibirCardapio();
					}
				});
	}

	/*
	 * Atribui o método de chamada quando o botão Ver Cardápio for clicado
	 */
	private void onClickBtnVerCardapio() {
		((Button) findViewById(R.id.btnVerCardapio))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						carregarCardapio();
						exibirCardapio();
					}
				});
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
		dscCardapioDoDia.clear();
		for (int i = 0; i < cardapioDaSemana.getDscCardapio(diaDaSemana).size(); i++) {
			dscCardapioDoDia.add(cardapioDaSemana.getDscCardapio(diaDaSemana)
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
	 * spinner
	 */
	private void carregarCardapio() {
		if (((Spinner) findViewById(R.id.spinnerUniversidade))
				.getSelectedItemPosition() == 0) {
			cardapioDaSemana.carregarDeArquivo("UECE");
		} else {
			cardapioDaSemana.carregarDeArquivo(null);
		}
	}
}