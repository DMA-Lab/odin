package ODIN.base.service.utils;


import ODIN.base.domain.api.Cluster;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * computer pair dis
 * 2022/5/12 zhoutao
 */
public class DisComputerUtil {

    /**
     * floyd
     *
     * @param cluster cluster
     */
    public static void floyd(Cluster cluster) {
//         String name = cluster.getName();
//         if (name.getBytes())
        Set<Integer> clusterVertices = cluster.getClusterLinkMap().keySet();
        for (Integer middle : clusterVertices) {
            for (Integer from : clusterVertices) {

                if (from.equals(middle)) continue;
                int disFromMiddle = cluster.getClusterDis(middle, from);
                if (disFromMiddle == -1) continue;

                for (Integer to : clusterVertices) {
                    if (to <= from) continue;

                    int disMiddleTo = cluster.getClusterDis(middle, to);
                    if (disMiddleTo == -1) {
                        continue;
                    }

                    int dis = cluster.getClusterDis(from, to);
                    if (dis == -1 || dis > (disFromMiddle + disMiddleTo)) {
                        cluster.addClusterLink(from, to, disFromMiddle + disMiddleTo);
                    }
                }
            }
        }
    }



    public static ArrayList<ArrayList<v_1>> isDis = new ArrayList<>();
    public static ArrayList<ArrayList<Integer>> boundDis = new ArrayList<>();
    public static void MPBS(Cluster cluster)  {
        Set<Integer> clusterVertices = cluster.getClusterLinkMap().keySet();
        ArrayList<ArrayList<v_1>> edge = new ArrayList<>();
        ExecutorService executorService = Executors.newCachedThreadPool();
        ArrayList<ArrayList<v_1>> isDis1 = new ArrayList<>();
        ArrayList<ArrayList<Integer>> boundDis1 = new ArrayList<>();

        int num10 = 0;
        for (Integer i:clusterVertices) {
            edge.add(new ArrayList<>());

            num10++;
            isDis1.add(new ArrayList<>());
            boundDis1.add(new ArrayList<>());
            boundDis1.get(boundDis1.size()-1).add(10000000);
            for (Integer j:clusterVertices) {
                isDis1.get(isDis1.size()-1).add(new v_1(i,j));
                int dis = cluster.getClusterDis(i, j);
                if (dis != -1){
                    edge.get(num10-1).add(new v_1());
                    edge.get(num10-1).get(edge.get(num10-1).size()-1).vs = i;
                    edge.get(num10-1).get(edge.get(num10-1).size()-1).vt = j;
                    edge.get(num10-1).get(edge.get(num10-1).size()-1).dis = dis;

                }
            }
        }

        for (int i = 0; i < isDis1.size(); i++) {
            for (int j = i; j < isDis1.get(i).size(); j++) {

                DM_MPBS(cluster,clusterVertices,  isDis1.get(i).get(j).vs,  isDis1.get(i).get(j).vt,  edge);
                DM_MPBS(cluster,clusterVertices,  isDis1.get(i).get(j).vt,  isDis1.get(i).get(j).vs,  edge);
            }
        }

    }






    public static void Dijkstra(Cluster cluster){
        Set<Integer> clusterVertices = cluster.getClusterLinkMap().keySet();
        ArrayList<ArrayList<v_1>> edge = new ArrayList<>();
        int num10 = 0;
        for (Integer i:clusterVertices) {
            edge.add(new ArrayList<>());
            //mapping_v[i] = num10;
            num10++;
            for (Integer j:clusterVertices) {
                int dis = cluster.getClusterDis(i, j);
                if (dis != -1){
                    edge.get(num10-1).add(new v_1());
                    edge.get(num10-1).get(edge.get(num10-1).size()-1).vs = i;
                    edge.get(num10-1).get(edge.get(num10-1).size()-1).vt = j;
                    edge.get(num10-1).get(edge.get(num10-1).size()-1).dis = dis;
                    //edge.get(num10-1).add(dis);
                }
            }
        }
        for (Integer from : clusterVertices){
            for(Integer to : clusterVertices){
                Dijkstra_dm(cluster,clusterVertices,from,to,edge);
            }
        }
    }



