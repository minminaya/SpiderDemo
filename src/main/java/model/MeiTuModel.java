package model;

import java.util.List;

/**
 * Created by Niwa on 2017/7/2.
 */
public class MeiTuModel {

    private List<PicInfo> picInfos;
    private String webTitle;

    public MeiTuModel() {

    }

    public MeiTuModel(List<PicInfo> picInfos, String webTitle) {
        this.picInfos = picInfos;
        this.webTitle = webTitle;
    }

    public List<PicInfo> getPicInfos() {
        return picInfos;
    }

    public void setPicInfos(List<PicInfo> picInfos) {
        this.picInfos = picInfos;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }
}
