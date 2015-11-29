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
        if(!"null".equals(title))
            setFields.put(titleColumn, sanitizeText(title));
        if(!"null".equals(date))
            setFields.put(dateColumn, sanitizeText(date));
        if(!"null".equals(summary))
            setFields.put(summaryColumn, sanitizeText(summary));
        if(!"null".equals(body))
            setFields.put(bodyColumn, sanitizeText(body));
        if(!"null".equals(url))
            setFields.put(urlColumn, url);
        if(!"null".equals(image))
            setFields.put(imageColumn, image);
        if(!"null".equals(source))
            setFields.put(sourceColumn, sanitizeText(source));
        if(!"null".equals(language))
            setFields.put(languageColumn, language);
        if(!"null".equals(tags))
        	this.tags = sanitizeText(tags).replace("[", "").replace("]", "");
    }

	public HashMap<String, String> getSetFields() {
        return setFields;
    }

	public String getSource(){
		return setFields.get(sourceColumn);
	}
	
	public String getBody(){
		return setFields.get("body");
	}

	public String getTags() {
		return tags;
	}
	
	public void addTags(String tags){
		this.tags += tags;
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

	public String sanitizeText(String str){
		String toSanitize = str;
		toSanitize = str.replaceAll("\"","");
		toSanitize = str.replaceAll("\'","");
		toSanitize = toSanitize.replaceAll("'",""); 
		return toSanitize;
	}
}
