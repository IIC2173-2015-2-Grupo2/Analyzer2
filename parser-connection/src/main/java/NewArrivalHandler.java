import java.awt.List;
import java.util.LinkedList;
import java.util.Queue;

interface NewArrivalListener{
	void newArrival(String s);
}

public class NewArrivalHandler {
	private static NewArrivalListener newArrivalListener;
	public static void createListener(NewArrivalListener listener){
		newArrivalListener = listener;
	}
	protected void newArrival(String s){
		newArrivalListener.newArrival(s);	
	}
}