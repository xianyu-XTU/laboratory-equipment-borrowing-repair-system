package com.xtu.labequipment.dto;

import lombok.Data;

@Data
public class DeviceAvailabilityVO {

    private Long deviceId;
    private Integer status;
    private String statusText;
    private Long pendingApplyCount;
    private Long activeBorrowCount;
    private Long activeRepairCount;
    private Boolean canBorrow;
    private String reason;
}
