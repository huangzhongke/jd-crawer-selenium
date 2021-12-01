package com.example.jdselenium.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

/**
 * @author kee
 * @version 1.0
 * @date 2021/12/1 14:10
 */
@Component
public class JdSpider {
    @Autowired
    JdDownLoader jdDownLoader;

    @Autowired
    JdPageProcessor jdPageProcessor;
    @Autowired
    JdPipeLine jdPipeLine;

    private String indexUrl="https://www.jd.com/";
    @Scheduled(fixedRate = 3600*24)
    public void doCrawer(){

        Request request = new Request(indexUrl);
        request.putExtra("level","list");
        request.putExtra("pageNum","1");
        Spider.create(jdPageProcessor)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .addPipeline(jdPipeLine)
                .setDownloader(jdDownLoader)
                .thread(1)
                .addRequest(request)
                .start();
    }
}
