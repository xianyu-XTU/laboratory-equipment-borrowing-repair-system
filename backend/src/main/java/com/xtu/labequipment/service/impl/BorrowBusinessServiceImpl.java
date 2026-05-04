package com.xtu.labequipment.service.impl;

import com.xtu.labequipment.common.AuthContext;
import com.xtu.labequipment.common.BusinessException;
import com.xtu.labequipment.dto.ApproveBorrowRequest;
import com.xtu.labequipment.dto.ReturnDeviceRequest;
import com.xtu.labequipment.entity.BorrowApply;
import com.xtu.labequipment.entity.BorrowRecord;
import com.xtu.labequipment.entity.Device;
import com.xtu.labequipment.mapper.BorrowApplyMapper;
import com.xtu.labequipment.mapper.BorrowRecordMapper;
import com.xtu.labequipment.mapper.DeviceMapper;
import com.xtu.labequipment.service.BorrowBusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BorrowBusinessServiceImpl implements BorrowBusinessService {
    private final BorrowApplyMapper borrowApplyMapper;
    private final BorrowRecordMapper borrowRecordMapper;
    private final DeviceMapper deviceMapper;

    @Override
    public void apply(BorrowApply apply) {
        Device device = deviceMapper.selectById(apply.getDeviceId());
        if (device == null) {
            throw new BusinessException("设备不存在");
        }
        if (device.getStatus() == null || device.getStatus() != 1) {
            throw new BusinessException("设备当前不可借");
        }
        apply.setUserId(AuthContext.getUserId());
        apply.setApplyTime(LocalDateTime.now());
        apply.setStatus(0);
        borrowApplyMapper.insert(apply);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(ApproveBorrowRequest request) {
        BorrowApply apply = borrowApplyMapper.selectById(request.getApplyId());
        if (apply == null) {
            throw new BusinessException("借用申请不存在");
        }
        if (apply.getStatus() != null && apply.getStatus() != 0) {
            throw new BusinessException("该申请已经审批过");
        }
        if (request.getStatus() != 1 && request.getStatus() != 2) {
            throw new BusinessException("审批状态只能是1通过或2拒绝");
        }

        apply.setStatus(request.getStatus());
        apply.setApproveUserId(AuthContext.getUserId());
        apply.setApproveTime(LocalDateTime.now());
        apply.setApproveRemark(request.getApproveRemark());
        borrowApplyMapper.updateById(apply);

        if (request.getStatus() == 1) {
            Device device = deviceMapper.selectById(apply.getDeviceId());
            if (device == null) {
                throw new BusinessException("设备不存在");
            }
            if (device.getStatus() == null || device.getStatus() != 1) {
                throw new BusinessException("设备当前不可借，无法审批通过");
            }

            BorrowRecord record = new BorrowRecord();
            record.setApplyId(apply.getId());
            record.setDeviceId(apply.getDeviceId());
            record.setUserId(apply.getUserId());
            record.setBorrowTime(LocalDateTime.now());
            record.setStatus(1);
            record.setRemark("审批通过后自动生成借还记录");
            borrowRecordMapper.insert(record);

            device.setStatus(2);
            deviceMapper.updateById(device);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnDevice(ReturnDeviceRequest request) {
        BorrowRecord record = borrowRecordMapper.selectById(request.getRecordId());
        if (record == null) {
            throw new BusinessException("借还记录不存在");
        }
        if (record.getStatus() == null || record.getStatus() != 1) {
            throw new BusinessException("该设备不是借用中状态");
        }

        record.setReturnTime(LocalDateTime.now());
        record.setStatus(2);
        record.setRemark(request.getRemark());
        borrowRecordMapper.updateById(record);

        Device device = deviceMapper.selectById(record.getDeviceId());
        if (device != null) {
            device.setStatus(1);
            deviceMapper.updateById(device);
        }
    }
}
