package com.liao.edu.ffmpeg.video.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用静态内部类实例化
 *
 * @author liao
 * @since 2020/2/4 18:03
 */
@Slf4j
public class FfmpegToMp4VideoUtils {
    /**
     * TODO:
     * 1、配置外部化参数 将比特率 分辨率 设置为可变的外部参数 转换的分辨率 可以为多个 利用list集合判断
     * 2、输入输出的路径外部化指定
     * 3、转换后上传到minio
     */
    public static boolean processVideo(String exeFilePath, String fileInput, String fileOutPut) {
        List<String> command = new ArrayList<String>();
        command.add(exeFilePath);
        command.add("-i");
        command.add(fileInput);
        command.add("-vcodec");
        command.add("h264");
        command.add("-preset");
        command.add("fast");
        command.add("-b:v");
        command.add("2000k");
        command.add(fileOutPut);
        ProcessBuilder builder = new ProcessBuilder();
        builder.redirectErrorStream(true);
        builder.command(command);
        Process process = null;
        try {
            process = builder.start();
            StringBuilder sbf = new StringBuilder();
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = br.readLine()) != null) {
                sbf.append(line);
                sbf.append("\n");
            }
            String resultInfo = sbf.toString();
            log.info("转码获取的消息:{}", resultInfo);
        } catch (IOException e) {
            log.error("转码失败的信息：{}", e.getMessage());
            return false;
        }
        return true;
    }



}
