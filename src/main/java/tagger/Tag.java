package tagger;

public class Tag {

	public enum DataSetType {Person, Location, Category, Company, Tag}

	private String content;
//	private int timesWasQueried;
//	private int timesFound;

	public static final int MIN_LEN_TAG = 4;
	private DataSetType dataSet;

	public Tag(String content, DataSetType dataSet){
		this.content = content;
		this.dataSet =  dataSet;
	}

	public String getContent(){
		return this.content;
	}
	
	public DataSetType getDataSet() {
		return this.dataSet;
	}

//	public void queried(){
//		this.timesWasQueried++;
//	}
//
//	public void found(){
//		this.timesFound++;
//	}



}
