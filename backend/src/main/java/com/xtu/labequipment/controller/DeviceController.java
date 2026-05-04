package com.xtu.labequipment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xtu.labequipment.annotation.RequireRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xtu.labequipment.common.Result;
import com.xtu.labequipment.entity.Device;
import com.xtu.labequipment.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;

    @GetMapping
    public Result<Page<Device>> page(@RequestParam(defaultValue = "1") long page,
                                     @RequestParam(defaultValue = "10") long size,
                                     @RequestParam(required = false) String keyword,
                                     @RequestParam(required = false) Long categoryId,
                                     @RequestParam(required = false) Integer status) {
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isBlank()) {
            wrapper.and(w -> w.like(Device::getDeviceName, keyword)
                    .or()
                    .like(Device::getDeviceNo, keyword)
                    .or()
                    .like(Device::getLocation, keyword));
        }
        if (categoryId != null) {
            wrapper.eq(Device::getCategoryId, categoryId);
        }
        if (status != null) {
            wrapper.eq(Device::getStatus, status);
        }
        wrapper.orderByDesc(Device::getCreateTime);
        return Result.ok(deviceService.page(new Page<>(page, size), wrapper));
    }

    @GetMapping("/{id}")
    public Result<Device> get(@PathVariable Long id) {
        return Result.ok(deviceService.getById(id));
    }

    @RequireRole({"LAB_ADMIN", "ADMIN"})
    @PostMapping
    public Result<Void> add(@RequestBody Device device) {
        if (device.getStatus() == null) {
            device.setStatus(1);
        }
        deviceService.save(device);
        return Result.ok();
    }

    @RequireRole({"LAB_ADMIN", "ADMIN"})
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody Device device) {
        device.setId(id);
        deviceService.updateById(device);
        return Result.ok();
    }

    @RequireRole({"LAB_ADMIN", "ADMIN"})
    @PutMapping("/{id}/status/{status}")
    public Result<Void> updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        Device device = new Device();
        device.setId(id);
        device.setStatus(status);
        deviceService.updateById(device);
        return Result.ok();
    }

    @RequireRole({"ADMIN"})
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        deviceService.removeById(id);
        return Result.ok();
    }
}
