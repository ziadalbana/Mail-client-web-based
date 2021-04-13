package CSED.BackEnd.Search;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import CSED.BackEnd.Files.FilesUtilities;
import CSED.BackEnd.Proxy.IProxy;
import CSED.BackEnd.Proxy.Proxy;
import CSED.BackEnd.Proxy.Singleton;
import CSED.BackEnd.Proxy.User;
import CSED.BackEnd.Service.Service;
import CSED.BackEnd.Service.emailOperation;
import CSED.BackEnd.builder.Email;
import CSED.BackEnd.builder.Ibuilder;
import CSED.BackEnd.builder.builder;
import CSED.BackEnd.filter.Info;
import CSED.BackEnd.builder.Director;




public class Search {

	Singleton userMails = Singleton.getInstance();
	List<Info> oldInfo = userMails.getInfos();
	private List<Info> newInfo = new ArrayList<>();
	FilesUtilities filesutillties = new FilesUtilities();




	public List<Info> search(String str){
		Service service = new Service();
		List<Info> oldInfo = userMails.getInfos();

		String[] words = str.split(" ");
		for (int i=0 ;i<oldInfo.size();i++)
		{
			Info mailInfo = oldInfo.get(i);
			Email email = filesutillties.getEmail(service.getUserName(), mailInfo.getDate());
			String subject  = email.getSubject();
			boolean exist = containsWords(subject,words);
			if (exist) {
				newInfo.add(mailInfo);
				continue;
			}
			String body = email.getBody() ;
			exist = containsWords(body, words);
			if (exist)
				newInfo.add(mailInfo);
		}
		return newInfo ;
	}

	private static boolean containsWords(String inputString, String[] items) {
		boolean found = true;
		for (String item : items) {
			if (!inputString.contains(item)) {
				found = false;
				break;
			}
		}
		return found;
	}


}