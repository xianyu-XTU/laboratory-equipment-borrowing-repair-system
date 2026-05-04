package com.xtu.labequipment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xtu.labequipment.annotation.RequireRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xtu.labequipment.common.Result;
import com.xtu.labequipment.dto.ApproveBorrowRequest;
import com.xtu.labequipment.dto.ReturnDeviceRequest;
import com.xtu.labequipment.entity.BorrowApply;
import com.xtu.labequipment.entity.BorrowRecord;
import com.xtu.labequipment.service.BorrowApplyService;
import com.xtu.labequipment.service.BorrowBusinessService;
import com.xtu.labequipment.service.BorrowRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
@RequiredArgsConstructor
public class BorrowController {
    private final BorrowApplyService borrowApplyService;
    private final BorrowRecordService borrowRecordService;
    private final BorrowBusinessService borrowBusinessService;

    @PostMapping("/apply")
    public Result<Void> apply(@RequestBody BorrowApply apply) {
        borrowBusinessService.apply(apply);
        return Result.ok();
    }

    @GetMapping("/applies")
    public Result<Page<BorrowApply>> applyPage(@RequestParam(defaultValue = "1") long page,
                                               @RequestParam(defaultValue = "10") long size,
                                               @RequestParam(required = false) Integer status,
                                               @RequestParam(required = false) Long userId) {
        LambdaQueryWrapper<BorrowApply> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(BorrowApply::getStatus, status);
        }
        if (userId != null) {
            wrapper.eq(BorrowApply::getUserId, userId);
        }
        wrapper.orderByDesc(BorrowApply::getApplyTime);
        return Result.ok(borrowApplyService.page(new Page<>(page, size), wrapper));
    }

    @RequireRole({"LAB_ADMIN", "ADMIN"})
    @PostMapping("/approve")
    public Result<Void> approve(@RequestBody @Valid ApproveBorrowRequest request) {
        borrowBusinessService.approve(request);
        return Result.ok();
    }

    @GetMapping("/records")
    public Result<Page<BorrowRecord>> recordPage(@RequestParam(defaultValue = "1") long page,
                                                 @RequestParam(defaultValue = "10") long size,
                                                 @RequestParam(required = false) Integer status,
                                                 @RequestParam(required = false) Long userId) {
        LambdaQueryWrapper<BorrowRecord> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(BorrowRecord::getStatus, status);
        }
        if (userId != null) {
            wrapper.eq(BorrowRecord::getUserId, userId);
        }
        wrapper.orderByDesc(BorrowRecord::getBorrowTime);
        return Result.ok(borrowRecordService.page(new Page<>(page, size), wrapper));
    }

    @RequireRole({"LAB_ADMIN", "ADMIN"})
    @PostMapping("/return")
    public Result<Void> returnDevice(@RequestBody @Valid ReturnDeviceRequest request) {
        borrowBusinessService.returnDevice(request);
        return Result.ok();
    }
}
