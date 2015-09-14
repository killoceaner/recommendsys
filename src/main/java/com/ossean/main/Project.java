package com.ossean.main;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by houxiang on 15/9/13.
 */
public class Project {
    private  int id;
    private  String tags;


    public List<String> seqTags(){
        List<String> list = new LinkedList<String>();
        String[] tagsArray = this.tags.split(">,<");

        for (String str:tagsArray){
            list.add(str);
        }
        return list ;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String toString(){
        String str = id+"\t"+tags+"\n";
        return str ;
    }
}
