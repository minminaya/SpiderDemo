import model.MeiTuModel;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import utils.SqlUtilForReBuild;
import utils.SqlUtilForSelectData;
import utils.SqlUtilForSpider;

import java.util.List;

/**
 * meitu爬虫测试
 * Created by Niwa on 2017/7/1.
 */
public class MeituRepoProcessor implements PageProcessor {


    public MeituRepoProcessor() {
    }

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    public void process(Page page) {
        //选取网页中css为id为maincontent的div，然后选取所有链接，再用正则表达式过滤掉
        List<String> urls = page.getHtml().css("#maincontent").links().regex("(http:\\/\\/www\\.meizitu\\.com\\/a\\/(\\d{4})\\.html)").all();
        //将待爬取的网址加到抓取队列池中
        page.addTargetRequests(urls);

        //这个是提取的主站www.meizitu.com的每个突变的title，用xpath表示，这个可以在调试里面下节点右键copy的到xpath地址，后面的这里的意思是id为maincontent的容器(这里是div)中的第一个盒子下的第一个盒子中的h2标签下的a标签的文字
        page.putField("webTitle", page.getHtml().xpath("//*[@id=\"maincontent\"]/div[1]/div[1]/h2/a/text()"));
        //先用xpath过滤到具体大的块（id为picture的容器中的p标签的img标签的src属性值），接着正则表达式提取出来（规律）
        page.putField("picName", page.getHtml().xpath("//*[@id=\"picture\"]/p/img[@src]").regex("(http:\\/\\/mm\\.howkuai\\.com\\/wp-content\\/uploads\\/(.*)\\.jpg)").all());

        if (page.getResultItems().get("picName") == null) {
            //如果当前获取到的图片网址为null，那么跳到网址池中下一个网址抓取
            page.setSkip(true);
        }

        //抓取底部的更多页面的地址
        List<String> nextUrls = page.getHtml().regex("(\\/a\\/more_(.*?)\\.html)").all();
        page.putField("nextUrls", nextUrls);
        //添加到池中
        page.addTargetRequests(nextUrls);

    }

    public Site getSite() {
        return site;
    }


    public static void main(String[] args) {

        //重新建表
        SqlUtilForReBuild.rebuild();
        Spider.create(new MeituRepoProcessor())
                .addUrl("http://www.meizitu.com/")
//                .addPipeline(new ConsolePipeline())
//                .addPipeline(new PileLineTest())
                .addPipeline(new PileLineTest11())
                //单线程保证存到数据库的数据不会乱序
                .thread(1)
                .run();


//        SqlUtilForSelectData sqlUtilForSelectData = new SqlUtilForSelectData();
//
//        List<MeiTuModel> meiTuModels = sqlUtilForSelectData.selectDataFormeiziweboneTable(1, 20);
//        for (int i = 0; i < meiTuModels.size(); i++) {
//            System.out.println("meiTuModels序号--" + i + "--title：" + meiTuModels.get(i).getWebTitle());
//
//            for (int j = 0; j < meiTuModels.get(i).getPicInfos().size(); j++) {
//                System.out.println("meiTuModels序号--" + i + "--pic中的图片地址为：" + meiTuModels.get(i).getPicInfos().get(j).getPicUrl());
//            }
//            System.out.println("==========================================================");
//        }


    }
}
