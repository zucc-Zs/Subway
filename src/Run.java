import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.*;

public class Run {
    public static void load(String filename) {
        ReadTxt.load(filename);
    }
    public static void line(String filename, String line, String output) throws Exception{
        List<BeanStation> list = ReadTxt.loadMap(filename);
        int count = 0;
        for (int i=0;i<list.size();i++) {
            if(list.get(i).getLineName().equals(line))
                count++;
        }
        if(count == 0)
            throw new Exception("线路不存在！");

        FileWriter fileWriter = new FileWriter(output);//创建文本文件
        fileWriter.write(line+":"+"\r\n");
        for (int i=0;i<list.size();i++) {
            if(list.get(i).getLineName().equals(line))
                fileWriter.write(list.get(i).getStationName()+"\r\n");//写入 \r\n换行
        }
        fileWriter.flush();
        fileWriter.close();
    }

    public static void serach(String filename, String start, String end,String output) throws Exception {
        List<BeanStation> list = ReadTxt.loadMap(filename);
        List<Integer> alist = ReadTxt.loadMatrix(filename);
        int sum = 0;
        for (int i=0;i<alist.size();i++)
            sum += alist.get(i);

        //保存站点名称
        List<String> blist = new ArrayList<String> ();
        for(int i = 0 ; i < list.size() ; i++){
            blist.add(list.get(i).getStationName());
        }

        int max = 99999;
        int a[][] = new int[sum][sum];
        for(int i=0;i<sum;i++){
            for(int j=0;j<sum;j++){
                a[i][j]=a[j][i]=max;
            }
        }
        for(int i=0;i<sum;i++){
            for(int j=0;j<=i;j++){
                if(blist.get(j).equals(blist.get(i))){
                    a[i][j]=a[j][i]=0;
                }
            }
        }
        int count = 0;
        for(int i=0;i<alist.size();i++){
            for(int j=1;j<alist.get(i);j++){
                count++;
                a[count][count-1]=a[count-1][count]=1;
            }
            count++;
        }

        int startOrder = blist.indexOf(start);
        if( startOrder == -1 )
            throw new Exception("起点站不存在");

        int endOrder = blist.indexOf(end);
        if( endOrder == -1 )
            throw new Exception("终点站不存在");

        ArrayList<Integer> clist = Dijkstra.dijkstra(endOrder, a, startOrder);
        ArrayList<String> dlist = new ArrayList<String> ();
        for(int i = 0 ; i < clist.size() ; i++){
            dlist.add(blist.get(clist.get(i)));
        }
        Set<String> set = new HashSet<String>();
        for(String str: dlist){
            set.add(str);
        }

        if(set.size() == 1)
            throw new Exception("不存在通路！");

        FileWriter fileWriter = new FileWriter(output);//创建文本文件
        fileWriter.write("站点数："+set.size()+"\r\n");//写入 \r\n换行

        fileWriter.write(blist.get(clist.get(0))+' ');
        for(int i=1;i<clist.size(); i++) {
            if (!blist.get(clist.get(i)).equals(blist.get(clist.get(i - 1))))
                fileWriter.write(blist.get(clist.get(i))+' ');
            else if(i != clist.size()-1) {
                fileWriter.write("\r\n");
                fileWriter.write(list.get(clist.get(i)).getLineName()+"\r\n");
            }
        }
        fileWriter.flush();
        fileWriter.close();
    }
}
