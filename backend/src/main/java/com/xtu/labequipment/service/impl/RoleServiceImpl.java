package com.xtu.labequipment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtu.labequipment.entity.Role;
import com.xtu.labequipment.mapper.RoleMapper;
import com.xtu.labequipment.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
