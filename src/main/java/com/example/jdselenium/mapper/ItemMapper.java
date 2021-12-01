package com.example.jdselenium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.jdselenium.pojo.Item;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author kee
 * @version 1.0
 * @date 2021/12/1 13:55
 */
@Mapper
public interface ItemMapper extends BaseMapper<Item> {
}
