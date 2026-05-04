package com.xtu.labequipment.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xtu.labequipment.common.BusinessException;
import com.xtu.labequipment.common.LoginUser;
import com.xtu.labequipment.dto.LoginRequest;
import com.xtu.labequipment.dto.LoginResponse;
import com.xtu.labequipment.dto.RegisterRequest;
import com.xtu.labequipment.entity.Role;
import com.xtu.labequipment.entity.User;
import com.xtu.labequipment.mapper.RoleMapper;
import com.xtu.labequipment.mapper.UserMapper;
import com.xtu.labequipment.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final TokenUtil tokenUtil;

    public LoginResponse login(LoginRequest request) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, request.getUsername()));
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new BusinessException("用户已被禁用");
        }
        if (!request.getPassword().equals(user.getPassword())) {
            throw new BusinessException("密码错误");
        }

        Role role = roleMapper.selectById(user.getRoleId());
        String roleCode = role == null ? "UNKNOWN" : role.getRoleCode();

        LoginUser loginUser = new LoginUser(user.getId(), user.getUsername(), roleCode);
        String token = tokenUtil.createToken(loginUser);
        return new LoginResponse(token, user.getId(), user.getUsername(), user.getRealName(), roleCode);
    }

    public void register(RegisterRequest request) {
        Long count = userMapper.selectCount(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, request.getUsername()));
        if (count != null && count > 0) {
            throw new BusinessException("用户名已存在");
        }

        Role studentRole = roleMapper.selectOne(new LambdaQueryWrapper<Role>()
                .eq(Role::getRoleCode, "STUDENT"));
        if (studentRole == null) {
            throw new BusinessException("学生角色不存在，请先初始化数据库");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRealName(request.getRealName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setRoleId(studentRole.getId());
        user.setStatus(1);
        userMapper.insert(user);
    }
}
