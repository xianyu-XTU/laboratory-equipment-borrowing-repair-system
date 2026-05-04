package com.xtu.labequipment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xtu.labequipment.annotation.RequireRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xtu.labequipment.common.Result;
import com.xtu.labequipment.entity.User;
import com.xtu.labequipment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@RequireRole({"ADMIN"})
public class UserController {
    private final UserService userService;

    @GetMapping
    public Result<Page<User>> page(@RequestParam(defaultValue = "1") long page,
                                   @RequestParam(defaultValue = "10") long size,
                                   @RequestParam(required = false) String keyword) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isBlank()) {
            wrapper.like(User::getUsername, keyword)
                    .or()
                    .like(User::getRealName, keyword);
        }
        wrapper.orderByDesc(User::getCreateTime);
        return Result.ok(userService.page(new Page<>(page, size), wrapper));
    }

    @GetMapping("/{id}")
    public Result<User> get(@PathVariable Long id) {
        return Result.ok(userService.getById(id));
    }

    @PostMapping
    public Result<Void> add(@RequestBody User user) {
        userService.save(user);
        return Result.ok();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        userService.updateById(user);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        userService.removeById(id);
        return Result.ok();
    }
}
