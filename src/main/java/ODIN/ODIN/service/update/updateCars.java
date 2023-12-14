package ODIN.ODIN.service.update;

import ODIN.ODIN.domain.ODINCluster;
import ODIN.base.domain.GlobalVariable;
import ODIN.base.domain.api.Variable;

import java.util.ArrayList;
import java.util.Set;

public class updateCars {
    public static ArrayList<Node> Node = new ArrayList<>();
    public static int foldingNum = 0;
    public static int unfoldingNum = 0;
    public static ArrayList<Integer> trueNode = new ArrayList<>();
    public static ArrayList<Integer> trueNode1 = new ArrayList<>();
    public static String qz = new String();
    public static long  startTime;
    public static long  endTime;

    public static void update(int CarNum,double Proportion) throws InterruptedException {
        //long startTime; //= System.currentTimeMillis();
        //long endTime;
        int carNum = 26000;
        //double num14 = 0.1;
        double num14 = 0.1;
        double proportion = Proportion*0.01;
        initialization();
        initializationTree();
        for (int i = 0; i < Node.size(); i++) {
            if (Node.get(i).activation == true){
                qz = Node.get(1).name.substring(0,5);
            }
        }


        for (int i = 5; i > 0 ; i--) {
            //int carNum1 = (int) (carNum1 * (0.25));
//            carNum = (int) (carNum *0.25);
//            num14 = num14 * 0.25;
            for (int j = 0; j < 9; j++) {
                //startTime =  System.nanoTime();
                changeCarNum(proportion,carNum);
               // System.out.println("改变carNum");
               // initializationTree();
               // endTime =  System.nanoTime();
               // System.out.println("改变Tree");
                System.out.println("比例为"+num14+":1的第"+j+"轮：foldingNum="+foldingNum+"，unfoldingNum="+unfoldingNum+"消耗时间="+((endTime - startTime))+"ns");
            }
            System.out.println();
            for (int j = 0; j < trueNode1.size(); j++) {
                //int num = (int) (Node.get(trueNode1.get(j)).carNum - (Node.get(j).fristCarNum *0.2));
                int num = (int) (Node.get(trueNode1.get(j)).carNum * 0.5);
                //int num = (int) (Node.get(trueNode1.get(j)).carNum * 4 );
                Node.get(trueNode1.get(j)).carNum = num;
            }
            //System.out.println("修改car数量完成");
            //carNum = (int) (carNum - CarNum*0.2);
            carNum = (int) (carNum *4);
            num14 = num14 *0.5;
           // num14 = num14 *4;

        }


//        for (int i = 0; i < 4; i++) {
//            changeCarNum(proportion,carNum);
//            initializationTree();
//            System.out.println("foldingNum="+foldingNum+"，unfoldingNum="+unfoldingNum+",NodeNum="+trueNode.size());
//            for (int j = 0; j < trueNode1.size(); j++) {
//                int num = (int) (Node.get(trueNode1.get(j)).carNum * 0.1);
//                Node.get(trueNode1.get(j)).carNum = num;
//            }
//            carNum =(int)( carNum *0.1);
//        }

        

        //Variable variable = GlobalVariable.variable;
        //int num1 = 0;
    }




