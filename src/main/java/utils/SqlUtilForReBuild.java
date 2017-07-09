package utils;

/**
 * Created by Niwa on 2017/7/9.
 */
public class SqlUtilForReBuild {

    /**
     * 重新创建数据库的2个表
     */
    public static void rebuild() {
        SqlUtilForSpider.executeSQL("DROP TABLE IF EXISTS `meiziwebone`;");
        SqlUtilForSpider.executeSQL("DROP TABLE IF EXISTS `picinfos`;");
        String sql = null;
        sql = "CREATE TABLE `meiziwebone` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `webTitle` varchar(255) DEFAULT '无标题' COMMENT '图片标题',\n" +
                "  `picStartId` int(11) DEFAULT NULL COMMENT '当前网页图片url在表picInfos中的起始位置',\n" +
                "  `picSize` int(11) DEFAULT NULL COMMENT '当前网页图片数量',\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=351 DEFAULT CHARSET=utf8;";
        SqlUtilForSpider.executeSQL(sql);
        sql = "CREATE TABLE `picinfos` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `piUrl` varchar(255) DEFAULT 'http://m.jikeyo.com/wap/images/error.png',\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=2928 DEFAULT CHARSET=utf8;";
        SqlUtilForSpider.executeSQL(sql);
        System.out.println("-------------数据表中数据已清除--------------");
    }
}
