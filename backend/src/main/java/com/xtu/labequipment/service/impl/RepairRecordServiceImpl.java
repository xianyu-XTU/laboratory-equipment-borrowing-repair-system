package com.xtu.labequipment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtu.labequipment.entity.RepairRecord;
import com.xtu.labequipment.mapper.RepairRecordMapper;
import com.xtu.labequipment.service.RepairRecordService;
import org.springframework.stereotype.Service;

@Service
public class RepairRecordServiceImpl extends ServiceImpl<RepairRecordMapper, RepairRecord> implements RepairRecordService {
}
