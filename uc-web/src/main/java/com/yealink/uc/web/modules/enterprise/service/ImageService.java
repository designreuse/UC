package com.yealink.uc.web.modules.enterprise.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import com.google.common.io.ByteStreams;
import com.yealink.uc.platform.utils.JSONConverter;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Dylan
 */
@Service
public class ImageService {

    @Value("${file.service.url}")
    String fileServiceUrl;

    // TODO 捕获此方法抛出的异常
    public void uploadAvatar(String username, String domain, MultipartFile multipartFile) throws IOException, NoSuchAlgorithmException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        InputStream multipartStream = multipartFile.getInputStream();
        byte[] buffer = new byte[1024];
        while (multipartStream.read(buffer) > 0) {
            fileOutputStream.write(buffer);
        }
        fileOutputStream.close();

        String fileSizeStr = String.valueOf(file.length());
        String fileMd5 = DigestUtils.md5Hex(new FileInputStream(file));
        FileBody fileBody = new FileBody(file);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.addPart("avatar", fileBody);
        multipartEntityBuilder.addTextBody("userName", username);
        multipartEntityBuilder.addTextBody("domain", domain);
        multipartEntityBuilder.addTextBody("size", fileSizeStr);
        multipartEntityBuilder.addTextBody("md5", fileMd5);
        StringBuilder urlBuilder = new StringBuilder(fileServiceUrl + "/avatar");
        urlBuilder.append("?").
            append("userName=").append(username).
            append("&").append("domain=").append(domain).
            append("&").append("size=").append(fileSizeStr).
            append("&").append("md5=").append(fileMd5);

        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(urlBuilder.toString());
        httpPost.setEntity(multipartEntityBuilder.build());

        //解析响应状态，判断头像是否上传成功
        HttpResponse response = httpclient.execute(httpPost);
        HttpEntity resEntity = response.getEntity();
        EntityUtils.consume(resEntity);
        file.deleteOnExit();
    }

    // TODO 返回imageId
    public String uploadImage(String username, String businessType, MultipartFile multipartFile) throws IOException, NoSuchAlgorithmException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        InputStream multipartStream = multipartFile.getInputStream();
        byte[] buffer = new byte[1024];
        while (multipartStream.read(buffer) > 0) {
            fileOutputStream.write(buffer);
        }
        fileOutputStream.close();

        String fileSizeStr = String.valueOf(file.length());
        String fileMd5 = DigestUtils.md5Hex(new FileInputStream(file));
        FileBody fileBody = new FileBody(file);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.addPart("avatar", fileBody);
        multipartEntityBuilder.addTextBody("userName", username);
        multipartEntityBuilder.addTextBody("size", fileSizeStr);
        multipartEntityBuilder.addTextBody("md5", fileMd5);
        StringBuilder urlBuilder = new StringBuilder(fileServiceUrl + "/image");
        urlBuilder
            .append("?").append("userName=").append(username).
            append("&").append("size=").append(fileSizeStr).
            append("&").append("md5=").append(fileMd5).
            append("&").append("busiType=").append(businessType).
            append("&").append("valid=").append(1);

        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(urlBuilder.toString());
        httpPost.setEntity(multipartEntityBuilder.build());

        //解析响应状态，判断头像是否上传成功
        HttpResponse response = httpclient.execute(httpPost);
        HttpEntity resEntity = response.getEntity();
        String imageResponse = new String(ByteStreams.toByteArray(response.getEntity().getContent()), "UTF-8");
        ImageDetail imageDetail = JSONConverter.fromJSON(ImageDetail.class, imageResponse);
        EntityUtils.consume(resEntity);
        file.deleteOnExit();
        return imageDetail.getId();
    }

}
