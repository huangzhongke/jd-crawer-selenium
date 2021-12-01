package com.example.jdselenium.component;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.jdselenium.mapper.ItemMapper;
import com.example.jdselenium.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * @author kee
 * @version 1.0
 * @date 2021/12/1 13:54
 */
@Component
public class JdPipeLine  implements Pipeline {

    @Autowired
    ItemMapper itemMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<Item> itemList = resultItems.get("itemList");
        if (itemList != null){
            for (Item item : itemList) {
//                itemService.save(item);
                itemMapper.insert(item);
            }
        }else {
            Item item = resultItems.get("item");
//            UpdateWrapper<Item> iuw = new UpdateWrapper<>();
//            LambdaUpdateWrapper<Item> lambda = iuw.lambda();
//            lambda.eq(Item::getSku,item.getSku());
            itemMapper.update(item, Wrappers.<Item>lambdaUpdate().eq(Item::getSku,item.getSku()));
        }
    }
}
