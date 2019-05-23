package util;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.junit.Test;

import java.net.URL;

/**
 * @author chenzhicong
 * @time 2019/5/23 11:22
 * @description
 */
public class EmailStudy {
  /** 发送简单文本文件 */
  @Test
  public void testSendSimpleEmail() throws Exception {
    SimpleEmail email = new SimpleEmail();
    // 通过QQ Server 发送邮件
    email.setHostName("smtp.qq.com"); // 设定smtp服务器
    email.setSSLOnConnect(Boolean.TRUE); // 设定是否使用SSL 
    email.setSslSmtpPort("465"); // 设定SSL端口
    email.setAuthentication("383059038@qq.com", "vjpjftyvwkmmbhdj"); // 设定smtp服务器的认证资料信息
    email.addTo("383059038@qq.com", "czc"); // 设定收件人
    email.setCharset("UTF-8"); // 设定内容的语言集
    email.setFrom("383059038@qq.com"); // 设定发件人
    email.setSubject("Hello"); // 设定主题
    email.setMsg("中国\n "); // 设定邮件内容
    email.send(); // 发送邮件
  }

  /**
    * 发送带附件邮件
    */
  @Test
  public void testSendFileEmail() throws Exception {
    MultiPartEmail email = new MultiPartEmail();
    // 通过QQ Server 发送邮件
    email.setHostName("smtp.qq.com"); //  设定smtp服务器
    email.setSSLOnConnect(Boolean.TRUE); //  设定是否使用SSL
    email.setSslSmtpPort("465"); //  设定SSL端口
    email.setAuthentication("383059038@qq.com", "vjpjftyvwkmmbhdj"); //  设定smtp服务器的认证资料信息
    EmailAttachment attachment = new EmailAttachment();
    attachment.setPath(
        this.getClass().getClassLoader().getResource("").getPath() + "test.jpg"); // 设定发送附件路径
    // 设定合法的URL指向文件
    // attachment.setURL(new URL(http://www.apache.org/images/asf_logo_wide.gif));
    attachment.setDisposition(EmailAttachment.ATTACHMENT); // 设定附件的方式（内嵌，附件）
    attachment.setDescription("Picture");
    attachment.setName("曾艳芬"); // 附件的文件名
    email.addTo("383059038@qq.com", "czc"); //  设定收件人
    email.setCharset("UTF-8"); //  设定内容的语言集
    email.setFrom("383059038@qq.com"); //  设定发件人
    email.setSubject("common email"); //  设定主题
    email.setMsg("测试邮件"); //  设定邮件内容
    email.attach(attachment);
    email.send();
  }

  /**
   * 发送HTML邮件
   */
  @Test
  public void testSendHtmlEmail() throws Exception {
    HtmlEmail email = new HtmlEmail();
    email.setHostName("smtp.qq.com"); //  设定smtp服务器
    email.setSSLOnConnect(Boolean.TRUE); //  设定是否使用SSL
    email.setSslSmtpPort("465"); //  设定SSL端口
    email.setAuthentication("383059038@qq.com", "vjpjftyvwkmmbhdj"); //  设定smtp服务器的认证资料信息
    email.addTo("383059038@qq.com", "To"); //  设定收件人
    email.setFrom("383059038@qq.com", "From");
    email.setSubject("Test email with inline image");
    //  获取图片的url
    URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
    /**
     * 那么我们在发送邮件的时候就需要对这些链接的图片做特殊处理。
     * 否则在对方接收到邮件的时候会看不到图片。
     * 我们特殊处理的方法就是把它们当成附件发送，
     * 但不显示在附件里。要做到这些就首先需要对输入的content进行解析，
     * 找到所带图片的路径。
     * 然后把content中<img src=”c:/test.jpg”></img>
     * 这段代码变成<img src=” cid:IMG”></img>
     */
    //将原本的图片Url解析为cid
    String cid = email.embed(url, "Apache logo");
    //  设定发送的html
    email.setHtmlMsg("<html>The apache logo - <img src=\"cid:" + cid + "\"></html>");
    //  失败后的邮件
    email.setTextMsg("Your email client does not support HTML messages");
    email.send();
  }
}
