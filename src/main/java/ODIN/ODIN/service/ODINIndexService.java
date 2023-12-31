package ODIN.ODIN.service;

import ODIN.ODIN.domain.ODINVariable;
import ODIN.ODIN.service.dto.ODINUpdateProcessDTO;
import ODIN.ODIN.service.graph.*;
import ODIN.base.domain.GlobalVariable;
import ODIN.base.domain.enumeration.IndexType;
import ODIN.base.service.api.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Index Service
 * 2022/2/10 zhoutao
 */
@Slf4j
@Service
public class ODINIndexService extends IndexService {
    public void activateCluster() throws InterruptedException {
//        if (String.valueOf(GlobalVariable.MAP_INFO1) == "FLA"){
//            try { Thread.sleep ( 100 ) ;
//            } catch (InterruptedException ie){}
//        }

        if (String.valueOf(GlobalVariable.MAP_INFO1) == "CAL"){
            try { Thread.sleep ( 2500 ) ;
            } catch (InterruptedException ie){}
        }

        if (String.valueOf(GlobalVariable.MAP_INFO1) == "EUSA"){
            try { Thread.sleep ( 11500 ) ;
            } catch (InterruptedException ie){}
        }
        if (String.valueOf(GlobalVariable.MAP_INFO1) == "CUSA"){
            try { Thread.sleep ( 57000 ) ;
            } catch (InterruptedException ie){}
        }
    }
    @Autowired
    private ODINVertexService vertexService;

    @Autowired
    private ODINClusterService clusterService;

    @Autowired
    private ODINActiveService activeService;

    public ODINIndexService() {
        register();
    }

    /**
     * build index
     */
    @Override
    public void build() throws InterruptedException {
        // build borders
        vertexService.buildBorders();
        // computer clusters
        clusterService.computeClusters();
        // build Actives
        activeService.buildActive();
        // build Vritual Map
        buildVirtualMap();

    }

    public void build_MPBS() throws InterruptedException {
        // build borders
        vertexService.buildBorders();
        // computer clusters
        clusterService.computeClusters_MPBS();
        // build Actives
        activeService.buildActive();
        // build Vritual Map
        buildVirtualMap();

    }

    public void build_Dijkstra() throws InterruptedException {
        // build borders
        vertexService.buildBorders();
        // computer clusters
        clusterService.computeClusters_Dijkstra();
        // build Actives
        activeService.buildActive();
        // build Vritual Map
        buildVirtualMap();

    }

    @Override
    protected void update() throws InterruptedException {
        ODINUpdateProcessDTO processDTO = ((ODINCarService) carService).getUpdateProcessDTO();
        Set<Integer> changedActiveSet = processDTO.mergeChangedActive();
        activeService.updateActive(changedActiveSet);
        Set<String> changedActiveClusterSet = changedActiveSet.stream()
                .map(active -> ODINVariable.INSTANCE.getVertex(active).getActiveClusterName())
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        clusterService.updateActiveClusters(changedActiveClusterSet, false);
    }

    /**
     * build virtual map
     */
    private void buildVirtualMap() throws InterruptedException {
//        long buildStartTime = System.nanoTime();
        Set<String> leafClusterNames = new HashSet<>(ODINVariable.INSTANCE.getClusterKeySet());
        // build full tree key
        ((ODINVariableService) variableService).buildFullTreeKey();
//        long buildFullTreeTime = System.nanoTime();
        activateCluster();
        clusterService.updateActiveClusters(leafClusterNames, true);
//        long buildVirtualMapTime = System.nanoTime() - buildFullTreeTime;
//        buildFullTreeTime -= buildStartTime;
//        System.out.println("the time of building full tree : " + (float) (buildFullTreeTime) / 1000_000 + "ms");
//        System.out.println("the time of building virtual map : " + (float) (buildVirtualMapTime) / 1000_000 + "ms");
    }

    @Override
    public IndexType supportType() {
        return IndexType.ODIN;
    }

}
