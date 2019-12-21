package com.liao.edu.video;

import com.liao.edu.video.entity.EduResource;
import com.liao.edu.video.service.EduResourceService;
import com.liao.edu.video.util.MinioClientBuilder;
import com.liao.edu.video.util.MinioClientPropertiesUtil;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.xmlpull.v1.XmlPullParserException;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author liao
 * @since 2019/12/21 14:55
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMinio {


    @Test
    public void test01() throws Exception {
        String endpoint = "http://192.168.77.21:9001";
        String accessKey = "minio";
        String secretKey = "minio123";
        MinioClient minioClient = new MinioClient(endpoint, accessKey, secretKey);
        // 创建视频存储桶
//        minioClient.makeBucket("video");

        String fileName = "E:\\Java\\尚硅谷-在线教育\\王泽在线教育\\资源\\1-阿里云上传测试视频\\a.mp4";
        minioClient.putObject("video","video/video3.mp4", fileName);
        System.out.println("success");

    }


    @Test
    public void test02(){
        MinioClient client;
        try {
             client = new MinioClientBuilder().build();
            String fileName = "E:\\Java\\尚硅谷-在线教育\\王泽在线教育\\资源\\1-阿里云上传测试视频\\a.mp4";
            client.putObject("video","video/video3.mp4", fileName);
//            client.putObject("video","章节/video2.mp4",fileName);
        } catch (InvalidPortException | InvalidEndpointException | InvalidBucketNameException | NoSuchAlgorithmException | InsufficientDataException | IOException | InvalidKeyException | NoResponseException | XmlPullParserException | ErrorResponseException | InternalException | InvalidArgumentException e) {
            e.printStackTrace();
        }finally {

        }

        System.out.println("success");
    }

    @Resource
    private MinioClient minioClient;

    @Test
    public void test03(){
        String fileName = "E:\\Java\\尚硅谷-在线教育\\王泽在线教育\\资源\\1-阿里云上传测试视频\\a.mp4";
        try {
            minioClient.putObject("video","video/video4.mp4", fileName);
            System.out.println("success");
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoResponseException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
    }

    // 测试保存视频信息


    @Autowired
    private EduResourceService eduResourceService;

    @Test
    public void test04(){
        EduResource entity = new EduResource().setVideoUrl("demo/demo.mp4");
        boolean save = eduResourceService.save(entity);
        if (save){
            System.out.println(entity);
        }else {
            System.out.println("error");
        }

    }

    @Test
    public void test05(){
        String url = "edu/video/18/1576922252522/a.mp4";
        String[] split = url.split("/");
        System.out.println(split[0]);
        System.out.println(split[split.length-1]);
        String replace = url.replace(split[0] + "/", "");
        System.out.println(replace);
    }

}
