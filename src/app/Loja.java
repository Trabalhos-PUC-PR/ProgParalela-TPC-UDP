package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Loja extends Thread {

	private String nome;
	private List<Produto> catalogo;
	private int port;
	
	
	public Loja(String nome, int port) {
		this.nome = nome;
		this.port = port;
		this.catalogo = Produto.generateRandomCatalogue();
		start();
	}

	public String getNome() {
		return nome;
	}

	public int getPort() {
		return port;
	}
	
	@Override
	public void run() {
		try (ServerSocket server = new ServerSocket(port)) {
			System.out.printf("Loja %s incializada na porta %d\n", nome, port);
			while(true) {
				Socket conexao = server.accept();
				System.out.println("Requesição anonima recebida at "+nome);
				SearchEngine procura = new SearchEngine(catalogo, conexao, this.nome);
				procura.start();
				try {
					procura.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				conexao.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
