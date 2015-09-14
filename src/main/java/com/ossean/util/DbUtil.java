package com.ossean.util;

import com.ossean.dao.FlowDao;
import com.ossean.main.Configuration;
import com.ossean.main.Project;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by houxiang on 15/9/13.
 */
@Component
public class DbUtil {

    @Resource
    private FlowDao flowDao ;

    private static File file = new File("/Users/houxiang/Desktop/open_source_project/result.txt");

    private static File file1 = new File("/Users/houxiang/Desktop/open_source_project/relative_memo_result.txt");

    private static List<Project> pros = new LinkedList<Project>();

    public static List<Project> readPro(){
        try {
            LineIterator lit = FileUtils.lineIterator(file);
            while (lit.hasNext()){
                String line = lit.next();
                Project pro =new Project();
                if (line != null){
                    String[] tmps = line.split("\t");
                    pro.setId(Integer.getInteger(tmps[0]));
                    pro.setTags(tmps[1]);
                    pros.add(pro);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  pros ;
    }


    public void fileHandler(){
        int begin = 0 ;
        int limit = 10000;
        List<String> lines = new LinkedList<String>();
        while (begin<117954){
            List<Project> ans = flowDao.selProjects(begin,limit);
            begin += limit ;
            System.out.println(begin);
            lines.clear();
            for (Project p : ans){
                if (p.toString()!=null){
                    lines.add(p.toString());
                }
            }
            System.out.println(lines);
            try {
                FileUtils.writeLines(file,lines,"\n",true);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        lines.clear();
        begin -= 10000;
        List<Project> ans = flowDao.selProjects(begin , 117954 - begin);
        for (Project p : ans){
            if (p.toString()!=null){
                lines.add(p.toString());
            }
        }
        try {
            FileUtils.writeLines(file,lines,"\n",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fileHandler2(){
        int end = flowDao.selMax();
        for (int i = 1 ; i<=end ; i++){
            List<Integer> list = flowDao.selData(i);
            String ans = i+"\t"+list.toString()+"\n";
            try {
                FileUtils.writeStringToFile(file1,ans,true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args){
        DbUtil du = Configuration.getConfig().getBean(DbUtil.class);
        du.fileHandler2();
    }


}
