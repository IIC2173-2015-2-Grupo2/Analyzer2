package model;


public class NewsItemData {

    public static String titleColumn = "title";
    public static String dateColumn = "date";
    public static String summaryColumn = "summary";
    public static String urlColumn = "url";
    public static String imageColumn = "image";
    public static String sourceColumn = "source";
    private HashMap<String, String> setFields;
		private String[] tags;

    public NewsItemData(){
        setFields = new HashMap<>();
    }

		public HashMap<String, String> getSetFields() {
        return setFields;
    }

    public void setTitle(String title) {
			if(title.equals("null") == false)
        setFields.put(titleColumn, title);
    }

    public void setSummary(String summary) {
			if(summary.equals("null") == false)
        setFields.put(summaryColumn, summary);
    }

    public void setDate(String date) {
			if(date.equals("null") == false)
        setFields.put(dateColumn, date);
    }

    public void setUrl(String url) {
			if(url.equals("null") == false)
        setFields.put(urlColumn, url);
    }

    public void setImage(String image) {
			if(image.equals("null") == false)
        setFields.put(imageColumn, image);
    }

    public void setSource(String source) {
			if(source.equals("null") == false)
        setFields.put(sourceColumn, source);
    }

		public void setTags(String[] tags){
			this.tags = tags;
		}

		public String[] getTags(){
			return tags;
		}
}
