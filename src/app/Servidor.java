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

		Log log = new Log("log");
		try (ServerSocket ss = new ServerSocket(5000)) {
			Loja[] tempList = {
				new Loja("Magazine Luiza", 8001),
				new Loja("Leroy Merlin", 8002),
				new Loja("Itiban", 8003),
				new Loja("Cinemark", 8004),
				new Loja("Casas China", 8005)
			};
			List<Loja> listaLojas = Arrays.asList(tempList);
			
			while (true) {
				log.clear();
				System.out.println("Servidor aguardando um cliente ...");
				Socket conexaoCliente = ss.accept();
				System.out.println("Servidor: conexao feita");
				DataInputStream entrada = new DataInputStream(conexaoCliente.getInputStream());
				String string = entrada.readUTF();
				
				if(string.contains("૑")) {
					log.register("ADMIN: LOG REQUEST");
					DataOutputStream resposta = new DataOutputStream(conexaoCliente.getOutputStream());
					log.export();
					String aux = log.readLogFile();
					resposta.writeUTF(aux.substring(0, aux.length()));
				}else {
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
					log.register("|"+string+"|["+aux.replaceAll("\n", ", ").replaceAll("\t", "-")+"]");
					log.export();
					resposta.writeUTF(aux.substring(0, aux.length()));
				}
			}
//            ss.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

}
