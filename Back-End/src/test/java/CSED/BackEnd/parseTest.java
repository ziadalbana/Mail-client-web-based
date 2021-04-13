package CSED.BackEnd;


import CSED.BackEnd.Controller.parse;
import CSED.BackEnd.Files.FilesUtilities;
import CSED.BackEnd.builder.Director;

import CSED.BackEnd.builder.Email;
import CSED.BackEnd.builder.Ibuilder;
import CSED.BackEnd.builder.builder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;


//["John","ziad"]
public class parseTest {
    Ibuilder builder = new builder();
    Director director = new Director(builder);
    @Test
    public void parseEmailTest() throws JsonProcessingException {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("ziad","ahmed"));
        list.forEach(System.out::println);
        director.compose(list,
                "game",
                "this game",
                2,
                "sent",
                new LinkedList<>(),
                new LinkedList<>(),
                "19-5");
        Email testCase = director.getEmail();
        assertEquals(testCase.getSubject(),"game");
        assertEquals(testCase.getBody(),"this game");
        assertEquals(testCase.getImportance(),2);
        assertEquals(testCase.getType(),"sent");

        ObjectMapper o = new ObjectMapper();
        String json = o.writeValueAsString(testCase).toString();
        parse p = new parse();
        Email e = p.jsonTOEmail(json);
        e.getUsername().forEach(System.out::println);
        assertEquals(e.getSubject(),"game");
        assertEquals(e.getBody(),"this game");
        assertEquals(e.getImportance(),2);
        assertEquals(e.getType(),"sent");
    }

}