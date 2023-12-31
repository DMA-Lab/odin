package ODIN.base.service.dto.result;

import ODIN.ODIN.service.graph.ODINClusterService;
import ODIN.base.domain.enumeration.UnitType;
import ODIN.base.service.dto.IndexDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.NumberFormat;

/**
 * AhgIndexResultDTO
 * 2022/4/23 zhoutao
 */
@Getter
@Setter
@ToString
public class IndexResultDTO extends ResultDTO {
    private static final String BUILD_TIME = "BuildTime";

    private static final String BUILD_MEMORY = "BuildMemory";

    private static final int SAVE_DECIMAL_PLACES = 2;

    public IndexResultDTO() {
        super();
    }

    public void buildResult(double buildTime, double buildMemory, IndexDTO indexDTO) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(SAVE_DECIMAL_PLACES);

        UnitType timeType = UnitType.valueOf(indexDTO.getTimeType());
        result.put(BUILD_TIME, nf.format(buildTime / (double) timeType.getQuantity()) + timeType.getName());
        if (indexDTO.isMemory()) {
            UnitType memoryType = UnitType.valueOf(indexDTO.getMemoryType());
            Object i = (buildMemory / (double) memoryType.getQuantity());
            result.put(BUILD_MEMORY, nf.format(ODINClusterService.Memory(buildMemory / (double) memoryType.getQuantity())) + memoryType.getName());
        }
    }


}
