package ODIN;

import ODIN.ODIN.service.dto.ODINkNNDTO;
import ODIN.base.controller.BaseController;
import ODIN.base.domain.GlobalVariable;
import ODIN.base.domain.api.Variable;
import ODIN.base.service.dto.IndexDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import java.util.ArrayList;


/**
 * spring starter
 **/
@Slf4j
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
public class ODINApplication implements CommandLineRunner {
//ODINApplication,IndexforknnApplication
    @Autowired
    BaseController controller;

    public static void main(String[] args) {
        SpringApplication.run(ODINApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
/**
 * Error queryName:142095
 */
        IndexDTO indexDTO = new IndexDTO();
        indexDTO.setIndexType("ODIN");
        indexDTO.setBranch(4); //这个更改构建的树是几叉树
        indexDTO.setSubGraphSize(300);
        indexDTO.setDistribution("RANDOM");
        indexDTO.setMapInfo("NY");
        indexDTO.setMapInfo1("NY");
        indexDTO.setCarNum(260000);
        indexDTO.setLeastActiveNum(40);
        indexDTO.setK(40);
        indexDTO.setTimeType("Second");
        indexDTO.setMemory(true);
        indexDTO.setMemoryType("GB");



       // System.out.println(controller.buildIndex_Dijkstra(indexDTO_Dijkstra).getResult());
        //System.out.println(controller.buildIndex_MPBS(indexDTO_MPBS).getResult());
       //System.out.println(GlobalVariable.NotLeaf);

        System.out.println(controller.buildIndex(indexDTO).getResult());

        //System.out.println(GlobalVariable.borderVertexNum.size());
        //borderVertexNum();




       // System.out.println("notLeaf_Num="+ GlobalVariable.NotLeaf);


        Variable variable = GlobalVariable.variable;

       // updateCars.update(indexDTO_MPBS.getCarNum(),0.25);

        //-------------------------------------------------------------
        //查询时节点折叠数量的统计
        //-------------------------------------------------------------

        ODINkNNDTO knnDTO=new ODINkNNDTO();
        knnDTO.setK(10);
        knnDTO.setQueryName(-2);
        knnDTO.setPrintKnn(false);
        knnDTO.setDijkstra(false);
        knnDTO.setQuerySize(10);
        System.out.println(controller.ahgKnn(knnDTO));

        //-------------------------------------------------------------
        //查询时节点折叠数量的统计
         //Variable variable = GlobalVariable.variable;


       // int changeNum = nodeNum2 - nodeNum1
        //-------------------------------------------------------------





    }
    public void borderVertexNum(){
        ArrayList<ArrayList<Integer>> boderNum = new ArrayList<>();
        int max = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < GlobalVariable.borderVertexNum.size(); i++) {
            if (GlobalVariable.borderVertexNum.get(i) > max){
                max = GlobalVariable.borderVertexNum.get(i);
            }
            if (GlobalVariable.borderVertexNum.get(i) < min){
                min = GlobalVariable.borderVertexNum.get(i);
            }
        }
        //int sq1 = max/sq;
        int num1 = 0;
        for (int i = 0; i < 10; i++) {
            boderNum.add(new ArrayList<>());
            boderNum.get(boderNum.size()-1).add(10*i);
            boderNum.get(boderNum.size()-1).add(10*(i+1));
            num1 = 0;
            for (int j = 0; j < GlobalVariable.borderVertexNum.size(); j++) {
               if (GlobalVariable.borderVertexNum.get(j)>=(10*i) && GlobalVariable.borderVertexNum.get(j)<(10*(i+1))){
                   num1++;
               }
            }
            boderNum.get(boderNum.size()-1).add(num1);
        }
        int num = boderNum.get(0).get(2)+1;
        boderNum.get(0).remove(2);
        boderNum.get(0).add(num);

        for (int i = 0; i < boderNum.size(); i++) {
            System.out.println("boderNum:"+boderNum.get(i).get(0)+"-"+boderNum.get(i).get(1)+":"+boderNum.get(i).get(2));
        }

    }
}
