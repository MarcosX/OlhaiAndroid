package br.android.olhai.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ArquivoUtil extends SQLiteOpenHelper{

	private static final String TABELA = "Universidade";
	private static final int VERSION = 1;
	private static final String[] COLS = {"id"};
	
	public ArquivoUtil(Context context) {
		super(context, TABELA, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABELA + 
		"( id INTEGER PRIMARY KEY);";
		db.execSQL(sql);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + ArquivoUtil.TABELA);
		this.onCreate(db);
	}
	
	public void inserir(Integer idUniversidade){
		ContentValues valores = new ContentValues();
		valores.put("id", idUniversidade);
		
		getWritableDatabase().insert(TABELA, null, valores);
	}
	
	public Integer carregarIdUniversidade(){
		Cursor c = getWritableDatabase().query(TABELA, COLS, null, null, null, null, null);
		Integer idUniversidade = -1;
		while(c.moveToNext()){
			idUniversidade = c.getInt(0);			
		}
		c.close();
		return idUniversidade;
	}
	
	public void alterar(Integer idUniversidade) {
		ContentValues values = new ContentValues();
		values.put("id", idUniversidade);
		
		getWritableDatabase().update(TABELA, values, null, null);
		
	}
	
}
