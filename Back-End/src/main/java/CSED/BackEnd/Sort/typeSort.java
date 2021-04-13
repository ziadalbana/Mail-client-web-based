package CSED.BackEnd.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import CSED.BackEnd.filter.Info;

public class typeSort implements ISort {

	@Override
	public List<Info> sort(List<Info> all) {
		if (all.size()<= 1 )
			return all ; 
		
		Collections.sort(all,new Comparator<Info>() {
			@Override
			public int compare(Info o1, Info o2) {
				if (o1.getType().compareTo(o2.getType()) > 0 )
					return 1 ; 
				else if (o1.getType().compareTo(o2.getType()) < 0 )
					return -1 ;
				return 0;
			}});
		return all;
		
	}

}
