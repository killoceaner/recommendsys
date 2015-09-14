package com.ossean.main;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by houxiang on 15/9/13.
 */
public class Project_Memos{
    private int id;
    private String relative_memos;


    public List<Integer> segMemos(){
        List<Integer> list = new LinkedList<>();
        int length = relative_memos.length()-1;
        String str = this.relative_memos.substring(1,length);
        String[] tmps = str.split(",");
        for (String s:tmps){
            list.add(Integer.valueOf(s));
        }
        return list ;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelative_memos() {
        return relative_memos;
    }

    public void setRelative_memos(String relative_memos) {
        this.relative_memos = relative_memos;
    }


}
