package com.xtu.labequipment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OverdueRecordVO {

    private Long recordId;
    private Long applyId;
    private Long deviceId;
    private Long userId;
    private LocalDateTime borrowTime;
    private LocalDateTime expectedReturnTime;
    private Long overdueHours;
    private Integer status;
}
