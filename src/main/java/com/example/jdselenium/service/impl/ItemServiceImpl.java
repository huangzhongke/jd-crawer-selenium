package com.example.jdselenium.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.jdselenium.mapper.ItemMapper;
import com.example.jdselenium.pojo.Item;
import com.example.jdselenium.service.ItemService;
import org.springframework.stereotype.Service;

/**
 * @author kee
 * @version 1.0
 * @date 2021/12/1 13:59
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {
}
