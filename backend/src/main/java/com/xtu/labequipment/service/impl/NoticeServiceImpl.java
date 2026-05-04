package com.xtu.labequipment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtu.labequipment.entity.Notice;
import com.xtu.labequipment.mapper.NoticeMapper;
import com.xtu.labequipment.service.NoticeService;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
}
