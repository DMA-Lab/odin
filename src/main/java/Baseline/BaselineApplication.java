package Baseline;

import Baseline.base.controller.BaseController;
import Baseline.base.domain.GlobalVariable;
import Baseline.base.service.dto.IndexDTO;
import Baseline.base.service.dto.KnnDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;


/**
 * spring starter
 **/
@Slf4j
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
public class BaselineApplication implements CommandLineRunner {

    @Autowired
    BaseController controller;

    public static void main(String[] args) {
        SpringApplication.run(BaselineApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
/**
 * Error queryName:142095
 */
        IndexDTO indexDTO = new IndexDTO();
        indexDTO.setIndexType("ERkNN");
        indexDTO.setBranch(4);
        indexDTO.setSubGraphSize(300);
        indexDTO.setDistribution("VTREE");
        indexDTO.setMapInfo("NY");
        indexDTO.setCarNum(26000);
        indexDTO.setLeastActiveNum(5);
        indexDTO.setK(30);
        indexDTO.setTimeType("Second");
        indexDTO.setMemory(true);
        indexDTO.setMemoryType("GB");
        System.out.println(controller.buildIndex(indexDTO).getResult());
        System.out.println("notLeaf_Num="+ GlobalVariable.NotLeaf+",Node="+GlobalVariable.Node);
        KnnDTO knnDTO =new KnnDTO();
        knnDTO.setK(50);
        knnDTO.setQueryName(1);
        knnDTO.setPrintKnn(false);
        knnDTO.setDijkstra(false);
        knnDTO.setQuerySize(10);
        System.out.println(controller.knn(knnDTO).getResult());





    }
}
