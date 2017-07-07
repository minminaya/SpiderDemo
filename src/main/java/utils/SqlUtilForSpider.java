package utils;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库的相关工具类，这个是用来测试数据库基本操作能不能用的
 * <p>
 * Created by Niwa on 2017/7/4.
 */
public class SqlUtilForSpider {

    /**
     *
     *  连接数据库
     * */
    public static Connection getConn() {

        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/meizitu";
        String username = "root";
        String password = "root";
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("数据库连接已建立");
        return conn;
    }

    /**
     * 查询妹子图表的数据，这里用于测试数据库读取的
     */
    public static String getmeiziWebOne() {
        Connection connection = getConn();
        String sql = "select * from meiziwebone";
        PreparedStatement preparedStatement;

        try {
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("============================");
            while (rs.next()) {
                System.out.println(rs.getString("webtitle"));
                System.out.println(rs.getInt("id"));
                System.out.println(rs.getInt("picStartId"));
                System.out.println(rs.getInt("picSize"));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 存数据进meiziwebone表里
     *
     * @param id       主键，
     * @param webtitle 当前网页的标题
     * @param startid  picinfos表中当前标题的网页存的图片的起始位置
     * @param picSize  图片数量
     */
    public static void insertDataFormeiziweboneTable(int id, String webtitle, int startid, int picSize) {
        Connection connection = getConn();
        PreparedStatement preparedStatement = null;
        String sql = "insert into meiziwebone value(?,?,?,?)";
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            //存数据进数据库，parammeterIndex分别1,2,3,4分别表示上面的四个？号
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, webtitle);
            preparedStatement.setInt(3, startid);
            preparedStatement.setInt(4, picSize);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("数据-----" + "id:" + id + "," + "webtitle:" + webtitle + "," + "startid:" + startid + "," + "picSize:" + picSize + "插入完成");

    }

    /**
     * 存数据进picinfos表里
     *
     * @param id     主键，
     * @param picUrl 当前图片
     */
    public static void insertDataForpicinfosTable(int id, String picUrl) {
        Connection connection = getConn();
        PreparedStatement preparedStatement = null;
        String sql = "insert into picinfos value(?,?)";
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            //存数据进数据库
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, picUrl);
            preparedStatement.executeUpdate();
            connection.commit();
            connection.close();
            System.out.println("数据库连接已关闭");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("数据-----" + "id:" + id + "," + "picUrl:" + picUrl + "插入完成");

    }
}
