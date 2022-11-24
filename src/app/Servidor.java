package app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Servidor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try (ServerSocket ss = new ServerSocket(5000)) {
			Loja[] tempList = {
				new Loja("Magazine Luiza", 8001),
				new Loja("Leroy Merlin", 8002),
				new Loja("Itiban", 8003),
				new Loja("Cinemark", 8004),
				new Loja("Casas China", 8005)
			};
			List<Loja> listaLojas = Arrays.asList(tempList);
			
			//TODO gerar uma lista de lojas (threads), cada instancia de loja ja abre um ServerSocket
			// conectar nesses sockets e fazer a mesma busca por palavras, retornar tudo aqui (no server), e juntar em uma res só
			
			while (true) {
				System.out.println("Servidor aguardando um cliente ...");
				Socket conexaoCliente = ss.accept();
				System.out.println("Servidor: conexao feita");
				DataInputStream entrada = new DataInputStream(conexaoCliente.getInputStream());
				String string = entrada.readUTF();
				
				List<Socket> storeConnections = new ArrayList<>();
				for(Loja loja : listaLojas) {
					int port = loja.getPort();
					Socket aux = new Socket("localhost", port);
					DataOutputStream requisicao = new DataOutputStream(aux.getOutputStream());
					
					requisicao.writeUTF(string);
					storeConnections.add(aux);
				}
				
				StringBuilder sb = new StringBuilder();
				for(Socket storeConnection : storeConnections) {
					DataInputStream response = new DataInputStream(storeConnection.getInputStream());
					sb.append(response.readUTF());
				}
				DataOutputStream resposta = new DataOutputStream(conexaoCliente.getOutputStream());
				String aux = sb.toString();
				resposta.writeUTF(aux.substring(0, aux.length()));
				
			}
//            ss.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

}
