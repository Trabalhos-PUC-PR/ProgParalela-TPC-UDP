package app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SearchEngine extends Thread {

	private Socket connection;
	private List<Produto> catalogo;
	private String loja;
	
	public SearchEngine(List<Produto> catalogo, Socket connection, String loja) {
		this.catalogo = catalogo;
		this.loja = "["+loja+"]";
		this.connection = connection;
	}

	public void run() {
		try {
			DataInputStream entrada = new DataInputStream(connection.getInputStream());
			DataOutputStream saida = new DataOutputStream(connection.getOutputStream());

			String subString = entrada.readUTF();
			
			List<Produto> productList = new ArrayList<>(catalogo);
			productList.removeIf(produto->(!produto.getTitulo().toLowerCase().contains(subString.toLowerCase())));
			StringBuilder sb = new StringBuilder();
			for(Produto p : productList) {
				sb.append(String.format("%-20s %s R$:%.2f\n", loja, p.getTitulo(), p.getPreco()));
			}
			saida.writeUTF(sb.toString());

			connection.close();
			System.out.println("SearchEngine: conexao encerrada");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
