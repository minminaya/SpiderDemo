package model;

/**
 * 每张图片的信息
 * Created by Niwa on 2017/7/2.
 */
public class PicInfo {


    private String picUrl;
    private String picTitle;


    public PicInfo() {
    }

    public PicInfo(String picUrl, String picTitle) {
        this.picUrl = picUrl;
        this.picTitle = picTitle;
    }

    public PicInfo(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicTitle() {
        return picTitle;
    }

    public void setPicTitle(String picTitle) {
        this.picTitle = picTitle;
    }
}
