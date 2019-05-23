package util;

import bean.PersonBean;
import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author chenzhicong
 * @time 2019/5/21 9:48
 * @description 用于xml与bean之间的转换
 */
public class BetwixtStudy {
    /**
      * 将bean转换为xml
      */
    @Test
    public void bean2Xml() throws Exception{
        // 先创建一个StringWriter，我们将把它写入为一个字符串
        StringWriter outputWriter = new StringWriter();
    // Betwixt在这里仅仅是将Bean写入为一个片断
    // 所以如果要想完整的XML内容，我们应该写入头格式
    outputWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        // 创建一个BeanWriter，其将写入到我们预备的stream中
        BeanWriter beanWriter = new BeanWriter(outputWriter);
        // 配置betwixt
        // 是否以attribute的形式显示简单的对象
        beanWriter.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
        //当遇到ArrayList或者Map时做一个包装
        beanWriter.getXMLIntrospector().getConfiguration().setWrapCollectionsInElement(true);
        //是否自动生成id（自动对map（bean）生成id）
        beanWriter.getBindingConfiguration().setMapIDs(false);
        //以缩进方式显示
        beanWriter.enablePrettyPrint();
        //每行结束时用什么字符,这里用一个换行符
        beanWriter.setEndOfLine("");
        //如果是null就写一个空值 
        beanWriter.setWriteEmptyElements(false);

        // 如果这个地方不传入XML的根节点名，Betwixt将以Bean的类名作为根节点
        // 但是让我们将例子Bean名作为根节点吧
        beanWriter.write("person", new PersonBean("John Smith", 21));
        //输出结果
        System.out.println(outputWriter.toString());
        // Betwixt写的是片断而不是一个文档，所以不要自动的关闭掉writers或者streams，
        //但这里仅仅是一个例子，不会做更多事情，所以可以关掉
        outputWriter.close();
    }

    /**
     * 将xml转换为bean
     */
    @Test
    public void xml2bean() throws Exception{
    // 先创建一个XML，由于这里仅是作为例子，所以我们硬编码了一段XML内容
    StringReader xmlReader =
        new StringReader(
            "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>  <person>    <age>21</age>    <name>John Smith</name>  </person>");
        //创建BeanReader
        BeanReader beanReader  = new BeanReader();
        //配置reader
        beanReader.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
        beanReader.getBindingConfiguration().setMapIDs(false);
        //注册beans，以便betwixt知道XML将要被转化为一个什么Bean
        beanReader.registerBeanClass("person", PersonBean.class);
        //现在我们对XML进行解析
        PersonBean person = (PersonBean) beanReader.parse(xmlReader);
        //输出结果
        System.out.println(person);
    }
}