    public static void Dijkstra_dm(Cluster cluster, Set<Integer> clusterVertices, int startIndex, int endIndex,
                                ArrayList<ArrayList<v_1>> edge) {
        ArrayList<ArrayList<Integer>> set = new ArrayList<>();
        for (Integer i:clusterVertices) {
            set.add(new ArrayList<>());
            set.get(set.size()-1).add(i);
            set.get(set.size()-1).add(0);
        }
        //int[] set = new int[num3]; // 是否已并入集合，该点是否已找到最短路径
        // s到i的最短路径长度
        ArrayList<ArrayList<Integer>> dist = new ArrayList<>();
        for (Integer i:clusterVertices) {
            dist.add(new ArrayList<>());
            dist.get(dist.size()-1).add(i);
        }

        //int[] dist = new int[num3];
        List<Integer> dist1 = new ArrayList<>();
        List<Integer> dist2 = new ArrayList<>();//已处理点集合


        int numStart = 0;
        int numEnd = 0;
        for (int i = 0; i < edge.size()-1; i++) {
            if (edge.get(i).get(0).vs == startIndex) numStart = i;
            if (edge.get(i).get(0).vs == endIndex) numEnd = i;
        }
//            int[] v = new int[num3]; //顶点与数组对应
//
//            int num5 = 0;
//            for (Integer from : clusterVertices) {
//                v[from] = num5;
//                num5++;
//            }

        int n = 0;
        int m = 0;


//            EdegeNode a = new EdegeNode();
//            EdegeNode aNext = new EdegeNode();
        // 初始化数组
        for (int i = 0; i < set.size(); i++) {
            if (set.get(i).get(0) == startIndex){
                set.get(i).remove(1);
                set.get(i).add(1);
            }
        }
        //set[startIndex] = 1;
        for (Integer from : clusterVertices){
            for (Integer to : clusterVertices) {
                if (from.equals(to)) {
                    for (int i = 0; i < dist.size(); i++) {
                        if (dist.get(i).get(0) == to){
                            dist.get(i).add(0);
                            break;
                        }
                    }
                    //dist[to] = 0;
                    continue;
                }
                int disFromMiddle = cluster.getClusterDis(to, from);

                for (int i = 0; i < dist.size(); i++) {
                    if (dist.get(i).get(0) == to){
                        if (disFromMiddle == -1){
                            dist.get(i).add(Integer.MAX_VALUE);
                        }else {
                            dist.get(i).add(disFromMiddle);
                            dist1.add(to);
                        }
                        break;

                    }
                }
                //dist[to] = Integer.MAX_VALUE;
                continue;
//                dist[to] = disFromMiddle;
//                dist1.add(to);
            }
        }


        int flag = 0;
        boolean flag1 = false;
        // 需进行n-2轮循环
        int k1 = startIndex;
        int num7 = -1;
        while (k1 != endIndex && dist1.size() != 0) {
            int k = -1;
            int min = Integer.MAX_VALUE;
            // EdegeNode g1 = g.point[k1].firstArc;
            // 找出dist[]中最小的（贪心）
            for (Integer j : dist1) {
                for (int i = 0; i < dist.size(); i++) {
                    if (dist.get(i).get(0) == j){
                        if ( dist.get(i).get(1) < min){
                            min = dist.get(i).get(1);
                            k = j;
                            break;
                        }
                    }
                }

            }
            if (k == -1) {
                // 说明从源点出发与其余节点不连通，无法再向下进行扩展
                break;
            }
            dist1.remove(new Integer(k));
            dist2.add(k);
            int numFrom = 0;
            int numTo = 0;
            for (Integer from : clusterVertices) {
                if (from == k){
                    for (Integer to : clusterVertices ) {
                        if (to <= from) continue;
                        int dis = cluster.getClusterDis(from, to);
                        if (dis == -1){
                            continue;
                        }else {
                            for (int i = 0; i < dist.size(); i++) {
                                if (dist.get(i).get(0) == from) numFrom = i;
                                if (dist.get(i).get(0) == to) numTo = i;
                            }


                            if (dist.get(numFrom).get(1)+dis < dist.get(numTo).get(1) ){
                                dist.get(numTo).remove(1);
                                dist.get(numTo).add(dist.get(numFrom).get(1)+dis);

                            }
                        }
                    }
                }

            }
            k1 = k;
        }
        if (flag == 1) {
            //cdl.countDown();
        } else {
            if (num7 != -1){
                isDis.get(numEnd).get(num7).flag = true;
            }


            dist1.clear();


        }



        //cdl.countDown();




    }


