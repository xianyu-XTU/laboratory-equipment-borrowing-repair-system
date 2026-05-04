package com.xtu.labequipment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReturnDeviceRequest {
    @NotNull(message = "借还记录ID不能为空")
    private Long recordId;

    private String remark;
}
