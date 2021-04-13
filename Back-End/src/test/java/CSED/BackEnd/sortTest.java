package CSED.BackEnd;

import java.util.ArrayList;

import CSED.BackEnd.Sort.subjectSort;
import CSED.BackEnd.filter.Info;

public class sortTest {

	public static void main(String[] args) {
		
		ArrayList<Info> arr = new ArrayList<Info>() ;
		Info f1 = new Info(new ArrayList<String>(),"hmed","213","123",2);
		Info f2 = new Info(new ArrayList<String>(),"med","213","123",2);
		Info f3 = new Info(new ArrayList<String>(),"tmed","213","123",2);
		Info f4 = new Info(new ArrayList<String>(),"aed","213","123",2);
		arr.add(f1) ; 
		arr.add(f2) ; 
		arr.add(f3) ; 
		arr.add(f4) ; 
		
		subjectSort sr = new subjectSort() ;
		
		sr.sort(arr)  ;
		for (Info info : arr) {
			System.out.println(info.getSubject());
		}

	}

}
