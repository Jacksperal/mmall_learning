package com.mmall.util;


import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class FTPUtil {

    private static final Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    private static String ftpIp = MyPropertiesUtil.getProperty("ftp.server.ip");
    private static String ftpUser = MyPropertiesUtil.getProperty("ftp.user");
    private static String ftpPassword = MyPropertiesUtil.getProperty("ftp.password");

    private String ip;
    private int port;
    private String user;
    private String pwd;

    //使用FTPClient上传下载
    private FTPClient ftpClient;

    public FTPUtil(String ip, int port, String user, String pwd) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }

    public static boolean uploadFile(List<File> fileList) throws IOException {
        FTPUtil ftpUtil = new FTPUtil(ftpIp, 21, ftpUser, ftpPassword);
        logger.info("开始连接ftp服务器");
        //remotePath是"image"，也就是传到ftp文件夹下面的img这个文件夹下
        boolean result = ftpUtil.uploadFile("image", fileList);

        logger.info("结束上传,上传结果:{}", result);
        return result;
    }

    //上传的具体逻辑
    //remotePath：远程路径，ftp服务器上的相对路径，上传到ftp服务器上，ftp服务器是一个文件夹，如果需要上传到这个文件夹下的一个文件夹的话，就需要用到remotePath
    private boolean uploadFile(String remotePath, List<File> fileList) throws IOException {
        boolean uploaded = true;
        FileInputStream fis = null;
        //连接ftp服务器
        if (connectServer(this.ip, this.port, this.user, this.pwd)) {
            try {
                //更改工作目录，将remotePath传入，如果是null，则不需要切换
                ftpClient.changeWorkingDirectory(remotePath);
                //设置encoding编码
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                //文件类型设置成二进制文件类型
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                //打开本地的ftp的被动模式
                ftpClient.enterLocalPassiveMode();

                //上传文件
                for (File fileItem : fileList) {
                    fis = new FileInputStream(fileItem);
                    ftpClient.storeFile(fileItem.getName(), fis);
                }
            } catch (IOException e) {
                logger.error("上传文件异常", e);
                uploaded = false;
            } finally {
                fis.close();
                //关闭ftpClient
                ftpClient.disconnect();
            }
        }
        return uploaded;
    }

    //连接ftp服务器
    private boolean connectServer(String ip, int prot, String user, String pwd) {
        boolean isSuccess = false;
        ftpClient = new FTPClient();
        try {
            //ftp服务器的ip
            ftpClient.connect(ip);
            //验证ftp服务器用户验证是否通过
            isSuccess = ftpClient.login(user, pwd);
        } catch (IOException e) {
            logger.error("连接ftp服务器异常", e);
        }
        return isSuccess;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }
}
