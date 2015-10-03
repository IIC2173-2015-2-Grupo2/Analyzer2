package Getter;

import java.util.ArrayList;
import java.util.List;

import Tagger.Tagger;

public class GetterTaggerAdapter extends Thread{
	private String[] bodies;
	private String stringBodies;
	private Tagger tagger;
	public GetterTaggerAdapter(String b){
		tagger = new Tagger();
		stringBodies = b;
	}
	public void run(){
		sendList();
	}
	private void sendList(){
		bodies = stringBodies.split("*");
		for (String string : bodies) {
			try {
				tagger.tagNews(string);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