    public static void initialization(){
        Variable variable = GlobalVariable.variable;
       // Set<String> keySet1 = GlobalVariable.variable.getClusterKeySet();
        Set<String> keySet = variable.getClusterKeySet();
        //ArrayList<Object> map = new ArrayList<>(keySet);
        int num1 = 0;
        int num2 = 0;
        //Object[] map =  GlobalVariable.variable.getClusters().toArray();
        for (Object ii: keySet) {

            Node.add(new Node());
            Node.get(Node.size()-1).name = ii.toString();
            num2 = 0;
            for (Object jj: variable.getClusters()) {
              if (num2 == num1){
                  if (jj != null){
                      Node.get(Node.size()-1).activation = true;
                      //Node.get(Node.size()-1).parentName = variable.getParentClusterName( Node.get(Node.size()-1).name);
                      Node.get(Node.size()-1).carNum = variable.getCluster(Node.get(Node.size()-1).name).getClusterDis1((ODINCluster) variable.getCluster(Node.get(Node.size()-1).name));
                  }
                  //Node.get(Node.size()-1).parentName = variable.getParentClusterName( Node.get(Node.size()-1).name);
                  Node.get(Node.size()-1).layer =  Node.get(Node.size()-1).name.length()/2+1;
                  //Node.get(Node.size()-1).parentName =  Node.get(Node.size()-1).name.substring(0,Node.get(Node.size()-1).name.length()-1);
                  break;
              }else {
                  num2++;
                  continue;
              }
            }
            num1++;

        }
        String children;
        String children1;
        //String children2;
        for (int i = 0; i < Node.size(); i++) {
            children = variable.getParentClusterName( Node.get(i).name);
            for (int j = 0; j <  Node.size(); j++) {
                if (Node.get(j).name.equals(children)){
                    Node.get(i).parentName = j;
                    break;
                }
            }
        }
        for (int i = 0; i < Node.size(); i++) {
            if (Node.get(i).name.length() < 12){
                children = Node.get(i).name;
                children1 = children+","+"0";
                for (int j = 0; j < Node.size(); j++) {
                    //children2 = Node.get(j).name;
                    if (Node.get(j).name.equals(children1) ){
                        Node.get(i).children.add(j);
                        break;
                    }
                }
                children1 = children+","+"1";
                for (int j = 0; j < Node.size(); j++) {
                    if (Node.get(j).name.equals(children1)){
                        Node.get(i).children.add(j);
                        break;
                    }
                }
                children1 = children+","+"2";
                for (int j = 0; j < Node.size(); j++) {
                    if (Node.get(j).name.equals(children1)){
                        Node.get(i).children.add(j);
                        break;
                    }
                }
                children1 = children+","+"3";
                for (int j = 0; j < Node.size(); j++) {
                    if (Node.get(j).name.equals(children1)){
                        Node.get(i).children.add(j);
                        break;
                    }
                }
            }
        }
    }

    public static void changeCarNum(double proportion,int CarNum) throws InterruptedException {
        int CarNum1 = (int) ( CarNum*proportion);
        int CarNum2 = (int) ( CarNum*proportion);
        trueNode.clear();
        for (int i = 0; i < Node.size(); i++) {
            if (Node.get(i).activation == true){
                trueNode.add(i);
            }
        }
        int randomCarNum = 0;
        int randomNodeNum = 0;
        //System.out.println("CarNum="+CarNum1);
        while (CarNum1 > 0){
            //System.out.println("111");
            if (CarNum1 > 10){
                randomNodeNum = GetNum(0,(trueNode.size()-1));
                randomCarNum = GetNum(1,10);
                if (Node.get(trueNode.get(randomNodeNum)).carNum < randomCarNum){
                    randomCarNum = Node.get(trueNode.get(randomNodeNum)).carNum;
                }
                Node.get(trueNode.get(randomNodeNum)).carNum -= randomCarNum;
                CarNum1 -= randomCarNum;
            }
            if (CarNum1 <= 10){
                randomNodeNum = GetNum(0,(trueNode.size()-1));
                randomCarNum = CarNum1;
                Node.get(trueNode.get(randomNodeNum)).carNum += randomCarNum;
                CarNum1 = 0;
            }

    }
       // System.out.println("减少完成");
        while (CarNum2 > 0){
           // System.out.println(CarNum2);
            if (CarNum2 > 10){
                randomNodeNum = GetNum(0,(trueNode.size()-1));
                randomCarNum = GetNum(1,10);
                Node.get(trueNode.get(randomNodeNum)).carNum += randomCarNum;
                CarNum2 -= randomCarNum;
            }
            if (CarNum2 <= 10){
                randomNodeNum = GetNum(0,(trueNode.size()-1));
                randomCarNum = CarNum2;
                Node.get(trueNode.get(randomNodeNum)).carNum += randomCarNum;
                CarNum2 = 0;
            }
        }
        //System.out.println("增加完成");


        changeTree(trueNode);




    }

