package br.android.olhai;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaCardapio extends ListView {

	ArrayAdapter<String> adapter;
	ArrayList<String> dscPratos;
	ArrayList<String> detalhesPratos;
	Context superContext;

	public ListaCardapio(Context context, AttributeSet attrs) {
		super(context, attrs);
		superContext = context;
		dscPratos = new ArrayList<String>();
		detalhesPratos = new ArrayList<String>();
	}

	public ListaCardapio(Context context) {
		super(context);
		superContext = context;
		dscPratos = new ArrayList<String>();
		detalhesPratos = new ArrayList<String>();
	}

	public void setInformaçõesPratos(ArrayList<String> dscPratos,
			ArrayList<String> detalhesPratos) {
		for (String string : detalhesPratos) {
			this.detalhesPratos.add(string);
		}
		for (String string : dscPratos) {
			this.dscPratos.add(string);
		}
		adapter = new ArrayAdapter<String>(superContext,
				android.R.layout.simple_list_item_1, dscPratos);
		setAdapter(adapter);
		setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						superContext);
				alert.setTitle(ListaCardapio.this.dscPratos.get(arg2));
				alert.setMessage(ListaCardapio.this.detalhesPratos.get(arg2));
				alert.setNeutralButton("Voltar", null);
				alert.show();
			}
		});
	}
}
