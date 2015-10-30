package Getter;

import java.util.ArrayList;

public class New {
	private String title, date, header, body, url, tags;
	public New(String t, String d, String h, String b, String u, String ts){
		title = t;
		date = d;
		header = h;
		body = b;
		url = u;
		tags = ts;
	}
	public String getTitle() {
		return title;
	}
	public String getDate() {
		return date;
	}
	public String getHeader() {
		return header;
	}
	public String getBody() {
		return body;
	}
	public String getUrl() {
		return url;
	}
	public String getTags() {
		return tags;
	}
}
