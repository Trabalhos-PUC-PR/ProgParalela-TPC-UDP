package app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {

			// saida nesse caso quer dizer a informação que sai do cliente
			System.out.print("Cliente: digite o produto procurado: ");
			Scanner sc = new Scanner(System.in);
			String produto = sc.nextLine();
			sc.close();

			Socket conexao = new Socket("localhost", 5000);
			DataOutputStream requisicao = new DataOutputStream(conexao.getOutputStream());
			System.out.println("Cliente: conexao feita");
			requisicao.writeUTF(produto);

			// entrada quer dizer a resposta da requisição
			DataInputStream entrada = new DataInputStream(conexao.getInputStream());
			System.out.println(entrada.readUTF());

			conexao.close(); // encerra a conexao com o servidor
			System.out.println("Cliente: conexao encerrada");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
