package CSED.BackEnd.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import CSED.BackEnd.filter.Info;

public class usersSort implements ISort{

	@Override
	public List<Info> sort(List<Info> all) {
		if (all.size()<= 1 )
			return all ; 
		for (Info info : all) {
			if(info.getUserName().size() <= 1)
				continue ;
			info.getUserName().sort(new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					if (o1.compareTo(o2) > 0)
						return 1 ;
					else if (o1.compareTo(o2) < 0)
						return -1 ;
					return 0;
				}

			});
	}
		
		Collections.sort(all,new Comparator<Info>() {
			@Override
			public int compare(Info o1, Info o2) {
				System.out.println("here");
				if (o1.getUserName().get(0).compareTo(o2.getUserName().get(0)) > 0 )
					return 1 ; 
				else if (o1.getUserName().get(0).compareTo(o2.getUserName().get(0)) < 0 )
					return -1 ;
				return 0;
			}
			
		});
		
		return all;
	}

}
