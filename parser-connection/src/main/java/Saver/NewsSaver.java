package Saver;

import javax.xml.crypto.Data;

/**
 * Clase encargada de guardar la información en la base de datos
 * @author estebandib
 *
 */
public class NewsSaver {
	String title, tags, header, date, url, imageUrl;
	final String Port, Host, Password, User;
	public NewsSaver(){
		Port = DatabaseManager.getPort();
		Host = DatabaseManager.getHost();
		Password = DatabaseManager.getPassword();
		User = DatabaseManager.getUser();
	}
	/**
	 * Encargado de guardar la distinta información en la base de datos
	 * @param s Arreglo de información con estructura [título, fecha, bajada, url, tags]
	 */
	public void saveInDataBase(String[] s){
		title = s[0];
		date = s[1];
		header = s[2];
		url = s[3];
		tags = s[4];
		//TODO: establecer conexión con la base de datos y enviar
	}
}
