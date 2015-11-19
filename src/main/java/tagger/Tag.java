package tagger;

public class Tag {

	public static final int MIN_LEN_TAG = 4;

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


	public Tag(String content){
//		timesFound = 0;
//		timesWasQueried = 0;
		this.content = content;
		String[] words = content.split(" ");

		String candidate = words[0];

		int word_index = 1;

		while(candidate.length() < MIN_LEN_TAG && word_index < words.length){
			candidate += " " + words[word_index];
		}

		this.startsWith = candidate;

	}

	public String getContent(){
		return this.content;
	}

	public String getStartsWith(){
		return this.startsWith;
	}

//	public void queried(){
//		this.timesWasQueried++;
//	}
//
//	public void found(){
//		this.timesFound++;
//	}



}
