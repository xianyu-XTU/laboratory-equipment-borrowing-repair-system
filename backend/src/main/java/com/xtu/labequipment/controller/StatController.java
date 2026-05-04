package com.xtu.labequipment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xtu.labequipment.annotation.RequireRole;
import com.xtu.labequipment.common.Result;
import com.xtu.labequipment.entity.*;
import com.xtu.labequipment.service.*;
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
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("userCount", userService.count());
        map.put("deviceCount", deviceService.count());
        map.put("canBorrowDeviceCount", deviceService.count(new LambdaQueryWrapper<Device>().eq(Device::getStatus, 1)));
        map.put("borrowedDeviceCount", deviceService.count(new LambdaQueryWrapper<Device>().eq(Device::getStatus, 2)));
        map.put("repairingDeviceCount", deviceService.count(new LambdaQueryWrapper<Device>().eq(Device::getStatus, 3)));
        map.put("pendingApplyCount", borrowApplyService.count(new LambdaQueryWrapper<BorrowApply>().eq(BorrowApply::getStatus, 0)));
        map.put("borrowingRecordCount", borrowRecordService.count(new LambdaQueryWrapper<BorrowRecord>().eq(BorrowRecord::getStatus, 1)));
        map.put("pendingRepairCount", repairRecordService.count(new LambdaQueryWrapper<RepairRecord>().eq(RepairRecord::getRepairStatus, 0)));
        return Result.ok(map);
    }
}
