package br.android.olhai;

public class ItemCardapio {
	String nomePrato;
	String tipoPrato;

	public ItemCardapio(String nome, String tipo) {
		this.nomePrato = nome;
		this.tipoPrato = tipo;
	}

	public String getNome() {
		return nomePrato;
	}

	public String getTipo() {
		return tipoPrato;
	}
}
