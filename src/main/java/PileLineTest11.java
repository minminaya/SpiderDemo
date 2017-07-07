import model.MeiTuModel;
import model.PicInfo;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import utils.SqlUtilForSpider;

import java.util.ArrayList;
import java.util.List;

/**
 * ·
 * Created by Niwa on 2017/6/30.
 */
public class PileLineTest11 implements Pipeline {
    public PileLineTest11() {
        System.out.println("PileLineTest11初始化");

    }

    private int downLoadPosition = 1;

    /**
     * 表picInfos中的id
     */
    private int picIndex = 1;
    /**
     * 表meiziwebone中的id
     */
    private int webIndex = 1;

    /**
     * 表meiziwebone中的picStartId
     * 某网页图片地址在picInfos中起始位置
     */
    private int picStartIndex = 1;

    public void process(ResultItems resultItems, Task task) {
        //抓取时这里第一个值是空值所以不要第一个值
        if (downLoadPosition != 1) {

//        System.out.println("图片抓取页为：" + resultItems.getRequest().getUrl());

            List<String> urls = resultItems.get("picName");


            int urlsSize = urls.size();
            for (String url : urls) {
                //图片地址数据存入数据库中表picinfos
                SqlUtilForSpider.insertDataForpicinfosTable(picIndex++, url);
            }

            //图片地址数据存入数据库中表meiziwebone
            if (!(resultItems.get("webTitle").toString().equals(null))) {
                SqlUtilForSpider.insertDataFormeiziweboneTable(webIndex++, resultItems.get("webTitle").toString(), picStartIndex, urlsSize);
            }
            picStartIndex += urlsSize;

            System.out.println("抓取图片序号为" + downLoadPosition + "开始");

            //下载图片的for循环，当前存入数据库，不保存在本地，故注释掉了
//            try {
//                for (int i = 0; i < urls.size(); i++) {
////                DownLoadImage.downLoad(urls.get(i), "pic" + i + ".jpg", "D:\\webmagic\\Spider3\\pic" + downLoadPosition);
//                    DownLoadImage.downLoad(urls.get(i), "pic" + i + ".jpg", "D:\\webmagic\\Spider4\\" + resultItems.get("webTitle"));
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            try {
                //每次抓取一个网页后休眠随机毫秒，模拟用户操作
                long random = new Double(Math.random() * 1000).longValue();
                Thread.sleep(random);
                System.out.println("随机时间：" + random + "毫秒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        downLoadPosition++;
    }
}
