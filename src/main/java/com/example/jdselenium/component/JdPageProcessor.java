package com.example.jdselenium.component;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.jdselenium.pojo.Item;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author kee
 * @version 1.0
 * @date 2021/12/1 10:22
 */
@Component
public class JdPageProcessor implements PageProcessor {
    @Override
    public void process(Page page) {
        String level = page.getRequest().getExtra("level").toString();
        switch (level) {
            case "list":
                parseList(page);
                break;
            case "detail":
                parseDetail(page);
                break;
        }
    }

    private void parseDetail(Page page) {
        Html html = page.getHtml();

        String title = html.css("div.itemInfo-wrap > div.sku-name","text").toString();
        String price = html.css("span.p-price > span","text").nodes().get(1).toString();
        String img = "https:" + html.$("#spec-img").xpath("///@src").get();
        String url = page.getUrl().toString();
        String sku = html.$("a.notice.J-notify-sale").xpath("///@data-sku").get();
        Item item = new Item();
        item.setTitle(title);
        item.setPrice(Float.valueOf(price));
        item.setUrl(url);
        item.setUpdated(new Date());
        item.setPic(img);
        item.setSku(StringUtils.isNotBlank(sku)?Long.valueOf(sku) : null);
        page.putField("item",item);

    }

    private void parseList(Page page) {
        Html html = page.getHtml();
        List<Selectable> nodes = html.css("ul.gl-warp.clearfix > li").nodes();
        List<Item> itemList = new ArrayList<>();
        for (Selectable node : nodes) {
            String sku = node.$("li").xpath("///@data-sku").get();
            String spu = node.$("li").xpath("///@data-spu").get();
            String href = "https:" + node.$("div.p-img a").xpath("///@href").get();
            Item item = new Item();
            item.setSku(Long.valueOf(sku));
            item.setSpu(StringUtils.isNotBlank(spu) ? Long.valueOf(spu) : null);
            item.setCreated(new Date());
            itemList.add(item);
            Request request = new Request(href);
            request.putExtra("level", "detail");
            request.putExtra("pageNum", page.getRequest().getExtra("pageNum"));
            request.putExtra("detailUrl", href);
            page.addTargetRequest(request);
        }
        page.putField("itemList", itemList);

        String pageNum = page.getRequest().getExtra("pageNum").toString();
        if ("1".equals(pageNum)){
            Request request = new Request("https://www.nextpage.com/"); //这里的网址只是做一个表示，默认会被自带的去重器去重
            request.putExtra("level", "page");
            request.putExtra("pageNum", (Integer.valueOf(pageNum) + 1) + "");
            page.addTargetRequest(request);
        }
    }

    @Override
    public Site getSite() {
        return Site.me();

    }
}
