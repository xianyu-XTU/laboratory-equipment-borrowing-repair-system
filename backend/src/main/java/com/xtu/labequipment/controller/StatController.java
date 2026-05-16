package com.xtu.labequipment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xtu.labequipment.annotation.RequireRole;
import com.xtu.labequipment.common.Result;
import com.xtu.labequipment.entity.BorrowApply;
import com.xtu.labequipment.entity.BorrowRecord;
import com.xtu.labequipment.entity.Device;
import com.xtu.labequipment.entity.RepairRecord;
import com.xtu.labequipment.service.BorrowApplyService;
import com.xtu.labequipment.service.BorrowRecordService;
import com.xtu.labequipment.service.DeviceService;
import com.xtu.labequipment.service.RepairRecordService;
import com.xtu.labequipment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
@RequireRole({"LAB_ADMIN", "ADMIN"})
public class StatController {

    private final UserService userService;
    private final DeviceService deviceService;
    private final BorrowApplyService borrowApplyService;
    private final BorrowRecordService borrowRecordService;
    private final RepairRecordService repairRecordService;

    @GetMapping("/overview")
    public Result<Map<String, Object>> overview() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("userCount", userService.count());
        data.put("deviceCount", deviceService.count());
        data.put("canBorrowDeviceCount", countDeviceStatus(1));
        data.put("borrowedDeviceCount", countDeviceStatus(2));
        data.put("repairingDeviceCount", countDeviceStatus(3));
        data.put("pendingApplyCount", countApplyStatus(0));
        data.put("borrowingRecordCount", countRecordStatus(1));
        data.put("overdueRecordCount", countRecordStatus(3));
        data.put("pendingRepairCount", countRepairStatus(0));
        data.put("processingRepairCount", countRepairStatus(1));
        return Result.ok(data);
    }

    @GetMapping("/device-status")
    public Result<Map<String, Long>> deviceStatus() {
        Map<String, Long> data = new LinkedHashMap<>();
        data.put("canBorrow", countDeviceStatus(1));
        data.put("borrowed", countDeviceStatus(2));
        data.put("repairing", countDeviceStatus(3));
        data.put("scrapped", countDeviceStatus(4));
        return Result.ok(data);
    }

    @GetMapping("/borrow-status")
    public Result<Map<String, Long>> borrowStatus() {
        Map<String, Long> data = new LinkedHashMap<>();
        data.put("pendingApply", countApplyStatus(0));
        data.put("approvedApply", countApplyStatus(1));
        data.put("rejectedApply", countApplyStatus(2));
        data.put("borrowingRecord", countRecordStatus(1));
        data.put("returnedRecord", countRecordStatus(2));
        data.put("overdueRecord", countRecordStatus(3));
        return Result.ok(data);
    }

    @GetMapping("/repair-status")
    public Result<Map<String, Long>> repairStatus() {
        Map<String, Long> data = new LinkedHashMap<>();
        data.put("pending", countRepairStatus(0));
        data.put("processing", countRepairStatus(1));
        data.put("finished", countRepairStatus(2));
        return Result.ok(data);
    }

    private long countDeviceStatus(Integer status) {
        return deviceService.count(new LambdaQueryWrapper<Device>().eq(Device::getStatus, status));
    }

    private long countApplyStatus(Integer status) {
        return borrowApplyService.count(new LambdaQueryWrapper<BorrowApply>().eq(BorrowApply::getStatus, status));
    }

    private long countRecordStatus(Integer status) {
        return borrowRecordService.count(new LambdaQueryWrapper<BorrowRecord>().eq(BorrowRecord::getStatus, status));
    }

    private long countRepairStatus(Integer status) {
        return repairRecordService.count(new LambdaQueryWrapper<RepairRecord>().eq(RepairRecord::getRepairStatus, status));
    }
}
