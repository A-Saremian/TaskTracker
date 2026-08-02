package org.task.tracker;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class FileHandling implements TaskFile {
  
    private final TaskProperties taskProperties;
    private String json;

    public FileHandling(TaskProperties taskProperties) {
        this.taskProperties = taskProperties;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }


    @Override
    public void addFile() throws IOException {
        Object stat = (Object) filereader("status");
        if (stat instanceof Set<?>){
            if(!((Set<?>) stat).contains(taskProperties.getStatus())){
                fileWriter("status",taskProperties.getStatus(),true);
            }
        }else {
            fileWriter("status",taskProperties.getStatus(),true);
        }
    fileWriter(taskProperties.getID()+".json",json,false);
    fileWriter("allTask",taskProperties.getID(),true);
    fileWriter(taskProperties.getStatus(),taskProperties.getID(),true);
    }
    @Override
    public void updateFile() throws IOException {
        Set<String> task = (Set<String>) filereader("alltask");
        if (!task.contains(taskProperties.getID())) {
            System.out.println("project with this id does not exist.");
        } else {
            Set<String> stat = (Set<String>) filereader("status");
            for (String s : stat) {
                    Fileupdater(s, taskProperties.getID());
            }
            if(taskProperties.getStatus() != null) {
                fileWriter(taskProperties.getStatus(), taskProperties.getID(), true);
                System.out.println("if1");
            }
            if(!stat.contains(taskProperties.getStatus()) && taskProperties.getStatus() != null){
                fileWriter("status",taskProperties.getStatus(),true);
            }if(taskProperties.getStatus() != null){
                fileWriter(taskProperties.getID() + ".json", json, false);
            }
        }
    }
    public void Fileupdater(String name,String content) throws IOException {
        Set<String> cnt = (Set<String>) filereader(name);
        cnt.remove(content);
        if (cnt.isEmpty()){
            deleteFile(name);
            Fileupdater("status",name);

        }else{
            overwriter(name,cnt);
        }
        }


    @Override
    public void overwriter(String name, Set<String> contents) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(name,false));
        for (String s: contents){
            bw.write(s);
            bw.newLine();
        }
        bw.close();
    }

    @Override
    public void deleteFile(String name) throws IOException {
        if(!taskProperties.getID().equals(null)){
            File fil = new File(name+".json");
            if(fil.exists()){
            taskProperties.setStatus(null);
            setJson(null);
            updateFile();
            Fileupdater("alltask",name);
            fil.delete();
                System.out.println("success!");
                }
            }else {
            System.out.println("file dose not exists!");
            }
    }

    @Override
    public Object filereader(String name) throws IOException {
        BufferedReader br;
        Set<String> content = new HashSet<>();
        File fil = new File(name);
        if (fil.exists()) {
            FileReader flrd = new FileReader(fil);
            br = new BufferedReader(flrd);
            String line;
            while ((line = br.readLine()) != null) {
                content.add(line);
            }
        } else {
            return "name file dose not exists!";

        }
        br.close();
        return content;
    }
    public void fileWriter(String name,String content,Boolean appender) throws IOException {
        File fil = new File(name);
        fil.createNewFile();
        FileWriter fwr = new FileWriter(fil,appender);
        BufferedWriter br = new BufferedWriter(fwr);
        br.write(content+"\n");
        br.flush();
        br.close();
    }


}
