package com.example.demo.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Component
public class UploadImage {

    @Value("${ftp.host}")
    private String server;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.username}")
    private String user;

    @Value("${ftp.password}")
    private String password;

    @Value("${ftp.upload-dir}")
    private String uploadDir;

    public void upload(byte[] bytes, String fileName) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // 确保目录存在
            String[] directories = uploadDir.split("/");
            for (String dir : directories) {
                if (!dir.isEmpty() && !ftpClient.changeWorkingDirectory(dir)) {
                    ftpClient.makeDirectory(dir);
                    ftpClient.changeWorkingDirectory(dir);
                }
            }

            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            boolean done = ftpClient.storeFile(fileName, inputStream);
            inputStream.close();

            if (done) {
                System.out.println("上传成功!");
            } else {
                throw new IOException("文件上传失败");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
