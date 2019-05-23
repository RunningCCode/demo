package util;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.DefaultExpressionEngine;
import org.apache.commons.configuration2.tree.DefaultExpressionEngineSymbols;
import org.junit.Test;

import java.io.File;

/**
 * @author chenzhicong
 * @time 2019/5/21 20:36
 * @description Configuration2学习
 */
public class ConfigurationStudy {
    public static final String fileName = "properties"+ File.separator+"icp.properties";
    public static PropertiesConfiguration cfg = null;

    /**
      *读取properties文件的内容
      */
    @Test

    public void testReadProperties(){
        FileBasedConfigurationBuilder.setDefaultEncoding(PropertiesConfiguration.class, "UTF-8");

        Configurations configs = new Configurations();
        try{
            Configuration config = configs.properties(new File("icp.properties"));
            // access configuration properties

            String users = config.getString("java.home");
            System.out.println(users);
        }catch (ConfigurationException e){

        }

       /* try
        {
            Configurations configs = new Configurations();
            // setDefaultEncoding是个静态方法,用于设置指定类型(class)所有对象的编码方式。
            // 本例中是PropertiesConfiguration,要在PropertiesConfiguration实例创建之前调用。
            FileBasedConfigurationBuilder.setDefaultEncoding(PropertiesConfiguration.class, "UTF-8");
            PropertiesConfiguration propConfig = configs.properties(this.getClass().getClassLoader().getResource("log4j.properties"));
            System.out.println(propConfig.getString("log4j.appender.CONSOLE.Target"));
            System.out.println(propConfig.getBoolean("log4j.appender.LOGFILE.Append"));
            System.out.println(propConfig.getString("test"));
        }
        catch(Throwable e)
        {
            e.printStackTrace();
        }
*/
    }
    /**
     * 读取xml文件内容
     */
    @Test
    public void testReadXml(){
        XMLConfiguration config = null;
        try {
            Configurations configs = new Configurations();
            config = configs.xml("generatorConfig.xml");
            {
                // 使用默认的符号定义创建一个表达式引擎
                DefaultExpressionEngine engine = new DefaultExpressionEngine(
                        DefaultExpressionEngineSymbols.DEFAULT_SYMBOLS);
                // 指定表达式引擎
                config.setExpressionEngine(engine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("******数据库连接信息******");
        System.out.println(config.getString("jdbcConnection[@driverClass]"));
        System.out.println(config.getString("jdbcConnection[@connectionURL]"));
        System.out.println(config.getString("jdbcConnection[@root]"));
        System.out.println(config.getString("jdbcConnection[@password]",""));
        System.out.println(config.getString("jdbcConnection.abc"));
        System.out.println("******模板信息******");
        int tableLen = config.getMaxIndex("table")+1;
        System.out.println(tableLen);
        for(int i=0;i<tableLen;i++){
            System.out.println("表名："+config.getString("table("+i+")[@tableName]"));
            System.out.println("对象名："+config.getString("table("+i+")[@domainObjectName]"));
            System.out.println("模板父目录："+config.getString("table("+i+")[@tempPath]"));
            int propertyLen = config.getMaxIndex("table("+i+").property")+1;
            System.out.println("table有"+propertyLen+"个子节点");
            for(int j=0;j<propertyLen;j++){
                System.out.println(config.getString("table("+i+").property("+j+")[@tempName]"));
            }
        }
    }

}
