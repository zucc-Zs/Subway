import jdk.nashorn.internal.ir.VarNode;

import java.util.ArrayList;
import java.util.List;

public class Dijkstra {
    private static int N = 99999;

    public static ArrayList<Integer> dijkstra(int vs, int[][] Graph, int vk) {

        int n = Graph[0].length;
        int[] prenode = new int[n];  //前驱节点
        int[] mindist = new int[n];  //最短距离
        boolean[] find = new boolean[n];  //是否访问
        int vnear = 0; //最近节点

        //初始化
        for(int i=0;i<mindist.length; i++){
            mindist[i] = Graph[vs][i];
            prenode[i] = vs;
            find[i] = false;
        }
        find[vs] = true;

        for (int v = 1; v < Graph.length; v++) {

            // 每次循环求得距离vs最近的节点vnear和最短距离min
            int min = N;
            for (int j = 0; j < Graph.length; j++) {
                if (!find[j] && mindist[j] < min) {
                    min = mindist[j];
                    vnear = j;
                }
            }
            find[vnear] = true;

            // 根据vnear修正vs到其他所有节点的前驱节点及距离
            for (int k = 0; k < Graph.length; k++) {
                if (!find[k] && (min + Graph[vnear][k]) < mindist[k]) {
                    prenode[k] = vnear;
                    mindist[k] = min + Graph[vnear][k];
                }
            }

        }

        ArrayList<Integer> list = new ArrayList<Integer>();
        int t = vk;
        while(t != vs) {
            list.add(t);
            t = prenode[t];
        }
        if(mindist[vk] != N)
            list.add(prenode[t]);

        return list;

    }
}

