package com.xtu.labequipment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xtu.labequipment.annotation.RequireRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xtu.labequipment.common.Result;
import com.xtu.labequipment.entity.DeviceCategory;
import com.xtu.labequipment.service.DeviceCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class DeviceCategoryController {
    private final DeviceCategoryService categoryService;

    @GetMapping
    public Result<Page<DeviceCategory>> page(@RequestParam(defaultValue = "1") long page,
                                             @RequestParam(defaultValue = "10") long size,
                                             @RequestParam(required = false) String keyword) {
        LambdaQueryWrapper<DeviceCategory> wrapper = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isBlank()) {
            wrapper.like(DeviceCategory::getCategoryName, keyword);
        }
        wrapper.orderByDesc(DeviceCategory::getCreateTime);
        return Result.ok(categoryService.page(new Page<>(page, size), wrapper));
    }

    @GetMapping("/all")
    public Result<Object> all() {
        return Result.ok(categoryService.list());
    }

    @RequireRole({"LAB_ADMIN", "ADMIN"})
    @PostMapping
    public Result<Void> add(@RequestBody DeviceCategory category) {
        categoryService.save(category);
        return Result.ok();
    }

    @RequireRole({"LAB_ADMIN", "ADMIN"})
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody DeviceCategory category) {
        category.setId(id);
        categoryService.updateById(category);
        return Result.ok();
    }

    @RequireRole({"LAB_ADMIN", "ADMIN"})
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        categoryService.removeById(id);
        return Result.ok();
    }
}
