package CSED.BackEnd.Sort;

public class sortFactory {
    public ISort getSortMethod(String attribute){
        if(attribute.equalsIgnoreCase("date"))
            return new dateSort();
        else if(attribute.equalsIgnoreCase("importance"))
            return new importanceSort();
        else if(attribute.equalsIgnoreCase("subject"))
            return new subjectSort();
        else if(attribute.equalsIgnoreCase("type"))
            return new typeSort();
        else if(attribute.equalsIgnoreCase("username"))
            return new usersSort();

        return null;
    }
}
