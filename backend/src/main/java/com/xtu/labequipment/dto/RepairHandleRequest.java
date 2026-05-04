package com.xtu.labequipment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RepairHandleRequest {
    @NotNull(message = "报修ID不能为空")
    private Long repairId;

    @NotNull(message = "维修状态不能为空")
    private Integer repairStatus;

    private String repairResult;
}
