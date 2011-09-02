package br.android.olhai;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MainActivity extends Activity implements OnTouchListener {

	private static final int NENHUMA_UNIVERSIDADE_SELECIONADA = 0;
	private static final int ESPACO_DE_ARRASTE_X = 30;
	private static final int ESPACO_DE_ARRASTE_Y = 50;

	private SharedPreferences preferences;
	private CardapioSemana cardapioDaSemana;

	private int diaDaSemana = 0;
	private int universidadeSelecionada;

	private float downEventX, downEventY;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setOnTouchListeners();
		cardapioDaSemana = new CardapioSemana();

		// Get the xml/preferences.xml preferences
		this.preferences = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
	}

	@Override
	protected void onResume() {
		super.onResume();
		universidadeSelecionada = getIdUniversidadeSelecionada();
		if (universidadeSelecionada == NENHUMA_UNIVERSIDADE_SELECIONADA) {
			executandoPelaPrimeiraVez();
		} else {
			exibirCardapioEDatas();
		}
	}

	private void exibirCardapioEDatas() {
		requistarCardapio();
		setAdapters();
		mostrarData();
	}

	private void setOnTouchListeners() {
		findViewById(R.id.layoutGlobal).setOnTouchListener(
				(OnTouchListener) this);
		findViewById(R.id.cardapioSegunda).setOnTouchListener(
				(OnTouchListener) this);
		findViewById(R.id.cardapioTerca).setOnTouchListener(
				(OnTouchListener) this);
		findViewById(R.id.cardapioQuarta).setOnTouchListener(
				(OnTouchListener) this);
		findViewById(R.id.cardapioQuinta).setOnTouchListener(
				(OnTouchListener) this);
		findViewById(R.id.cardapioSexta).setOnTouchListener(
				(OnTouchListener) this);
	}

	/**
	 * Método responsável por exibir a primeira configuração do usuário onde ele
	 * irá escolher qual universidade ele deseja seguir;
	 * 
	 * @author thiagodnf
	 */
	private void executandoPelaPrimeiraVez() {
		final String items[] = getResources().getStringArray(
				R.array.universidadesParticipantes_descricao);

		AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
		ab.setTitle("Universidade");

		/*
		 * Callback executado SOMENTE quando o usuário clica em algum elemento
		 * da lista
		 */
		ab.setSingleChoiceItems(items, 0,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int itemSelecionado) {
						MainActivity.this.universidadeSelecionada = itemSelecionado + 1;
					}
				});

		ab.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				/* Salva a escolha nas preferências */
				SharedPreferences.Editor editor = MainActivity.this.preferences
						.edit();
				String idUniversidade = String
						.valueOf(MainActivity.this.universidadeSelecionada);
				editor.putString("universidadesParticipantesListPreference",
						idUniversidade);
				editor.commit();

				exibirCardapioEDatas();
			}
		});

		ab.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				MainActivity.this.finish();
			}
		});

		/* Quando o usuário clica no botão voltar */
		ab.setOnCancelListener(new DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				MainActivity.this.finish();
			}
		});

		ab.show();
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_principal, menu);
		return true;
	};

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuConfiguracoes:

			Intent settingsActivity = new Intent(getBaseContext(),
					Preferences.class);

			startActivity(settingsActivity);
			break;
		case R.id.menuSobre:
			Dialog dialog = new Dialog(MainActivity.this);

			dialog.setContentView(R.layout.menu_sobre);
			dialog.setTitle("Sobre");
			dialog.setCancelable(true);

			dialog.show();
			break;
		default:
			Log.e("Erro", "Nenhuma opção selecionada no Menu Principal");
			break;
		}
		return true;
	};

	/**
	 * Verifica se o usuário deixou a sincronização automática
	 * 
	 * @author thiagodnf
	 * @return Verdadeiro caso o checkbox esteja ativa. Falso, otherwise
	 */
	@SuppressWarnings("unused")
	private boolean isCardapioAutomatico() {
		if (this.preferences != null) {
			return this.preferences.getBoolean("cardapioAutomaticoCheckBox",
					true);
		}

		return false;
	}

	/**
	 * Retorna o id da Universidade Selecionada
	 * 
	 * @return Retorna 0 Caso não tenha sido selecionado, e retorna o Id caso
	 *         tenha sido selecionado
	 * @author thiagodnf *
	 */
	private int getIdUniversidadeSelecionada() {
		if (this.preferences != null) {
			String idUniversidade = this.preferences.getString(
					"universidadesParticipantesListPreference", "0");
			return Integer.parseInt(idUniversidade);
		}
		return 0;
	}

	private void setAdapters() {
		((ListaCardapio) findViewById(R.id.cardapioSegunda))
				.setInformacoesPratos(cardapioDaSemana.getDscNomePrato(0),
						cardapioDaSemana.getDetalhesPrato(0));
		((ListaCardapio) findViewById(R.id.cardapioTerca))
				.setInformacoesPratos(cardapioDaSemana.getDscNomePrato(1),
						cardapioDaSemana.getDetalhesPrato(1));
		((ListaCardapio) findViewById(R.id.cardapioQuarta))
				.setInformacoesPratos(cardapioDaSemana.getDscNomePrato(2),
						cardapioDaSemana.getDetalhesPrato(2));
		((ListaCardapio) findViewById(R.id.cardapioQuinta))
				.setInformacoesPratos(cardapioDaSemana.getDscNomePrato(3),
						cardapioDaSemana.getDetalhesPrato(3));
		((ListaCardapio) findViewById(R.id.cardapioSexta))
				.setInformacoesPratos(cardapioDaSemana.getDscNomePrato(4),
						cardapioDaSemana.getDetalhesPrato(4));
	}

	private void mostrarData() {
		((TextView) findViewById(R.id.textViewHoje)).setText(cardapioDaSemana
				.getDataFormatada(diaDaSemana));
	}

	private void requistarCardapio() {
		try {
			cardapioDaSemana.carregarDeArquivo();
		} catch (ClientProtocolException e) {
			Log.e("JSON", "ClientProtocol: " + e.getMessage());
		} catch (JSONException e) {
			Log.e("JSON", "JSONException: " + e.getMessage());
		} catch (IOException e) {
			AlertDialog.Builder d = new AlertDialog.Builder(this);
			d.setMessage("Verifique a conexão com a Internet.");
			d.show();
			Log.e("JSON", "IOException: " + e.getMessage());
		}
	}

	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downEventX = event.getX();
			downEventY = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			float upEventY = event.getY();
			if (Math.abs(downEventY - upEventY) > ESPACO_DE_ARRASTE_Y) {
				return false;
			}
			float upEventX = event.getX();
			if (downEventX - ESPACO_DE_ARRASTE_X > upEventX) {
				if (diaDaSemana < 4) {
					flipDireitaParaEsquerda();
					diaDaSemana++;
				}
			} else if (downEventX + ESPACO_DE_ARRASTE_X < upEventX) {
				if (diaDaSemana > 0) {
					flipEsquerdaParaDireita();
					diaDaSemana--;
				}
			} else {
				return false;
			}
			mostrarData();
			return true;

		default:
			break;
		}
		return false;
	}

	private void flipEsquerdaParaDireita() {
		ViewFlipper cardapioViewFlipper = (ViewFlipper) findViewById(R.id.viewFlipperCardapio);
		cardapioViewFlipper.setInAnimation(ViewFlipAnimation.getInstance()
				.getInFromLeft());
		cardapioViewFlipper.setOutAnimation(ViewFlipAnimation.getInstance()
				.getOutToRight());
		cardapioViewFlipper.showPrevious();
	}

	private void flipDireitaParaEsquerda() {
		ViewFlipper cardapioViewFlipper = (ViewFlipper) findViewById(R.id.viewFlipperCardapio);
		cardapioViewFlipper.setInAnimation(ViewFlipAnimation.getInstance()
				.getInFromRight());
		cardapioViewFlipper.setOutAnimation(ViewFlipAnimation.getInstance()
				.getOutToLeft());
		cardapioViewFlipper.showNext();

	}
}
