package CSED.BackEnd.Controller;


import CSED.BackEnd.Proxy.User;
        import CSED.BackEnd.Service.Service;
        import CSED.BackEnd.Service.emailOperation;
import CSED.BackEnd.builder.*;

        import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
public class controller {
    Service service = new Service();
    emailOperation ops = new emailOperation();
    parse p = new parse();

    @GetMapping("/login/{userName}/{password}")
    public boolean login(@PathVariable("userName") String userName,@PathVariable("password") String password){
        return service.SignIn(userName,password);
    }

    @GetMapping("/{userName}")
    public boolean checkExist(@PathVariable("userName") String userName){
        return service.check(userName);
    }
    @PostMapping("/")
    public boolean SignUp(@RequestBody User user){
        return service.SignUp(user);
    }

    @GetMapping("/getEmails/{filter}")
    public List<Object> getEmailsType(@PathVariable("filter") String filter){
        return  service.getEmailsType(filter.toLowerCase());
    }

    @GetMapping("/userName")
    public String getUserName(){
        return Service.getUserName();
    }

    @GetMapping("/getEmails/{filter}/{choose}/{word}")
    public List<Object> getEmailsFiltered(@PathVariable("filter") String filter,@PathVariable("choose") String choose,@PathVariable("word") String word){
        return  service.getEmailFiltered(filter.toLowerCase(),choose,word);
    }
    @GetMapping("/sort/{filter}/{type}")
    public List<Object> getEmailsSorted(@PathVariable("filter") String filter,@PathVariable("type") String type){
        return service.getEmailSorted(filter.toLowerCase(),type);
    }

    @GetMapping("/show/{date}")
    public Email showEmail(@PathVariable("date") String date){
        return service.getEmail(date);
    }

    @GetMapping("/setting")
    public List<Object> getUserData(){
        return service.getUserData();
    }

    @GetMapping("/getContact")
    public List<Object> getContact(){
        return service.getContacts();
    }
    @PostMapping("/addContact")
    public void addContact(@RequestBody String userName){
        String username=p.jsonToUsername(userName);
        System.out.println(username);
        service.addContact(username);
    }

    @DeleteMapping("/deleteContact/")
    public void deleteContact(@RequestBody String userName){
        System.out.println( "this"+ userName);
    	String name =p.jsonToUsername(userName);
    	System.out.println( "this"+ name);
        service.deleteContact(name);
    }

    @PostMapping("/updateSetting")
    public void updateSetting(@RequestBody User user){
        service.updateSetting(user);
    }
    @PostMapping("/search/")
    public List<Object> search(@RequestBody String words){
        return service.search(p.jsonToWord(words));
    }

    @DeleteMapping("/deleteUser")
    public void deleteUser(){
        service.deleteUser();
        service.reset();
        return;
    }
    @DeleteMapping("/{date}")
    public boolean deleteEmail(@PathVariable("date") String date){
        boolean done = ops.deleteEmail(date);
        service.load();
        return done;
    }

    @DeleteMapping("/deleteDraft/{date}")
    public boolean deleteDraft(@PathVariable("date") String date){
        boolean done = ops.deleteDraft(date);
        service.load();
        return done;
    }
    @PostMapping("/sent")
    public boolean sentEmail(@RequestBody String json){
        Email e = p.jsonTOEmail(json);
        boolean done = ops.sentEmail(e);
        service.load();
        return done;
    }
    @PostMapping(value="/attachment" )
    public void sentAttachment(@RequestPart("files") List<MultipartFile> myfile)  {
        ops.attachmentSet(myfile);
    }
    @PostMapping("/download")
    public Attachment downloadAttachment(@RequestBody String json){
        attachmentInfo attachmentnfo =p.jsonToPath(json);
        return service.getByteArray(attachmentnfo.getName(),attachmentnfo.getPath());
    }
    @PostMapping("/draft")
    public boolean saveDraft(@RequestBody String json){
        Email e = p.jsonTOEmail(json);
        boolean done = ops.draftEmail(e);
        service.load();
        return done;
    }
    @GetMapping("/reset")
    public void reset(){
        service.reset();
    }

}

