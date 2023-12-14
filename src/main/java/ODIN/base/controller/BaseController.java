package ODIN.base.controller;

import ODIN.ODIN.service.ODINService;
import ODIN.ODIN.service.dto.ODINkNNDTO;
import ODIN.ODIN.service.dto.result.ODINkNNEdge;
import ODIN.base.domain.GlobalVariable;
import ODIN.base.service.GlobalVariableService;
import ODIN.base.service.api.IKnnService;
import ODIN.base.service.api.IndexService;
import ODIN.base.service.dto.IndexDTO;
import ODIN.base.service.dto.KnnDTO;
import ODIN.base.service.dto.UpdateDTO;
import ODIN.base.service.dto.result.ResultDTO;
import ODIN.base.service.factory.ServiceFactory;
import ODIN.base.service.utils.DistributionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BaseController
 * 2022/3/20 zhoutao
 */
@RestController
@RequestMapping()
@Slf4j
public class BaseController {
    private IndexService indexService;

    @Autowired
    private GlobalVariableService globalVariableService;

    private IKnnService knnService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "API is connected";
    }

    /**
     * buildIndex
     *
     * @param index Index related parameters
     */
    @RequestMapping(value = "/build", method = RequestMethod.POST)
    public ResultDTO buildIndex(@RequestBody IndexDTO index) throws InterruptedException {
        initGlobalVariable(index);
        indexService.buildIndex(index);
        return indexService.buildResult(index);
    }

    public ResultDTO buildIndex_MPBS(@RequestBody IndexDTO index) throws InterruptedException {
        initGlobalVariable(index);
        indexService.buildIndex(index);
        return indexService.buildResult(index);
    }

    public ResultDTO buildIndex_Dijkstra(@RequestBody IndexDTO index) throws InterruptedException {
        initGlobalVariable(index);
        indexService.buildIndex_Dijkstra(index);
        return indexService.buildResult(index);
    }

    @RequestMapping(value = "/ahg/knn", method = RequestMethod.POST)
    public @ResponseBody
    List<ODINkNNEdge> ahgKnn(@RequestBody ODINkNNDTO knnDTO) {
        globalVariableService.initKnnVariable(knnDTO);
        knnService = ServiceFactory.getAhgKnnService();
        ((ODINService) knnService).initAhgKnn(knnDTO);
        ((ODINService) knnService).batchAhgKnnSerach();
        ((ODINService) knnService).updateKnnPosition();

        //ODINService i = ((ODINService) knnService).ODINkNNSearch() ;


        return ((ODINService) knnService).getQueryEdges();
    }

    public void dateSet(IndexDTO index){
        index.setMapInfo("NY");
    }

    @RequestMapping(value = "/knn", method = RequestMethod.POST)
    public @ResponseBody
    ResultDTO knn(@RequestBody KnnDTO knnDTO) {
        globalVariableService.initKnnVariable(knnDTO);
        knnService = ServiceFactory.getKnnService();
        if (knnDTO.getQueryName() > -1 && knnDTO.getQueryName() < GlobalVariable.VERTEX_NUM) {
            knnService.knnSearch(knnDTO.getQueryName());
        } else {
            knnService.knnSearch(DistributionUtil.getVertexName());
        }
        return knnService.buildResult(knnDTO);
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(@RequestBody UpdateDTO updateDTO) throws InterruptedException {
        indexService.updateCar(updateDTO);
    }

    /**
     * initGlobalVariable
     */
    private void initGlobalVariable(IndexDTO index) throws InterruptedException {
        dateSet(index);
        globalVariableService.initGlobalVariable(index);
        indexService = ServiceFactory.getIndexService();



    }




}
