package com.ossean.main;

import com.ossean.dao.FlowDao;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by houxiang on 15/9/12.
 */

@Component
public class RelationCount {
    @Resource
    private FlowDao flowDao ;

    private File sourceFile = new File("/Users/houxiang/Desktop/open_source_project/relation_source_tmp.txt");

    private File targetFile = new File("/Users/houxiang/Desktop/open_source_project/result_of_relation");

    private List<Integer> list = null;

    private Set<Integer> baseSets = new HashSet<Integer>();

    private Set<Line> sortSets = new TreeSet<>();

    private final static int end = 300 ;

    public void Count(){
        int end = flowDao.selMax();
        for (int i = 1 ; i<=end ; i++){
            List<Integer> list1  = flowDao.selData(i);

            for(int j = 1 ; j<=end ; j++){
                sortSets.clear();
                baseSets.clear();
                List<Integer> list2 = flowDao.selData(j);
                baseSets.addAll(list1);
                baseSets.addAll(list2);
                float relation_weight = ((float)list1.size()+(float)list2.size()-(float)baseSets.size())/(float)baseSets.size();
                if (relation_weight>=0.001){
                    flowDao.insertRelation(i,j,relation_weight);
                    System.out.println(i+"---->"+j+" : "+relation_weight);
                }
            }
        }

    }

    /**
     * resource from file
     */
    public void Count2(){
        List<Project_Memos> baselist = readFile();
        Set<Integer> baseSets = new HashSet<>();
        for (Project_Memos pm1 : baselist){
            sortSets.clear();
            List<Integer> list1 = pm1.segMemos();
            baseSets.clear();
            for (Project_Memos pm2:baselist){
                Line line ;
                List<Integer> list2 = pm2.segMemos();
                baseSets.addAll(list1);
                baseSets.addAll(list2);
                float relation_weight = countWeight(list1.size(),list2.size(),baseSets.size());
                if (relation_weight>=0.003 && relation_weight !=1.0){
                    //String str = pm1.getId()+"\t"+pm2.getId()+"\t"+relation_weight+"\n";
                    line = new Line(pm1.getId() , pm2.getId() , relation_weight);
                    sortSets.add(line);
                }
            }
        }
        writeFile(sortSets);
        System.out.println(sortSets);

    }

    public float countWeight(int a , int b , int c){
        float weight =((float)a + (float)b-(float)c)/((float)a);
        return  weight ;
    }

    public void writeFile(Set<Line> sets){
        List<String> list =new LinkedList<>();
        int count = 0 ;
        for (Line line : sets){
            count ++ ;
            if (count<end) list.add(line.toString());
            else{
                break;
            }
        }
        if (targetFile!=null){
            try {
                FileUtils.writeLines(targetFile,list,true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public List<Project_Memos> readFile(){

        List<Project_Memos> promList = new LinkedList<>();
        try {
            LineIterator litr = FileUtils.lineIterator(sourceFile);
            while (litr.hasNext()){
                Project_Memos pm = new Project_Memos();
                String str = litr.next();
                String[] tmps = str.split("\t");
                if (tmps.length ==2) {
                    pm.setId(Integer.parseInt(tmps[0]));
                    pm.setRelative_memos(tmps[1]);
                    System.out.println(pm);
                    promList.add(pm);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return promList ;
    }
    public static void main(String[] args){
        ApplicationContext aContext = new ClassPathXmlApplicationContext(
                "classpath:/spring/applicationContext*.xml");
        RelationCount rc = aContext.getBean(RelationCount.class);
        rc.Count2();
    }
}

class Line implements Comparable<Line>{
    private int osp_id;
    private int relative_osp_id;
    private float weight ;


    Line(int osp_id , int relative_osp_id , float weight){

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
    public int compareTo(Line o) {
        return Float.compare(o.getWeight() , this.getWeight());
    }
}