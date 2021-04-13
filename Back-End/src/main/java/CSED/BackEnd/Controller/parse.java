package CSED.BackEnd.Controller;

import CSED.BackEnd.builder.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class parse {
    Ibuilder builder = new builder();
    Director director = new Director(builder);

    public Email jsonTOEmail(String json){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss");
        LocalDateTime now = LocalDateTime.now();
        String time=format.format(now).replace(" ","_");
        JSONParser p = new JSONParser();
        JSONObject obj  = null ;
        try {
//            System.out.println(json);
//            System.out.println(p);
//            System.out.println(obj);
            obj = (JSONObject) p.parse(json);
            director.compose(
                    (ArrayList<String>) obj.get("username"),
                    obj.get("subject")+"",
                    obj.get("body")+"",
                    Integer.parseInt(obj.get("importance")+""),
                    obj.get("type")+"",
                    (ArrayList<String>) obj.get("attachment"),
                    new ArrayList<>(),
                    time
            );

            return director.getEmail();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String emailToJson(Email mail) throws JsonProcessingException {
        ObjectMapper o = new ObjectMapper();
        String json = o.writeValueAsString(mail).toString();
        return json;
    }

    public String jsonToWord(String json) {
        JSONParser p = new JSONParser();
        JSONObject obj  = null ;
        try{
            obj = (JSONObject) p.parse(json);
            return obj.get("word")+"";
        } catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }
    public attachmentInfo jsonToPath(String json){
        attachmentInfo n=new attachmentInfo();
        JSONParser p = new JSONParser();
        JSONObject obj  = null ;
        try{
            obj = (JSONObject) p.parse(json);
            n.setName(obj.get("name")+"");
            n.setPath(obj.get("path")+"");
            return n;
        } catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }
    public String jsonToUsername(String json){
        String username;
        JSONParser p = new JSONParser();
        JSONObject obj  = null ;
        try{
            obj = (JSONObject) p.parse(json);
            username=obj.get("userName")+"";
            return username;
        } catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }
}