    public static void DM_MPBS(Cluster cluster, Set<Integer> clusterVertices, int startIndex, int endIndex,
                               ArrayList<ArrayList<v_1>> edge) {
        ArrayList<ArrayList<Integer>> set = new ArrayList<>();
        for (Integer i:clusterVertices) {
            set.add(new ArrayList<>());
            set.get(set.size()-1).add(i);
            set.get(set.size()-1).add(0);
        }
        //int[] set = new int[num3]; // 是否已并入集合，该点是否已找到最短路径
        // s到i的最短路径长度
        ArrayList<ArrayList<Integer>> dist = new ArrayList<>();
        for (Integer i:clusterVertices) {
            dist.add(new ArrayList<>());
            dist.get(dist.size()-1).add(i);
        }

        //int[] dist = new int[num3];
        List<Integer> dist1 = new ArrayList<>();
        List<Integer> dist2 = new ArrayList<>();//已处理点集合


        int numStart = 0;
        int numEnd = 0;
        for (int i = 0; i < edge.size()-1; i++) {
            if (edge.get(i).get(0).vs == startIndex) numStart = i;
            if (edge.get(i).get(0).vs == endIndex) numEnd = i;
        }
//            int[] v = new int[num3]; //顶点与数组对应
//
//            int num5 = 0;
//            for (Integer from : clusterVertices) {
//                v[from] = num5;
//                num5++;
//            }

        int n = 0;
        int m = 0;


//            EdegeNode a = new EdegeNode();
//            EdegeNode aNext = new EdegeNode();
        // 初始化数组
        for (int i = 0; i < set.size(); i++) {
            if (set.get(i).get(0) == startIndex){
                set.get(i).remove(1);
                set.get(i).add(1);
            }
        }
        //set[startIndex] = 1;
        for (Integer from : clusterVertices){
            for (Integer to : clusterVertices) {
                if (from.equals(to)) {
                    for (int i = 0; i < dist.size(); i++) {
                        if (dist.get(i).get(0) == to){
                            dist.get(i).add(0);
                            break;
                        }
                    }
                    //dist[to] = 0;
                    continue;
                }
                int disFromMiddle = cluster.getClusterDis(to, from);

                for (int i = 0; i < dist.size(); i++) {
                    if (dist.get(i).get(0) == to){
                        if (disFromMiddle == -1){
                            dist.get(i).add(Integer.MAX_VALUE);
                        }else {
                            dist.get(i).add(disFromMiddle);
                            dist1.add(to);
                        }
                        break;

                    }
                }
                //dist[to] = Integer.MAX_VALUE;
                continue;
//                dist[to] = disFromMiddle;
//                dist1.add(to);
            }
        }


        int flag = 0;
        boolean flag1 = false;
        // 需进行n-2轮循环
        int k1 = startIndex;
        int num7 = -1;
        while (k1 != endIndex && dist1.size() != 0) {
            int k = -1;
            int min = Integer.MAX_VALUE;
            // EdegeNode g1 = g.point[k1].firstArc;
            // 找出dist[]中最小的（贪心）
            for (Integer j : dist1) {
                for (int i = 0; i < dist.size(); i++) {
                    if (dist.get(i).get(0) == j){
                        if ( dist.get(i).get(1) < min){
                            min = dist.get(i).get(1);
                            k = j;
                            break;
                        }
                    }
                }

            }
            if (k == -1) {
                // 说明从源点出发与其余节点不连通，无法再向下进行扩展
                break;
            }


//------------------------------------------------------------------------------------------
            for (int i = 0; i < set.size(); i++) {
                if (set.get(i).get(0) == k){
                    set.get(i).remove(1);
                    set.get(i).add(1);
                    break;
                }
            }
            //set[k] = 1; // 把节点k并入
            dist1.remove(new Integer(k));
            dist2.add(k);
            int numK = 0;

            for (int i = 0; i < edge.size(); i++) {

                if (edge.get(i).get(0).vs == k) {
                    numK = i;
                    break;
                }
            }


            //维护绑定距离
            int flag2 = 0;
            int num11 = 0;
            for (int j = 0; j < edge.get(numK).size(); j++) {
                for (int l = 0; l < dist2.size(); l++) {
                    if (dist2.get(l) == edge.get(numK).get(j).vt){
                        num11++;
                    }
                }
            }
            if (num11 < edge.get(numK).size() ){ //k是绑定顶点
                if (dist.get(numK).get(1) < boundDis.get(numStart).get(0)){
                    boundDis.get(numStart).remove(0);
                    boundDis.get(numStart).add(0,dist.get(numK).get(1));
                }
            }
            int distS = 0;

            for (int j = 0; j < isDis.get(numStart).size(); j++) {
                if (isDis.get(numStart).get(j).vt == k){
                    isDis.get(numStart).get(j).flag = true;
                    isDis.get(numStart).get(j).dis = dist.get(numK).get(1);
                    distS = dist.get(numK).get(1);
                }
            }

            for (int j = 0; j < isDis.get(numEnd).size(); j++) { //访问终点对应的的Sp元素
                if (isDis.get(numEnd).get(j).vt == k) {
                    num7 = j;
                    if (isDis.get(numEnd).get(j).flag == true) {
                        if ((distS+isDis.get(numEnd).get(j).dis) <=
                                (boundDis.get(numStart).get(0)+boundDis.get(numEnd).get(0))){
                            cluster.addClusterLink(startIndex, endIndex, distS+isDis.get(numEnd).get(j).dis);
                            flag1 = true;
                            break;
                        }

                    } else {
                        break;
                    }
                }
            }
            if (flag1 == true) {
                flag = 1;
                break;
            }
//------------------------------------------------------------------------------------------
            int numFrom = 0;
            int numTo = 0;
            for (Integer from : clusterVertices) {
                if (from == k){
                    for (Integer to : clusterVertices ) {
                        if (to <= from) continue;
                        int dis = cluster.getClusterDis(from, to);
                        if (dis == -1){
                            continue;
                        }else {
                            for (int i = 0; i < dist.size(); i++) {
                                if (dist.get(i).get(0) == from) numFrom = i;
                                if (dist.get(i).get(0) == to) numTo = i;
                            }


                            if (dist.get(numFrom).get(1)+dis < dist.get(numTo).get(1) ){
                                dist.get(numTo).remove(1);
                                dist.get(numTo).add(dist.get(numFrom).get(1)+dis);

                            }
                        }
                    }
                }

            }
            k1 = k;
        }
        if (flag == 1) {
            //cdl.countDown();
        } else {
            if (num7 != -1){
                isDis.get(numEnd).get(num7).flag = true;
            }


            dist1.clear();


        }



        //cdl.countDown();




    }




    public static class v_1 {
        public int vs;
        public int vt;
        public boolean flag;
        public int dis;

        public v_1() {

            this.vs = 0;
            this.vt = 0;
            this.flag = false;

        }

        public v_1(int vs, int vt) {

            this.vs = vs;
            this.vt = vt;
            this.flag = false;
            this.dis = 0;
        }
    }


}


