package CSED.BackEnd;

//import CSED.BackEnd.Service.Service;
import CSED.BackEnd.Service.emailOperation;
import CSED.BackEnd.builder.Director;
//import CSED.BackEnd.builder.Email;
import CSED.BackEnd.builder.Ibuilder;
import CSED.BackEnd.builder.builder;
//import org.junit.jupiter.api.Test;



public class operationtest {
    Ibuilder builder = new builder();
    emailOperation n=new emailOperation();
    Director director = new Director(builder);
    /*@Test
    public void sentTest(){
        ArrayList<String> list = new ArrayList<>(Arrays.asList("ziad","ahmed"));
        director.compose(list,
                "game",
                "this game",
                2,
                "sent",
                new LinkedList<>().add("ziad.txt"),
                "19-5");
        Email testCase = director.getEmail();
        System.out.println(n.sentEmail(testCase));
    }
    @Test
    public void drafttest(){
        ArrayList<String> list = new ArrayList<>(Arrays.asList("ziad","ahmed"));
        director.compose(list,
                "game",
                "this game",
                2,
                "draft",
                "ziad.txt",
                "19-5");
        Email testCase = director.getEmail();
        System.out.println(n.draftEmail(testCase));
    }
    @Test
    public void deletetest(){
        System.out.println(n.deleteEmail("19-5"));
    }*/
}
