package com.ossean.main;

import com.ossean.dao.FlowDao;
import com.ossean.util.DbUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by houxiang on 15/9/13.
 */
@Component
public class SimilityCount {

    @Resource
    private FlowDao flowDao ;

    private File sourceFile = new File("/Users/houxiang/Desktop/open_source_project/result.txt");

    private File targetFile = new File("/Users/houxiang/Desktop/open_source_project/simility_result.txt");

    private List<Project> pros = new LinkedList<Project>();

    private Set<Simility_line> sortSets = new TreeSet<>();

    private final static int end = 300;


    public SimilityCount(){

    }
    /**
     * resources from file
     */
    public void CountSimility1() {
        List<Project> pros = readPro();
        Set<String> baseSets = new HashSet<String>();
        List<String> list1 = new LinkedList<String>();
        List<String> list2 = new LinkedList<String>();

        for (Project pro1 : pros){
            sortSets.clear();
            list1.clear();
            baseSets.clear();
            if (pro1!=null){
                list1 = new LinkedList<String>(pro1.seqTags());
            }
            for (Project pro2 :pros){
                Simility_line line ;
                baseSets.clear();
                list2.clear();
                list2 = new LinkedList<String>(pro2.seqTags());
              //  int size = list2.size();
                baseSets.addAll(list1);
                baseSets.addAll(list2);
                float weight = countWeight(list1.size() , list2.size() , baseSets.size());
                if (weight!=0.0){
                    line = new Simility_line(pro1.getId() , pro2.getId() , weight);
                    sortSets.add(line);
                    //System.out.println(pro1.getId() + "------->" + pro2.getId() + ": " + weight);

                }else {
                    continue;
                }
            }
            writePro(sortSets);
            System.out.println(sortSets);
        }
    }

    public void writePro(Set<Simility_line> sets){
        int count = 0 ;
        List<String> list = new LinkedList<>();
        for(Simility_line line : sets){
            count++;
            if (count < end) {
                list.add(line.toString());
            }else{
                break;
            }
        }

        if(targetFile!=null){
            try {
                FileUtils.writeLines(targetFile,list,true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public List<Project> readPro() {

            try {
                LineIterator lit = FileUtils.lineIterator(sourceFile);
                while (lit.hasNext()) {
                    String line = lit.next();
                    Project pro = new Project();
                    if (line.split("\t").length == 2) {
                        String[] tmps = line.split("\t");
                        pro.setId(Integer.valueOf(tmps[0]));
                        String tags = tmps[1].substring(1, tmps[1].length() - 2);
                        pro.setTags(tags);
                        pros.add(pro);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return pros;
        }


    public float countWeight(int a , int b , int c){
        float weight =((float)a + (float)b-(float)c)/((float)c);
        return  weight ;
    }

    public static void main(String[] args){
        SimilityCount sc = Configuration.getConfig().getBean(SimilityCount.class);
        sc.CountSimility1();
    }
}

class Simility_line implements Comparable<Simility_line>{
    private int osp_id;
    private int relative_osp_id;
    private float weight ;

    Simility_line(int osp_id , int relative_osp_id , float weight){
        super();
        this.osp_id = osp_id ;
        this.relative_osp_id = relative_osp_id ;
        this.weight = weight ;
    }
    public int getOsp_id() {
        return osp_id;
    }

    public void setOsp_id(int osp_id) {
        this.osp_id = osp_id;
    }

    public int getRelative_osp_id() {
        return relative_osp_id;
    }

    public void setRelative_osp_id(int relative_osp_id) {
        this.relative_osp_id = relative_osp_id;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        String str = osp_id +"\t" + relative_osp_id+"\t"+weight+"\n";
        return str ;
    }

    @Override
    public int compareTo(Simility_line o) {
        return Float.compare( o.getWeight(),this.getWeight());
    }
}