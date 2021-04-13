package CSED.BackEnd.Files;

import CSED.BackEnd.builder.Email;
import CSED.BackEnd.filter.Info;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilesUtilities {
    private  File current;

    /**
     * Check if the file exist
     * @param path of the file
     * @return True if the file exists
     */
    public boolean exist(String path){
        current=new File(path);
        return current.exists();
    }

    /**
     * Deletes a directory recursively
     * @param path of the directory
     * @throws IOException
     */
    public void delete(String path) throws IOException {
        FileUtils.deleteDirectory(new File(path));
    }

    /**
     * Create new directory
     * @param path of the file to be created
     * @return True if the file created successfully
     */
    public boolean makdir(String path){
        current=new File(path);
        return current.mkdirs();
    }

    /**
     * change the name of a file
     * @param path
     * @param newName
     * @return true if it is changed successfully
     */
    public boolean rename(String path,String newName){
        current=new File(path);
        File news =new File(current.getParent()+"\\"+ newName);
        return current.renameTo(news);
    }

    /**
     * make the path of child file from parent path and child name
     * @param parent
     * @param child
     * @return the path of the child file
     */
    public String makPath(String parent,String child){
        File file=new File(parent,child);
        return file.getAbsolutePath();
    }

    /**
     * make the path of baby file from parent path, child name
     * and baby name
     * @param parent
     * @param child
     * @param baby
     * @return  the path of the baby file
     */
    public String makPath(String parent,String child,String baby){
        File file=new File(this.makPath(parent, child),baby);
        return file.getAbsolutePath();
    }

    /**
     * reads all lines in the file
     * @param path
     * @return lines of the file
     * @throws IOException
     */
    public List<String> read(String path)  {
        try{
            List<String> lines= Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
            return lines;
        }catch (IOException e){
            return null;
        }
    }
    /**
     * reads all Mails from index file
     * @param userName
     * @return lines of the index file
     * @throws IOException
     */
    public List<Info> readAllMails(String userName) {
        try{
            String path = this.makPath("users",userName,"AllMails");
            this.makdir(path);
            current = new File(this.makPath(path,"index.txt"));
            BufferedReader in = new BufferedReader(new FileReader(current));
            List<Info> Mails = new ArrayList<>();
            String line;
            while((line = in.readLine()) != null){
                ArrayList<String> names = new ArrayList<>(Arrays.asList(line.split("%")));
                String[] data = in.readLine().split("%");
                Mails.add(new Info(names,data[0],data[1],data[2],Integer.parseInt(data[3])));
            }
            return  Mails;
        }catch (IOException e){
            return null;
        }
    }

    /**
     * write the lines in the text file
     * @param path of the text file you want to write in
     * @param lines in form of a list
     * @param append indicating whether or not to append the data written.
     */
    public void write(String path,List<String> lines ,boolean append){
        try {
            current=new File(path);
            current.createNewFile();
            FileWriter fw = new FileWriter(current,append);
            BufferedWriter bw = new BufferedWriter(fw);
            while(!lines.isEmpty()) {
                bw.write(lines.remove(0));
                bw.newLine();
            }
            bw.close();fw.close();
        } catch (IOException e) {
             e.printStackTrace();
        }

    }
    /**
     * write the name in the file
     * @param path of the text file you want to write in
     * @param name to be added in the file
     */
    public void writeContact(String path,String name){
        try {
            current=new File(path);
            current.createNewFile();
            FileWriter fw = new FileWriter(current,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(name);
            bw.newLine();
            bw.close();fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * write the elements in the list on one line seperated by '%'
     * @param path of the text file you want to write in
     * @param elements in form of a list
     * @param append indicating whether or not to append the data written.
     */
    public void writeLine(String path,List<String> elements ,boolean append){
        try {
            current=new File(path);
            current.createNewFile();
            String line = "";
            for(int i=0;i<elements.size();i++){
                line += elements.get(i);
                if(i<elements.size()-1)
                    line += "%";
            }
            FileWriter fw = new FileWriter(current,append);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(line);
            bw.newLine();
            bw.close();fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * write the list of infos
     * @param path of the text file you want to write in
     * @param infos in form of a list<Info>
     */
    public void writeInfos(String path, List<Info> infos){
        current=new File(path);
        try {
            FileWriter fw = new FileWriter(current,false);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        infos.forEach( info ->{
            this.writeLine(path,info.getUserName(),true);
            this.writeLine(path,info.getAttributes(),true);
        });
    }

    /**
     * read
     * @param userName
     * @param date
     * @return
     */
    public Email getEmail(String userName,String date){
        String path = this.makPath(this.makPath("users",userName),"AllMails",date);
        path = this.makPath(path,"email.json");
        ObjectMapper mapper = new ObjectMapper();
        Email Mail = null;
        try {
            Mail = mapper.readValue(new File(path), Email.class);
            return Mail;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
