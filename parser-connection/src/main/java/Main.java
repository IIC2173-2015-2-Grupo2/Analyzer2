import static spark.Spark.*;

public class Main {

	public static void main(String[] args) {
		post("/analyzer", (req, res) -> "Hello World");
	}

}
