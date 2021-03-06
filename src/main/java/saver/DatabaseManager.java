package saver;

/**
 * Contiene la información de login y conexión con la base de datos.
 * @author estebandib
 */
public class DatabaseManager {
	private static String Port, Host, Password, User;

	public static void createDatabaseManager(){
		Port = System.getenv("NEO4J_PORT");
		Host = System.getenv("NEO4J_HOST");
		Password = System.getenv("NEO4J_PASS");
		User = System.getenv("NEO4J_USER");
	}

	public static String getPort() {
		return Port;
	}

	public static String getHost() {
		return Host;
	}

	public static String getPassword() {
		return Password;
	}

	public static String getUser() {
		return User;
	}
}
