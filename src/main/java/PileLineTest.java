import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import utils.DownLoadImage;

import java.io.IOException;
import java.util.List;

/**
 * Created by Niwa on 2017/6/30.
 */
public class PileLineTest implements Pipeline {
    public PileLineTest() {
        System.out.println("到大这里1");

    }

    int downLoadPosition = 1;

    public void process(ResultItems resultItems, Task task) {
        System.out.println("图片抓取页为：" + resultItems.getRequest().getUrl());

        List<String> urls = resultItems.get("picName");

        System.out.println("抓取图片序号为" + downLoadPosition + "开始进入下载");
        try {
            for (int i = 0; i < urls.size(); i++) {
                DownLoadImage.downLoad(urls.get(i), "pic" + i + ".jpg", "D:\\webmagic\\SpiderA\\pic" + downLoadPosition);
            }
            downLoadPosition++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("抓取图片序号为" + downLoadPosition + "下载结束");
    }
}
