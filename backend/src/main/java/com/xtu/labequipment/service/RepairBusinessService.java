package com.xtu.labequipment.service;

import com.xtu.labequipment.dto.RepairHandleRequest;
import com.xtu.labequipment.entity.RepairRecord;

public interface RepairBusinessService {
    void report(RepairRecord repairRecord);
    void handle(RepairHandleRequest request);
}
