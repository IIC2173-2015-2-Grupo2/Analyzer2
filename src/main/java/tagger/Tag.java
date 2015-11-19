package tagger;

public class Tag {

	public enum DataSetType {PEOPLE, PLACES, CATEGORIES, COMPANIES, OTHER}

	private String content;
	private String startsWith;
	private int category;
//	private int timesWasQueried;
//	private int timesFound;

	public static final int TAG_CATEGORY_PEOPLE = 0;
	public static final int TAG_CATEGORY_COMPANIES = 1;
	public static final int TAG_CATEGORY_PLACES = 2;
	public static final int TAG_CATEGORY_CATEGORIES = 3;
	public static final int TAG_CATEGORY_OTHERS = 4;

	public static final int MIN_LEN_TAG = 4;
	private DataSetType dataSet;

	public Tag(String content, DataSetType dataSet){
		this.content = content;
		this.dataSet =  dataSet;
	}

	public String getContent(){
		return this.content;
	}

//	public void queried(){
//		this.timesWasQueried++;
//	}
//
//	public void found(){
//		this.timesFound++;
//	}



}
