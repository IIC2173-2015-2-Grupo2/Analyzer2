package tagger;



public class Tag {
	
	public enum DataSetType {PEOPLE, PLACES, CATEGORIES, COMPANIES, OTHER}

	


	public static final int MIN_LEN_TAG = 4;

	private String content;
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
