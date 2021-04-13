package CSED.BackEnd.Sort;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import CSED.BackEnd.filter.Info;

public class dateSort implements ISort {

	@Override
	public List<Info> sort(List<Info> all) {
		if (all.size()<= 1 )
			return all ; 
		Collections.sort(all,new Comparator<Info>(){
			@Override
			public int compare(Info o1, Info o2) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
				Date d1 = null; 
				Date d2 = null; 
				try {
					 d1 = df.parse(o1.getDate().replace("_"," "));
					 d2 = df.parse(o2.getDate().replace("_"," ")) ;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				if (d1.compareTo(d2) > 0)
					return -1 ; 
				else if (d1.compareTo(d2) < 0)
					return 1 ; 
				return 0;
			}	
		});
		return all;
	}
	
}
