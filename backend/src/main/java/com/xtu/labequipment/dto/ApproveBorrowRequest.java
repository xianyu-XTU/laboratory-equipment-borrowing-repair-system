package com.xtu.labequipment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApproveBorrowRequest {
    @NotNull(message = "申请ID不能为空")
    private Long applyId;

    @NotNull(message = "审批状态不能为空")
    private Integer status;

    private String approveRemark;
}
