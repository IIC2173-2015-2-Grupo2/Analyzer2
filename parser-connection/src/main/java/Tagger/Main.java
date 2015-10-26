package Tagger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {
	public static void main(String[] args) throws IOException, InterruptedException{
		
		//System.out.println(Feed.getGoogleTrends(2014, 10, 10));
		
		HashSet<String> hs = new HashSet<String>();
		
		for(int year = 2016 ; year <= 2016 ; year++){
			for(int month = 10; month <= 10 ; month++){
				for(int day = 1; day <= 1 ; day+=3){
					ArrayList<String> kws = Feed.parseTrendsJSON(Feed.getGoogleTrends(year, month, day, 38));
					hs.addAll(kws);
				}
			}
		}
		/*
		
		
		for(int year = 2011 ; year <= 2014 ; year++){
			for(int month = 1; month <= 12 ; month++){
				hs.addAll(   Feed.parseChartsJSON(     Feed.getGoogleTopCharts(year, month)   )   );

			}
		}
		
		
		int[] years = {2008,2009,2011,2012,2013,2014};
		for (int i = 0; i < years.length; i++) {
			hs.addAll(   Feed.parseChartsJSON(     Feed.getGoogleTopChartsLocation(years[i], "CL")   )   );
		}
		*/

		
		System.out.println("found " + hs.size() + " keywords");
		System.out.println("_________________________________________________________");
		int i = 1;
		for (String string : hs) {
			System.out.println(i + ": " +string);
			i++;
		}
		
		
		
	}
}
