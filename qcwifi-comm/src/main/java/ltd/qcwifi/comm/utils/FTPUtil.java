package ltd.qcwifi.comm.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FTP操作工具类
 * @author 张芳
 *
 */
public class FTPUtil {
    
    private String userName;     //登录名
    private String password;     //密码
    private String ftpHostName;  //ftp地址
    private int port = 21;       //端口
    private FTPClient ftpClient = new FTPClient();
    private OutputStream os = null;
    private InputStream is = null;
    private static Logger logger = LoggerFactory.getLogger(FTPUtil.class);
    
    private Pattern pattern = Pattern.compile("\\d{8}");    //8位数字
    
    public FTPUtil(String userName,String password,String ftpHostName,int port){
        super();
        this.userName = userName;
        this.password = password;
        this.ftpHostName = ftpHostName;
        this.port = port;
    }
    public FTPUtil(String userName,String password,String ftpHostName){
    	super();
    	this.userName = userName;
    	this.password = password;
    	this.ftpHostName = ftpHostName;
    }
    /**
     * 建立链接
     */
    private void connect(){
        try {
            System.out.println("开始链接...");
            ftpClient.connect(ftpHostName, port);
            int reply = ftpClient.getReplyCode();   //ftp响应码
            if(!FTPReply.isPositiveCompletion(reply)){  //ftp拒绝链接
                logger.error("ftp拒绝链接...");
                ftpClient.disconnect();
            }
            ftpClient.login(userName, password);
            ftpClient.enterLocalPassiveMode();       //设置被动模式    通知server端开通端口传输数据
            ftpClient.setBufferSize(256);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setControlEncoding("utf-8");
            System.out.println("登录成功！");
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        }
    }
    /**
     * 退出FTP登录关闭链接   并  关闭输入输出流
     * @param os
     */
    private void close(){
        try {
            if(is!=null){
                is.close();
            }
            if(os!=null){
                os.flush();
                os.close();
            }
            ftpClient.logout();
            System.out.println("退出登录！");
            ftpClient.disconnect();
            System.out.println("关闭链接！");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        }
    }
    
    /**
     * 下载文件
     * @param ftpFileName    
     * @param downloadDate
     */
    public void downloadFiles(String ftpFileName,String downloadDate){
        connect();
        downloadFileByDate(ftpFileName,downloadDate);
        close();
    }
    
    /**
     * 下载指定文件
     * @param ftpFileName
     * @param downloadDate
     */
    private void downloadFileByDate(String ftpFileName,String downloadDate){
        try {
            if(isDir(ftpFileName)){        //文件夹
                String[] names = ftpClient.listNames();
                for(int i=0;i<names.length;i++){
                    System.out.println(names[i] + "--------------");
                    if(pattern.matcher(names[i]).matches()){   //如果是8位数字的文件夹
                        downloadFileByDate(ftpFileName + "/" + downloadDate,downloadDate);  //指定文件夹
                        ftpClient.changeToParentDirectory();
                        break;
                    }
                    if(isDir(names[i])){
                        downloadFileByDate(ftpFileName + "/" + names[i],downloadDate);
                        ftpClient.changeToParentDirectory();
                    }else{
                        is = ftpClient.retrieveFileStream(names[i]);     //取出文件转成输入流 
                        zipByFile(ftpFileName+"/"+names[i],is);                             //压缩文件 
                        //在retrieveFileStream后面加上completePendingCommand，changeWorkingDirectory才能正常输出
                        //而且completePendingCommand一定要在is.close()之后，否则容易程序死掉，坑爹啊；
                        ftpClient.completePendingCommand();  
                        //ftpClient.changeToParentDirectory();
                    }
//                    //测试 
//                    if("04".equals(names[i])){
//                        break;
//                    }
                }
            } else {    //文件
                System.out.println(ftpFileName + "-------------------------");
                is = ftpClient.retrieveFileStream(ftpFileName);
                zipByFile(ftpFileName,is);                             //压缩文件 
                ftpClient.completePendingCommand(); 
                ftpClient.changeToParentDirectory();
            }
            //os.flush();
            System.out.println("下载成功！");
        } catch (IOException e) {
            logger.error("下载失败！",e);
        }
    }
    /**
     * @param ftpFileName
     */
    private void downloadFileOrDir(String ftpFileName){
        try {
            if(isDir(ftpFileName)){        //文件夹
                String[] names = ftpClient.listNames();
                for(int i=0;i<names.length;i++){
                    System.out.println(names[i] + "--------------");
                    if(isDir(names[i])){
                        downloadFileOrDir(ftpFileName + "/" + names[i]);
                        ftpClient.changeToParentDirectory();
                    }else{
                        File loadFile = new File(ftpFileName + File.separator
                                + names[i]);
                        os = new FileOutputStream(loadFile);
                        ftpClient.retrieveFile(names[i], os);
                    }
                }
            } else {    //文件
                File file = new File(ftpFileName);
                os = new FileOutputStream(file);
                ftpClient.retrieveFile(file.getName(), os);
                ftpClient.changeToParentDirectory();
            }
            System.out.println("下载成功！");
        } catch (IOException e) {
            logger.error("下载失败！",e);
        }
    }
    
    /**
     * 判断是否是目录
     * @param fileName
     * @return
     */
    public boolean isDir(String fileName){
         try {
            // 切换目录，若当前是目录则返回true,否则返回true。
            boolean falg = ftpClient.changeWorkingDirectory(fileName);
            return falg;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        }
         return false;
    }
    
    /**
     * 压缩文件 
     */
    private void zipByFile(String fileName,InputStream is){
         try {
            // System.out.println(fileName+"==============");
            ((ZipOutputStream) os).putNextEntry(new ZipEntry(fileName));
             // 设置注释  
            //zip.setComment("hello");  
            int temp = 0;  
            while((temp = is.read()) != -1){  
                os.write(temp);  
            }  
            is.close();
            System.out.println("压缩成功！");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
            
        } finally{
            try {
                if(is!=null){
                    is.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    
    public void uploadFile(String ftpPath,String fileName){
        connect();
        uploadFileByDo(ftpPath,fileName);
        close();
    }
    /**
     * 上传文件 
     * @param ftpPath   FTP服务器保存目录
     * @param fileName  上传到FTP服务器上的文件名
     */
    private void uploadFileByDo(String ftpPath,String fileName){
        try {
            if(ftpClient.changeWorkingDirectory(ftpPath)){
                ftpClient.storeFile(fileName, is);
                is.close();
            }else{
                System.out.println("上传FTP服务器路径有误！");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        } finally{
            try {
                if(is!=null){
                    is.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                logger.error(e.getMessage(), e);
            }
        }
    }

    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getFtpHostName() {
        return ftpHostName;
    }


    public void setFtpHostName(String ftpHostName) {
        this.ftpHostName = ftpHostName;
    }


    public int getPort() {
        return port;
    }


    public void setPort(int port) {
        this.port = port;
    }
    
    public OutputStream getOs() {
        return os;
    }
    public void setOs(OutputStream os) {
        this.os = os;
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //downloadFiles();
    	FTPUtil ftp = new FTPUtil("root", "Wen885845", "120.77.149.125", 21);
    	ftp.connect();
    }

}
