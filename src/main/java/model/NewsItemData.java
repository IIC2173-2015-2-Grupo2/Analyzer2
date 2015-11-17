package model;

import java.util.HashMap;

public class NewsItemData {

    public static String titleColumn = "title";
    public static String dateColumn = "date";
    public static String summaryColumn = "summary";
    public static String bodyColumn = "body";
    public static String urlColumn = "url";
    public static String imageColumn = "image";
    public static String sourceColumn = "source";
    public static String languageColumn = "language";
    public static String tagsColumn = "tags";
    private HashMap<String, String> setFields;
	private String tags;

    public NewsItemData(String title, String date, String summary, String body, String url, String image, String source, String language, String tags){
        setFields = new HashMap<>();
        if(!title.equals("null"))
            setFields.put(titleColumn, title);
        if(!date.equals("null"))
            setFields.put(dateColumn, date);
        if(!summary.equals("null"))
            setFields.put(summaryColumn, summary);
        if(!body.equals("null"))
            setFields.put(bodyColumn, body);
        if(!url.equals("null"))
            setFields.put(urlColumn, url);
        if(!image.equals("null"))
            setFields.put(imageColumn, image);
        if(!source.equals("null"))
            setFields.put(sourceColumn, source);
        if(!language.equals("null"))
            setFields.put(languageColumn, language);
        if(!language.equals(""))
            setFields.put(tagsColumn, tags);
    }

	public HashMap<String, String> getSetFields() {
        return setFields;
    }

	public String getBody(){
		return setFields.get("body");
	}

	public String getTags() {
		return setFields.get("tags");
	}
	public String getLanguage(){
		return setFields.get("language");
	}
	@Override
	public String toString(){
		return "Title: " + setFields.get("title") + "\ndate: " + setFields.get("date") + "\nsummary: " + setFields.get("summary") + 
				"\nbody: " + setFields.get("body") + "\nurl: " + setFields.get("url") + "\nimage: " + setFields.get("image") +
				"\nsource: " + setFields.get("source") + "\nlanguage: " + setFields.get("language") + "\ntags: " + tags;
	}


}
