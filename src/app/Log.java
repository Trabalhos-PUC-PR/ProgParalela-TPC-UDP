package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Log {

	private List<String> events;
	private String path;

	public Log(String path) {
		this.path = path;
		events = new ArrayList<String>();
	}

	public void register(String event) {
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = myDateObj.format(myFormatObj);
//	    System.out.println("Before formatting: " + myDateObj);
//	    System.out.println("After formatting: " + formattedDate);
		events.add(String.format("[%s] - %s", formattedDate, event));
	}

	public void clear() {
		events = new ArrayList<String>();
	}
	
	public String readLogFile() {
		File file = new File(path);
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			if(!file.exists())
				return "";
			StringBuilder sb = new StringBuilder();
			String aux = br.readLine();
			while(aux != null) {
				sb.append(aux + "\n");
				aux = br.readLine();
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public void export() {
		File file = new File(path);
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
			if(!file.exists())
				file.createNewFile();
			StringBuilder sb = new StringBuilder();
			for (String s : events) {
				sb.append(s + " ");
			}
			String aux = sb.toString();
			bw.append(aux + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String s : events) {
			sb.append(s+"\n");
		}
		String aux = sb.toString();
		return aux.substring(0, aux.length() - 1);
	}
	
}
