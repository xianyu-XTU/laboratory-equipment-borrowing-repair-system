package com.xtu.labequipment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtu.labequipment.entity.DeviceCategory;
import com.xtu.labequipment.mapper.DeviceCategoryMapper;
import com.xtu.labequipment.service.DeviceCategoryService;
import org.springframework.stereotype.Service;

@Service
public class DeviceCategoryServiceImpl extends ServiceImpl<DeviceCategoryMapper, DeviceCategory> implements DeviceCategoryService {
}
