package com.example.jdselenium.component;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.selector.PlainText;

/**
 * @author kee
 * @version 1.0
 * @date 2021/11/30 20:09
 */
@Component
public class JdDownLoader implements Downloader {


    private String indexUrl = "https://www.jd.com/";
    private RemoteWebDriver driver;

    public JdDownLoader() {
        //加载chromedriver 必要条件
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        //添加chrome的配置信息
        ChromeOptions chromeOptions = new ChromeOptions();
        //设置为无头模式
        //chromeOptions.addArguments("--headless");
        //设置打开窗口大小
        chromeOptions.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(chromeOptions);
    }


    @Override
    public Page download(Request request, Task task) {
        if("list".equals(request.getExtra("level"))){
            try {
                driver.get(indexUrl);
                driver.findElementByCssSelector("#key").sendKeys("手机");
                Thread.sleep(1000);
                driver.findElementsByCssSelector(".button").get(0).click();
                Thread.sleep(1000);
                Integer start = 0;
                Integer end = 500;

                while (true) {
                    if (end == 6000) {
                        break;
                    }
                    String scriptStr = "window.scrollTo(" + start + "," + end + ")";
                    driver.executeScript(scriptStr);
                    Thread.sleep(500);
                    start += 500;
                    end += 500;
                }
                //拿到网页源码创建page对象
                String htmlStr = driver.getPageSource();
                return  createPage(htmlStr,driver.getCurrentUrl(),"list",request.getExtra("pageNum").toString());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //2分页下载
        if("page".equals(request.getExtra("level"))){
            try {
                driver.get(indexUrl);
                driver.findElementByCssSelector("#key").sendKeys("手机");
                Thread.sleep(1000);
                driver.findElementsByCssSelector(".button").get(0).click();
                Thread.sleep(1000);

                String pageNum = request.getExtra("pageNum").toString();
                for (int i = 1; i < Integer.valueOf(pageNum); i++) {
                    driver.findElementsByCssSelector(".fp-next").get(0).click();
                    Thread.sleep(1000);
                }
                Integer start = 0;
                Integer end = 500;

                while (true) {
                    if (end == 6000) {
                        break;
                    }
                    String scriptStr = "window.scrollTo(" + start + "," + end + ")";
                    driver.executeScript(scriptStr);
                    Thread.sleep(500);
                    start += 500;
                    end += 500;
                }
                //拿到网页源码创建page对象
                String htmlStr = driver.getPageSource();
                return  createPage(htmlStr,driver.getCurrentUrl(),"list",request.getExtra("pageNum").toString());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //3 详情页下载
        if("detail".equals(request.getExtra("level"))){
            driver.get(request.getUrl());
            String htmStr = driver.getPageSource();
            return createPage(htmStr,driver.getCurrentUrl(),"detail",request.getExtra("pageNum").toString());
        }
        return  null;
    }

    private Page createPage(String htmlStr, String currentUrl,String flag,String pageNum) {
        Page page = new Page();
        //设置网页源码
        page.setRawText(htmlStr);
        page.setUrl(new PlainText(currentUrl));
        page.isDownloadSuccess();
        //设置request对象
        Request request = new Request(currentUrl);
        request.putExtra("level",flag);
        request.putExtra("pageNum",pageNum);
        page.setRequest(request);
        return page;
    }

    @Override
    public void setThread(int i) {

    }
}
