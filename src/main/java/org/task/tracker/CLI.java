package org.task.tracker;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CLI implements Task {
    Scanner sc;
    JsonHandling jsg;
    FileHandling filehandl;
    LocalDateTime date;
    private String arg;
    private String name;
    private TaskProperties taskProperties;
    public CLI() {
        sc   = new Scanner(System.in);
        jsg  = new JsonHandling();
        taskProperties = new TaskProperties();
        filehandl = new FileHandling(taskProperties);
    }

    public void userinteraction() throws IOException {
        System.out.println("wellcome to task tracker");
        System.out.println("enter 1 for add task\nenter 2 for update task\nenter 3 for delete task\nenter 4 for preview task\nenter 5 for list");
        while(true) {
             arg = sc.nextLine();
            if (arg.contains("1")) {
                add();
            }
            if(arg.contains("2")){
                Update();
            }
            if(arg.contains("3")){
                deletetask();
            }
            if(arg.contains("4")){
                String Id = null;
                System.out.println("enter ID: ");
                Id = sc.nextLine();
                preview(Id+".json");
            }
            if(arg.contains("5")){
                System.out.println("select one of status from status list, or enter \"alltask\" keyword:");
                preview("status");
                System.out.println("status is: ");
                String Id = null;
                Id = sc.nextLine();
                preview(Id);
            }
        }

    }

    @Override
    public void add() throws IOException {
        Map<String,Map<String,String>> task;
        task = settProperties();
        Map<String,String> taskptrs = task.get(name);
        String Id = null;
        Id = UUID.randomUUID().toString();
        taskProperties.setID(Id);
        taskptrs.put("ID", Id);
        taskptrs.put("createdAt",date.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        task.put(name,taskptrs);
        System.out.println("your id project is: "+Id);
        String json = jsg.toJson(task);
        filehandl.setJson(json);
        filehandl.addFile();
    }
    public Map<String,Map<String,String>> settProperties() throws IOException{
        Map<String,String> taskprts = new HashMap<>();
        Map<String,Map<String,String>> task = new HashMap<>();
        System.out.println("enter project name: ");
        name = sc.nextLine();
        taskProperties.setName(name);
        System.out.println("enter descripton: ");
        String desc = sc.nextLine();
        taskProperties.setDescription(desc);
        System.out.println("enter status: ");
        String status = sc.nextLine();
        taskProperties.setStatus(status);
        taskprts.put("Descripton",desc);
        taskprts.put("Status",status);
        task.put(name,taskprts);
        return task;
    }

    @Override
    public void preview(String name) throws IOException {
            Object taskview = filehandl.filereader(name);
            System.out.println(taskview.toString());
    }

    public void Update() throws IOException {
        Map<String,Map<String,String>> task;
        String Id = null;
        System.out.println("enter ID: ");
        Id = sc.nextLine();
        task = settProperties();
        Map<String,String> taskptrs = task.get(name);
        taskProperties.setUpdatedAt(date.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        taskptrs.put("updatedAt",taskProperties.getUpdatedAt());
        taskptrs.put("ID",Id);
        String json = jsg.toJson(task);
        taskProperties.setID(Id);
        filehandl.setJson(json);
        filehandl.updateFile();
    }

    @Override
    public void deletetask() throws IOException {
        System.out.println("enter ID: ");
        String Id = sc.nextLine();
        taskProperties.setID(Id);
        filehandl.deleteFile(Id);
    }
}
