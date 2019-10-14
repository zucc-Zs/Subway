import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.*;

public class ReadTxt {
    public static void load(String fileName) {
        File file = new File(fileName);

        try {
            BufferedReader bf = new BufferedReader(new FileReader(file));

            String content = "";

            while (content != null) {
                content = bf.readLine();

                if (content == null) {
                    break;
                }

                System.out.println(content);
            }
            bf.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<BeanStation> loadMap(String fileName) {
        List<BeanStation> list = new ArrayList<>();

        File file = new File(fileName);

        try {
            BufferedReader bf = new BufferedReader(new FileReader(file));

            String content = "";

            while (content != null) {
                content = bf.readLine();

                if (content == null) {
                    break;
                }

                String[] str = content.trim().split(" ");

                for(int i=1;i<str.length;i++) {
                    BeanStation station = new BeanStation();
                    station.setLineName(str[0]);
                    station.setStationName(str[i]);
                    station.setOrder(i);
                    list.add(station);
                }

            }
            bf.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Integer> loadMatrix(String fileName) {
        List<Integer> list = new ArrayList<>();

        File file = new File(fileName);

        try {
            BufferedReader bf = new BufferedReader(new FileReader(file));

            String content = "";

            while (content != null) {
                content = bf.readLine();

                if (content == null) {
                    break;
                }

                String[] str = content.trim().split(" ");

                list.add(str.length-1);

            }
            bf.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String [] args) throws Exception {
        load("/Users/zhaosheng/Desktop/软件工程/subway.txt");
        List<BeanStation> list = loadMap("/Users/zhaosheng/Desktop/软件工程/subway.txt");
        Scanner sc = new Scanner(System.in);
//        System.out.println("请输入搜索线路名称：");
//        String name = sc.nextLine();
//        for (int i=0;i<list.size();i++) {
//            if(list.get(i).getLineName().equals(name))
//                System.out.println(list.get(i).getStationName() );
//        }
        System.out.println();
        List<Integer> alist = loadMatrix("/Users/zhaosheng/Desktop/软件工程/subway.txt");
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

        System.out.println("请输入起点站：");
        String startName = sc.nextLine();
        int startOrder = blist.indexOf(startName);
        if( startOrder == -1 )
            throw new Exception("起点站不存在");
        System.out.println("请输入终点站：");
        String endName = sc.nextLine();
        int endOrder = blist.indexOf(endName);
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

        FileWriter fileWriter = new FileWriter("/Users/zhaosheng/IdeaProjects/subway/src/station.txt");//创建文本文件
//        System.out.println("站点数："+set.size());
        fileWriter.write("站点数："+set.size()+"\r\n");//写入 \r\n换行

//        System.out.print(blist.get(clist.get(0))+' ');
        fileWriter.write(blist.get(clist.get(0))+' ');
        for(int i=1;i<clist.size(); i++) {
            if (!blist.get(clist.get(i)).equals(blist.get(clist.get(i - 1))))
//                System.out.print(blist.get(clist.get(i))+' ');
                fileWriter.write(blist.get(clist.get(i))+' ');
            else if(i != clist.size()-1) {
//                System.out.println();
//                System.out.println(list.get(clist.get(i)).getLineName());
                fileWriter.write("\r\n");
                fileWriter.write(list.get(clist.get(i)).getLineName()+"\r\n");
            }
        }
        fileWriter.flush();
        fileWriter.close();
    }
}
