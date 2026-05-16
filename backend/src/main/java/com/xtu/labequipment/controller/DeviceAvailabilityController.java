package com.xtu.labequipment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xtu.labequipment.common.BusinessException;
import com.xtu.labequipment.common.Result;
import com.xtu.labequipment.dto.DeviceAvailabilityVO;
import com.xtu.labequipment.entity.BorrowApply;
import com.xtu.labequipment.entity.BorrowRecord;
import com.xtu.labequipment.entity.Device;
import com.xtu.labequipment.entity.RepairRecord;
import com.xtu.labequipment.service.BorrowApplyService;
import com.xtu.labequipment.service.BorrowRecordService;
import com.xtu.labequipment.service.DeviceService;
import com.xtu.labequipment.service.RepairRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceAvailabilityController {

    private final DeviceService deviceService;
    private final BorrowApplyService borrowApplyService;
    private final BorrowRecordService borrowRecordService;
    private final RepairRecordService repairRecordService;

    @GetMapping("/{id}/availability")
    public Result<DeviceAvailabilityVO> availability(@PathVariable Long id) {
        Device device = deviceService.getById(id);
        if (device == null) {
            throw new BusinessException("设备不存在");
        }

        long pendingApplyCount = borrowApplyService.count(new LambdaQueryWrapper<BorrowApply>()
                .eq(BorrowApply::getDeviceId, id)
                .eq(BorrowApply::getStatus, 0));
        long activeBorrowCount = borrowRecordService.count(new LambdaQueryWrapper<BorrowRecord>()
                .eq(BorrowRecord::getDeviceId, id)
                .in(BorrowRecord::getStatus, 1, 3));
        long activeRepairCount = repairRecordService.count(new LambdaQueryWrapper<RepairRecord>()
                .eq(RepairRecord::getDeviceId, id)
                .in(RepairRecord::getRepairStatus, 0, 1));

        DeviceAvailabilityVO vo = new DeviceAvailabilityVO();
        vo.setDeviceId(id);
        vo.setStatus(device.getStatus());
        vo.setStatusText(statusText(device.getStatus()));
        vo.setPendingApplyCount(pendingApplyCount);
        vo.setActiveBorrowCount(activeBorrowCount);
        vo.setActiveRepairCount(activeRepairCount);

        boolean canBorrow = Objects.equals(device.getStatus(), 1)
                && pendingApplyCount == 0
                && activeBorrowCount == 0
                && activeRepairCount == 0;
        vo.setCanBorrow(canBorrow);
        vo.setReason(reason(device.getStatus(), pendingApplyCount, activeBorrowCount, activeRepairCount));
        return Result.ok(vo);
    }

    private String statusText(Integer status) {
        if (Objects.equals(status, 1)) {
            return "可借";
        }
        if (Objects.equals(status, 2)) {
            return "已借出";
        }
        if (Objects.equals(status, 3)) {
            return "维修中";
        }
        if (Objects.equals(status, 4)) {
            return "报废";
        }
        return "未知";
    }

    private String reason(Integer status, long pendingApplyCount, long activeBorrowCount, long activeRepairCount) {
        if (!Objects.equals(status, 1)) {
            return "设备状态为" + statusText(status) + "，暂不可借";
        }
        if (pendingApplyCount > 0) {
            return "存在待审批借用申请";
        }
        if (activeBorrowCount > 0) {
            return "存在未归还借用记录";
        }
        if (activeRepairCount > 0) {
            return "存在未完成维修记录";
        }
        return "设备当前可借";
    }
}
