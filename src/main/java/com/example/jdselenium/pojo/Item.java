package com.example.jdselenium.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author kee
 * @version 1.0
 * @date 2021/12/1 10:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private Long id;
    private Long sku;
    private Long spu;
    private Date created;
    private Date updated;
    private String title;
    private String pic;
    private Float price;
    private String url;
}
