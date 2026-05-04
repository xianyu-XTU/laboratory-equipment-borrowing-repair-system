package com.xtu.labequipment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xtu.labequipment.annotation.RequireRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xtu.labequipment.common.Result;
import com.xtu.labequipment.dto.RepairHandleRequest;
import com.xtu.labequipment.entity.RepairRecord;
import com.xtu.labequipment.service.RepairBusinessService;
import com.xtu.labequipment.service.RepairRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/repairs")
@RequiredArgsConstructor
public class RepairController {
    private final RepairRecordService repairRecordService;
    private final RepairBusinessService repairBusinessService;

    @PostMapping("/report")
    public Result<Void> report(@RequestBody RepairRecord repairRecord) {
        repairBusinessService.report(repairRecord);
        return Result.ok();
    }

    @GetMapping
    public Result<Page<RepairRecord>> page(@RequestParam(defaultValue = "1") long page,
                                           @RequestParam(defaultValue = "10") long size,
                                           @RequestParam(required = false) Integer repairStatus,
                                           @RequestParam(required = false) Long deviceId) {
        LambdaQueryWrapper<RepairRecord> wrapper = new LambdaQueryWrapper<>();
        if (repairStatus != null) {
            wrapper.eq(RepairRecord::getRepairStatus, repairStatus);
        }
        if (deviceId != null) {
            wrapper.eq(RepairRecord::getDeviceId, deviceId);
        }
        wrapper.orderByDesc(RepairRecord::getReportTime);
        return Result.ok(repairRecordService.page(new Page<>(page, size), wrapper));
    }

    @RequireRole({"LAB_ADMIN", "ADMIN"})
    @PostMapping("/handle")
    public Result<Void> handle(@RequestBody @Valid RepairHandleRequest request) {
        repairBusinessService.handle(request);
        return Result.ok();
    }

    @RequireRole({"ADMIN"})
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        repairRecordService.removeById(id);
        return Result.ok();
    }
}
