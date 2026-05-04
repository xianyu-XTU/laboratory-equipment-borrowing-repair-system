package com.xtu.labequipment.service.impl;

import com.xtu.labequipment.common.AuthContext;
import com.xtu.labequipment.common.BusinessException;
import com.xtu.labequipment.dto.RepairHandleRequest;
import com.xtu.labequipment.entity.Device;
import com.xtu.labequipment.entity.RepairRecord;
import com.xtu.labequipment.mapper.DeviceMapper;
import com.xtu.labequipment.mapper.RepairRecordMapper;
import com.xtu.labequipment.service.RepairBusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RepairBusinessServiceImpl implements RepairBusinessService {
    private final RepairRecordMapper repairRecordMapper;
    private final DeviceMapper deviceMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void report(RepairRecord repairRecord) {
        Device device = deviceMapper.selectById(repairRecord.getDeviceId());
        if (device == null) {
            throw new BusinessException("设备不存在");
        }
        repairRecord.setUserId(AuthContext.getUserId());
        repairRecord.setRepairStatus(0);
        repairRecord.setReportTime(LocalDateTime.now());
        repairRecordMapper.insert(repairRecord);

        device.setStatus(3);
        deviceMapper.updateById(device);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handle(RepairHandleRequest request) {
        RepairRecord record = repairRecordMapper.selectById(request.getRepairId());
        if (record == null) {
            throw new BusinessException("报修记录不存在");
        }
        if (request.getRepairStatus() < 0 || request.getRepairStatus() > 2) {
            throw new BusinessException("维修状态只能是0待处理、1维修中、2已完成");
        }

        record.setRepairStatus(request.getRepairStatus());
        record.setRepairResult(request.getRepairResult());
        if (request.getRepairStatus() == 2) {
            record.setFinishTime(LocalDateTime.now());
        }
        repairRecordMapper.updateById(record);

        if (request.getRepairStatus() == 2) {
            Device device = deviceMapper.selectById(record.getDeviceId());
            if (device != null) {
                device.setStatus(1);
                deviceMapper.updateById(device);
            }
        }
    }
}
