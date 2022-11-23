package app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Produto implements Serializable{

	private static final long serialVersionUID = 1L;
	private String titulo;
	private double preco;
	private String origem;
	
	public Produto(String titulo, double preco) {
		this.titulo = titulo;
		this.preco = preco;
	}
	
	private static String getRandomProdName() {
		String[] names = {
				"Geladeira",
				"Fogão",
				"Guarda-roupa"
		};
		Random random = new Random();
		return names[random.nextInt(names.length)];
	}
	
	public static Produto generateRandomProduct() {
		Random random = new Random();
		double randomPrice = random.nextDouble(10000);
		Produto aux = new Produto(getRandomProdName(), randomPrice);
		return aux;
	}
	
	public static List<Produto> generateRandomCatalogue(){
		Random random = new Random();
		int catalogueSize = random.nextInt(15);
		List<Produto> aux = new ArrayList<>();
		while(aux.size() < catalogueSize) {
			aux.add(generateRandomProduct());
		}
		return aux;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

}
