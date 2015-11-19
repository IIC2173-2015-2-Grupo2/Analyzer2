package saver;

import java.util.ArrayList;

import model.NewsItemData;

public class DebuggerNewsSaver {
	private ArrayList<NewsItemData> allNews;
	public DebuggerNewsSaver(){
		allNews = new ArrayList<NewsItemData>();
	}
	
	public void saveInDatabase(NewsItemData newItem){
		System.out.println("Ha llegado la siguiente noticia: \n" + newItem.toString());
		allNews.add(newItem);
	}
}