    public static void changeTree(ArrayList<Integer> trueNode) throws InterruptedException {
        startTime = System.nanoTime();
        //fold
        int parentName = 0;
        foldingNum = 0;
        unfoldingNum = 0;
        int foldingNum1 = 25;
        for (int i = 0; i < Node.size(); i++) {
            if (Node.get(i).activation == true){
                if (Node.get(i).carNum > foldingNum1){//unfolding
                    if (Node.get(i).layer < 7){
                        Node.get(i).activation = false;
                        for (int j = 0; j < Node.get(i).children.size(); j++) {
                            int num = Node.get(i).carNum/4;
                            Node.get(Node.get(i).children.get(j)).carNum = num;
                            Node.get(Node.get(i).children.get(j)).activation = true;
                        }
                       // unfoldingNum++;
                            if (Node.get(parentName).name.length() >5){
                               String qz1 =  Node.get(parentName).name.substring(0,5);
                               if (qz1.equals(qz)){
                                   Thread.sleep(1);
                                  unfoldingNum++;
                               }
                          }else {
                               String qz1 = Node.get(parentName).name;
                              String qz2 = qz.substring(0,qz1.length()-1);
                               if (qz1.equals(qz2)){
                                   Thread.sleep(1);
                                   unfoldingNum++;
                               }
                           }


//                        needUnfoldingNum.clear();
//                        needUnfoldingNum.add(i);
//                        while (needUnfoldingNum.size() != 0){
//                            Node.get(needUnfoldingNum.get(0)).activation = false;
//                           if (Node.get(needUnfoldingNum.get(0)).children.size() != 0){
//                               for (int j = 0; j < Node.get(needUnfoldingNum.get(0)).children.size(); j++) {
//                                   int num = Node.get(needUnfoldingNum.get(0)).carNum/4;
//                                   if (num > 20 && Node.get(Node.get(needUnfoldingNum.get(0)).children.get(j)).layer < 7){
//                                       needUnfoldingNum.add(Node.get(needUnfoldingNum.get(0)).children.get(j));
//                                   }
//                                   Node.get(Node.get(needUnfoldingNum.get(0)).children.get(j)).carNum = num;
//                                   Node.get(Node.get(needUnfoldingNum.get(0)).children.get(j)).activation = true;
//                               }
//                           }
//                            unfoldingNum++;
//
////                            if (Node.get(parentName).name.length() >5){
////                                String qz1 =  Node.get(parentName).name.substring(0,5);
////                                if (qz1.equals(qz)){
////                                    unfoldingNum++;
////                                }
////                            }else {
////                                String qz1 = Node.get(parentName).name;
////                                String qz2 = qz.substring(0,qz1.length()-1);
////                                if (qz1.equals(qz2)){
////                                    unfoldingNum++;
////                                }
////                            }
//                            //unfoldingNum++;
//                        }

                    }
                }


                if (Node.get(i).carNum < foldingNum1){ //folding
                    int carNum = 0;
                    parentName = Node.get(i).parentName;
                    for (int j = 0; j < Node.get(parentName).children.size(); j++) {
                        carNum += Node.get(Node.get(parentName).children.get(j)).carNum;
                        // Node.get(Node.get(parentName).children.get(j)).activation = false;
                    }
                    if (carNum < foldingNum1){
                        Node.get(parentName).activation = true;
                        for (int j = 0; j < Node.get(parentName).children.size(); j++) {
                            //carNum += Node.get(Node.get(parentName).children.get(j)).carNum;
                            Node.get(Node.get(parentName).children.get(j)).activation = false;
                        }
                        Node.get(parentName).carNum = carNum;
//                        if (Node.get(parentName).name.length() >5){
//                                String qz1 =  Node.get(parentName).name.substring(0,5);
//                                if (qz1.equals(qz)){
//                                    foldingNum++;
//                                }
//                            }else {
//                                String qz1 = Node.get(parentName).name;
//                                String qz2 = qz.substring(0,qz1.length());
//                                if (qz1.equals(qz2)){
//                                    foldingNum++;
//                                }
//                            }
                        //foldingNum++;
                        if (Node.get(parentName).name.length() >5){
                            String qz1 =  Node.get(parentName).name.substring(0,5);
                            if (qz1.equals(qz)){
                                Thread.sleep(1);
                                foldingNum++;
                            }
                        }else {
                            String qz1 = Node.get(parentName).name;
                            String qz2 = qz.substring(0,qz1.length()-1);
                            if (qz1.equals(qz2)){
                                Thread.sleep(1);
                                foldingNum++;
                            }
                        }

                    }

//                    while (needfoldingNum.size() != 0){
//                        parentName = Node.get(needfoldingNum.get(0)).parentName;
//                        for (int j = 0; j < Node.get(parentName).children.size(); j++) {
//                            carNum += Node.get(Node.get(parentName).children.get(j)).carNum;
//                           // Node.get(Node.get(parentName).children.get(j)).activation = false;
//                        }
//
//                        if (carNum < foldingNum1){
//                            Node.get(parentName).activation = true;
//                            for (int j = 0; j < Node.get(parentName).children.size(); j++) {
//                                //carNum += Node.get(Node.get(parentName).children.get(j)).carNum;
//                                Node.get(Node.get(parentName).children.get(j)).activation = false;
//                            }
//                            Node.get(parentName).carNum = carNum;
//
//                            if (Node.get(parentName).carNum < foldingNum1){
//                                needfoldingNum.add(parentName);
//                            }
//
////                            if (Node.get(parentName).name.length() >5){
////                                String qz1 =  Node.get(parentName).name.substring(0,5);
////                                if (qz1.equals(qz)){
////                                    foldingNum++;
////                                }
////                            }else {
////                                String qz1 = Node.get(parentName).name;
////                                String qz2 = qz.substring(0,qz1.length());
////                                if (qz1.equals(qz2)){
////                                    foldingNum++;
////                                }
////                            }
//                            foldingNum++;
//                        }
//
//                        needfoldingNum.remove(0);
//                       // foldingNum++;
//                    }

                }

            }
        }

        trueNode1.clear();
        for (int i = 0; i < Node.size(); i++) {
            if (Node.get(i).activation == true){
                trueNode1.add(i);
            }
        }
        endTime = System.nanoTime();
        //System.out.println("时间=" + (endTime -startTime));
    }


    public static void initializationTree(){
        for (int i = 6; i > 0; i--) {
            for (int j = 0; j < Node.size(); j++) {
                if (Node.get(j).layer == i){
                    if (Node.get(j).activation == true){
                        for (int k = 0; k < Node.get(j).children.size(); k++) {
                            Node.get(Node.get(j).children.get(k)).activation = false;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < Node.size(); i++) {
            if (Node.get(i).fristCarNum == 0 && Node.get(i).carNum != 0){
                Node.get(i).fristCarNum = Node.get(i).carNum;
            }
        }
    }


    public static class Node{
        public String name;
        public int carNum;
        public boolean activation;
        public int parentName;
        public ArrayList<Integer> children;
        public int layer;
        public int fristCarNum;

        public Node(){
            this.name = new String();
            this.carNum = 0;
            this.activation = false;
            this.parentName = 0;
            this.children = new ArrayList<>();
            this.layer = 0;
            this.fristCarNum = 0;
        }
    }

    public static int GetNum(int num1,int num2){
        int result = (int) (num1 + Math.random() * (num2 - num1 + 1));
        return result;
    }
}
