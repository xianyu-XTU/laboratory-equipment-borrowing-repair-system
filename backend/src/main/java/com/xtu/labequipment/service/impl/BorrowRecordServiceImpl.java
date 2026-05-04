package com.xtu.labequipment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtu.labequipment.entity.BorrowRecord;
import com.xtu.labequipment.mapper.BorrowRecordMapper;
import com.xtu.labequipment.service.BorrowRecordService;
import org.springframework.stereotype.Service;

@Service
public class BorrowRecordServiceImpl extends ServiceImpl<BorrowRecordMapper, BorrowRecord> implements BorrowRecordService {
}
