package com.xtu.labequipment.service;

import com.xtu.labequipment.dto.ApproveBorrowRequest;
import com.xtu.labequipment.dto.ReturnDeviceRequest;
import com.xtu.labequipment.entity.BorrowApply;

public interface BorrowBusinessService {
    void apply(BorrowApply apply);
    void approve(ApproveBorrowRequest request);
    void returnDevice(ReturnDeviceRequest request);
}
