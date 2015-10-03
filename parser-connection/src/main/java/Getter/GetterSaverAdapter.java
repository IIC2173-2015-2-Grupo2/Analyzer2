package Getter;

import Saver.NewsSaver;

public class GetterSaverAdapter extends Thread{
	private String[] toProcess;
	private NewsSaver newsSaver;
	public GetterSaverAdapter(String[] tp){
		newsSaver = new NewsSaver();
		toProcess = tp;
	}
	public void run(){
		saveNews();
	}
	private void saveNews(){
		String[] titlesToSave = toProcess[0].split("*");
		String[] datesToSave = toProcess[1].split("*");
		String[] headersToSave = toProcess[2].split("*");
		String[] urlsToSave = toProcess[3].split("*");
		String[] tagsToSave = toProcess[4].split("*");
		for (int i = 0; i < toProcess.length; i++) {
			String[] toSave = {titlesToSave[i], datesToSave[i], headersToSave[i], urlsToSave[i], tagsToSave[i]};
			newsSaver.saveInDataBase(toSave);
		}
	}
}
