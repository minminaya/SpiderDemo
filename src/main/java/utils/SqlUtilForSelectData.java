package utils;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import model.MeiTuModel;
import model.PicInfo;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库的相关工具类
 * <p>
 * Created by Niwa on 2017/7/4.
 */
public class SqlUtilForSelectData {


    private List<PicInfo> picInfos;
    MeiTuModel meiTuModel = new MeiTuModel();
    List<MeiTuModel> meiTuModels = new ArrayList<MeiTuModel>();

    /**
     * 查询picinfos中id为idd到end序号范围内的数据，最后返回List<PicInfo>
     *
     * @param idd 查询的起始号
     * @param end 查询的结束序号
     */
    public List<PicInfo> selectDataForpicinfosTable(int idd, int end) {
        Connection connection = SqlUtilForSpider.getConn();
        PreparedStatement preparedStatement = null;
        String sql = "SELECT id, piUrl FROM picinfos WHERE id BETWEEN  '" + idd + "' AND '" + end + "'";

        PicInfo picInfo;

        try {
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("id:" + resultSet.getInt("id") + "，picUrl:" + resultSet.getString("piUrl"));
                picInfo = new PicInfo(resultSet.getString("piUrl"));
//                System.out.println("picInfo对象：" + picInfo.getPicUrl());
                picInfos.add(picInfo);
            }
            connection.close();
            System.out.println("数据库连接已关闭");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return picInfos;
    }


    /**
     * 查询meiziwebone中id为start到end序号范围内的数据，并嵌套查询picinfos表，封装成MeiTuModel对象，最后返回List<MeiTuModel>
     *
     * @param start 查询的起始id
     * @param end   查询的结束id
     */
    public List<MeiTuModel> selectDataFormeiziweboneTable(int start, int end) {
        Connection connection = SqlUtilForSpider.getConn();
        PreparedStatement preparedStatement = null;
        String sql = "SELECT id, webTitle, picStartId, picSize FROM meiziwebone WHERE id BETWEEN '" + start + "'AND '" + end + "'";

        try {
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                System.out.println("id:" + resultSet.getInt("id") + "，webTitle:" + resultSet.getString("webTitle") + ",picStartId:" + resultSet.getString("picStartId") + ",picSize:" + resultSet.getString("picSize"));

                picInfos = new ArrayList<PicInfo>();

                //这里嵌套查询picinfos表查出picInfos信息用来封装成MeiTuModel对象
                List<PicInfo> picInfos = selectDataForpicinfosTable(resultSet.getInt("picStartId"), (resultSet.getInt("picStartId") + resultSet.getInt("picSize") - 1));
                meiTuModel = new MeiTuModel();

//                for (PicInfo picInfo :
//                        picInfos
//                        ) {
//                    System.out.println("对象中的数据有：" + picInfo.getPicUrl());
//                }

                meiTuModel.setPicInfos(picInfos);
                meiTuModel.setWebTitle(resultSet.getString("webTitle"));
                meiTuModels.add(meiTuModel);
            }
            connection.close();
            System.out.println("数据库连接已关闭");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meiTuModels;
    }
}
