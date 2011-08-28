package br.android.olhai;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;


import br.android.olhai.util.ArquivoUtil;

public class MainActivity extends Activity implements OnTouchListener {
	private SharedPreferences preferences;
	
	CardapioSemana cardapioDaSemana;
	ArrayList<String> dscPratoDoDia;
	ArrayList<String> detalhesPrato;
	ArrayAdapter<String> adaptadorCardapio;
	int diaDaSemana = 0;
	int universidadeSelecionada = 1;
	private float downEventX;
	private static final int UECE = 1;
	private static final int NENHUMA_UNIVERSIDADE = 0;
	private ArquivoUtil arquivoUtil;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		findViewById(R.id.layoutGlobal).setOnTouchListener(
				(OnTouchListener) this);

		// Get the xml/preferences.xml preferences
		this.preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		
		
		int idUniversidadeSelecionada = getIdUniversidadeSelecionada();
		
		arquivoUtil = new ArquivoUtil(this);

		cardapioDaSemana = new CardapioSemana();
		dscPratoDoDia = new ArrayList<String>();
		detalhesPrato = new ArrayList<String>();
		setAdapters();
		setOnClick();
		
		/*if (arquivoUtil.carregarIdUniversidade() != -1) {
			carregarCardapio();
		} else {
			Toast.makeText(this, "ESCOLHER UNIVERSIDADE", Toast.LENGTH_SHORT)
					.show();
		}
		*/
		
		if (idUniversidadeSelecionada == 0) {			
			executandoPelaPrimeiraVez();
		}else{
			/*Carrega as informações salvas as preferencias e carrega o cardápio*/
		}
		
	}
	
	/**
	 * Método responsável por exibir a primeira configuração do usuário onde
	 * ele irá escolher qual universidade ele deseja seguir;
	 * @author thiagodnf
	 */
	private void executandoPelaPrimeiraVez()
	{
		final String items[] =  getResources().getStringArray(R.array.universidadesParticipantes_descricao);
		
		AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
		ab.setTitle("Universidade");
		
		/*Callback executado SOMENTE quando o usuário clica em algum elemento da lista*/
		ab.setSingleChoiceItems(items, 0,new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int itemSelecionado) {				
				MainActivity.this.universidadeSelecionada = itemSelecionado+1;				
			}
		});		
		
		ab.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				/*Salva a escolha nas preferências*/
				SharedPreferences.Editor editor = MainActivity.this.preferences.edit();
				String idUniversidade = String.valueOf(MainActivity.this.universidadeSelecionada);
				editor.putString("universidadesParticipantesListPreference", idUniversidade);
				
				editor.commit();			
			}
		});
		
		ab.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {				
				MainActivity.this.finish();
			}
		});	
		
		/*Quando o usuário clica no botão voltar*/
		ab.setOnCancelListener(new DialogInterface.OnCancelListener(){
			public void onCancel (DialogInterface dialog){
				MainActivity.this.finish();
			}
		});
				
		ab.show();
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
		inflater.inflate(R.menu.menu_principal, menu);
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
		//int escolha = universidadeSelecionada;
		switch (item.getItemId()) {
			/*case R.id.opcaoUece:
				universidadeSelecionada = UECE;
				((TextView) findViewById(R.id.textViewUniversidadeEstatico))
						.setText("Visualizando cardápio de UECE");
				break;
			case R.id.opcaoNenhuma:
				universidadeSelecionada = NENHUMA_UNIVERSIDADE;
				((TextView) findViewById(R.id.textViewUniversidadeEstatico))
						.setText("Selecione a Universidade no menu Configurações");
				break;
			*/
			case R.id.menuConfiguracoes:
				
				Intent settingsActivity = 
					 new Intent(getBaseContext(),br.android.olhai.Preferences.class);
				 
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
		//if (universidadeSelecionada == escolha) {
		//	return true;
		//}
		//carregarCardapio();
		return true;
	};
	
	
	/**
	 * Verifica se o usuário deixou a sincronização automática
	 * @author thiagodnf	 
	 * @return Verdadeiro caso o checkbox esteja ativa. Falso, otherwise  
	 */
	private boolean isCardapioAutomatico()
	{		
		if(this.preferences != null)
		{
			return this.preferences.getBoolean("cardapioAutomaticoCheckBox", true);
		}
		
		return false;
 	}
	
	/** Retorna o id da Universidade Selecionada 
	 * @return Retorna 0 Caso não tenha sido selecionado, e retorna o Id caso tenha sido selecionado
	 * @author thiagodnf	 * 
	 */
	private int getIdUniversidadeSelecionada()
	{
		if(this.preferences != null)
		{
			String idUniversidade = this.preferences.getString("universidadesParticipantesListPreference", "0"); 
			return Integer.parseInt(idUniversidade);
		}
		return 0;
	}

	/*
	 * Atribui os metodos de chamada aos botões
	 */
	private void setOnClick() {
		((Button) findViewById(R.id.btnOntem))
				.setOnClickListener(new OnClickListener() {

					
					public void onClick(View v) {
						alterarData(false);
						exibirCardapio();
					}
				});
		((Button) findViewById(R.id.btnAmanha))
				.setOnClickListener(new OnClickListener() {

					
					public void onClick(View v) {
						alterarData(true);
						exibirCardapio();
					}
				});
		((ListView) findViewById(R.id.listCardapio))
				.setOnItemClickListener(new OnItemClickListener() {

					
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
		final ProgressDialog progress = ProgressDialog.show(this, "Aguarde...",
				"Carregando Cardapio..", true);

		final Toast aviso = Toast.makeText(this,
				"Cardapio carregado com sucesso!!!", Toast.LENGTH_LONG);

		try {
			switch (universidadeSelecionada) {
			case UECE:
				new Thread(new Runnable() {
					public void run() {
						try {
							cardapioDaSemana.carregarDeArquivo("UECE");
						} catch (ClientProtocolException e) {
							Log.e("Erro", "JSON", e);
						} catch (JSONException e) {
							Log.e("Erro", "JSON", e);
						} catch (IOException e) {
							Log.e("Erro", "JSON", e);
						} catch (Exception e) {
							Log.e("Erro", "JSON", e);
						}
						aviso.show();
						progress.dismiss();
					}
				}).start();				
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
		aviso.show();
		progress.dismiss();

	}

	
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downEventX = event.getX();
			break;
		case MotionEvent.ACTION_UP:
			float upEventX = event.getX();
			if (downEventX > upEventX) {
				if (diaDaSemana < 4)
					diaDaSemana++;
			} else if (downEventX < upEventX) {
				if (diaDaSemana > 0)
					diaDaSemana--;
			}
			exibirCardapio();
			break;

		default:
			break;
		}
		return true;
	}
}
