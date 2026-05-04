package com.xtu.labequipment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xtu.labequipment.annotation.RequireRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xtu.labequipment.common.AuthContext;
import com.xtu.labequipment.common.Result;
import com.xtu.labequipment.entity.Notice;
import com.xtu.labequipment.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping
    public Result<Page<Notice>> page(@RequestParam(defaultValue = "1") long page,
                                     @RequestParam(defaultValue = "10") long size,
                                     @RequestParam(required = false) Integer status,
                                     @RequestParam(required = false) String keyword) {
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(Notice::getStatus, status);
        }
        if (keyword != null && !keyword.isBlank()) {
            wrapper.like(Notice::getTitle, keyword);
        }
        wrapper.orderByDesc(Notice::getPublishTime);
        return Result.ok(noticeService.page(new Page<>(page, size), wrapper));
    }

    @RequireRole({"LAB_ADMIN", "ADMIN"})
    @PostMapping
    public Result<Void> add(@RequestBody Notice notice) {
        notice.setPublishUserId(AuthContext.getUserId());
        notice.setPublishTime(LocalDateTime.now());
        if (notice.getStatus() == null) {
            notice.setStatus(1);
        }
        noticeService.save(notice);
        return Result.ok();
    }

    @RequireRole({"LAB_ADMIN", "ADMIN"})
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody Notice notice) {
        notice.setId(id);
        noticeService.updateById(notice);
        return Result.ok();
    }

    @RequireRole({"ADMIN"})
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        noticeService.removeById(id);
        return Result.ok();
    }
}
