package br.android.olhai;

public class ItemCardapio {
	String nomePrato, tipoPrato;

	/*
	 * @param nome nome do prato
	 * 
	 * @param tipo tipo do prato
	 */

	public ItemCardapio(String nome, String tipo) {
		this.nomePrato = nome;
		this.tipoPrato = tipo;
	}

	/*
	 * @return nome do prato
	 */
	public String getNome() {
		return nomePrato;
	}

	/*
	 * @return tipo do prato
	 */
	public String getTipo() {
		return tipoPrato;
	}
}
