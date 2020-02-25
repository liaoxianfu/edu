package com.liao.edu.ffmpeg.video.controller;


import com.liao.edu.common.constants.ResultCodeEnum;
import com.liao.edu.common.entity.EduResource;
import com.liao.edu.common.exception.EduException;
import com.liao.edu.common.vo.R;
import com.liao.edu.ffmpeg.video.service.EduResourceService;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author liao
 * @since 2019-12-21
 */

@Api(description = "Minio文件存储")
@Slf4j
@RestController
@RequestMapping("/edu/edu-resource")
public class EduResourceController {

    private final EduResourceService eduResourceService;

    public EduResourceController(EduResourceService eduResourceService) {
        this.eduResourceService = eduResourceService;
    }

    @PostMapping("video/storage")
    public R saveInfo(@RequestBody EduResource eduResource) {
        log.info("获取到的信息为{}", eduResource);
        boolean save = eduResourceService.save(eduResource);
        if (save) {
            return R.ok();
        }
        return R.error();
    }


    @ApiOperation(value = "视频文件上传")
    @PostMapping("/video")
    public R uploadFile(String courseId, String token, MultipartFile file) {
        log.info("获取到数据{}，{}", courseId, token);
        eduResourceService.uploadResource(courseId, token, file);
        return R.ok();
    }


    @ApiOperation(value = "删除视频")
    @DeleteMapping("/video/{courseId}")
    public R deleteFile(@PathVariable String courseId) {
        EduResource eduResource = eduResourceService.getById(courseId);
        String videoUrl = eduResource.getVideoUrl();
        if (StringUtils.isEmpty(videoUrl)) {
            boolean b = eduResourceService.removeById(eduResource);
            if (b) {
                return R.ok();
            } else {
                return R.error();
            }
        } else {
            boolean b = eduResourceService.deleteResource(eduResource);
            if (b) {
                return R.ok();
            } else {
                return R.error();
            }
        }
    }

    @ApiOperation(value = "获取上传文件的原文名")
    @GetMapping("/video/{id}")
    public R getVideoNameById(@PathVariable String id) {
        String name = eduResourceService.getUploadFileNameById(id);
        return R.ok().data("name", name);
    }

    @ApiOperation(value = "通过文件id获取视频的文件")
    @GetMapping("/video/file/{id}")
    public void getVideoFileByName(@PathVariable String id, HttpServletResponse response) {
        // 通过id获取文件对象
        EduResource eduResource = eduResourceService.getById(id);
        // 定义文件缓存
        byte[] buffer = new byte[1024];
        //输出流
        OutputStream os = null;
        // 获取名称
        String[] split = eduResource.getVideoUrl().split("/");
        String fileName = split[split.length - 1];
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            os = response.getOutputStream();
            InputStream inputStream = eduResourceService.getFileObject(eduResource);
            int i = inputStream.read(buffer);
            while (i > 0) {
                os.write(buffer, 0, i);
                i = inputStream.read(buffer);
            }
            inputStream.close();
            os.close();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new EduException(ResultCodeEnum.DOWNLOAD_FILE_ERROR);
        }
    }

    @ApiOperation(value = "通过文件id获取视频的文件")
    @GetMapping("/video/play/{id}")
    public void test(@PathVariable String id, HttpRequest request, HttpResponse response){
        BufferedInputStream bis = null;
        long p = 0L;
        long toLength = 0L;
        long contentLength = 0L;
        int rangeSwitch = 0; // 0,从头开始的全文下载；1,从某字节开始的下载（bytes=27000-）；2,从某字节开始到某字节结束的下载（bytes=27000-39000）
        long fileLength;
        String rangBytes = "";
        EduResource eduResource = eduResourceService.getById(id);
        InputStream fileObject = eduResourceService.getFileObject(eduResource);

    }


    /*



    @RequestMapping(value = "/player", method = RequestMethod.GET)
	public void player2(HttpServletRequest request, HttpServletResponse response) {
		String path = request.getServletContext().getRealPath("/static/my/video/interview.mp4");
		BufferedInputStream bis = null;
		try {
			File file = new File(path);
			if (file.exists()) {
				long p = 0L;
				long toLength = 0L;
				long contentLength = 0L;
				int rangeSwitch = 0; // 0,从头开始的全文下载；1,从某字节开始的下载（bytes=27000-）；2,从某字节开始到某字节结束的下载（bytes=27000-39000）
				long fileLength;
				String rangBytes = "";
				fileLength = file.length();

				// get file content
				InputStream ins = new FileInputStream(file);
				bis = new BufferedInputStream(ins);

				// tell the client to allow accept-ranges
				response.reset();
				response.setHeader("Accept-Ranges", "bytes");

				// client requests a file block download start byte
				String range = request.getHeader("Range");
				if (range != null && range.trim().length() > 0 && !"null".equals(range)) {
					response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);
					rangBytes = range.replaceAll("bytes=", "");
					if (rangBytes.endsWith("-")) { // bytes=270000-
						rangeSwitch = 1;
						p = Long.parseLong(rangBytes.substring(0, rangBytes.indexOf("-")));
						contentLength = fileLength - p; // 客户端请求的是270000之后的字节（包括bytes下标索引为270000的字节）
					} else { // bytes=270000-320000
						rangeSwitch = 2;
						String temp1 = rangBytes.substring(0, rangBytes.indexOf("-"));
						String temp2 = rangBytes.substring(rangBytes.indexOf("-") + 1, rangBytes.length());
						p = Long.parseLong(temp1);
						toLength = Long.parseLong(temp2);
						contentLength = toLength - p + 1; // 客户端请求的是 270000-320000 之间的字节
					}
				} else {
					contentLength = fileLength;
				}

				// 如果设设置了Content-Length，则客户端会自动进行多线程下载。如果不希望支持多线程，则不要设置这个参数。
				// Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]
				response.setHeader("Content-Length", new Long(contentLength).toString());

				// 断点开始
				// 响应的格式是:
				// Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
				if (rangeSwitch == 1) {
					String contentRange = new StringBuffer("bytes ").append(new Long(p).toString()).append("-")
							.append(new Long(fileLength - 1).toString()).append("/")
							.append(new Long(fileLength).toString()).toString();
					response.setHeader("Content-Range", contentRange);
					bis.skip(p);
				} else if (rangeSwitch == 2) {
					String contentRange = range.replace("=", " ") + "/" + new Long(fileLength).toString();
					response.setHeader("Content-Range", contentRange);
					bis.skip(p);
				} else {
					String contentRange = new StringBuffer("bytes ").append("0-").append(fileLength - 1).append("/")
							.append(fileLength).toString();
					response.setHeader("Content-Range", contentRange);
				}

				String fileName = file.getName();
				response.setContentType("application/octet-stream");
				response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

				OutputStream out = response.getOutputStream();
				int n = 0;
				long readLength = 0;
				int bsize = 1024;
				byte[] bytes = new byte[bsize];
				if (rangeSwitch == 2) {
					// 针对 bytes=27000-39000 的请求，从27000开始写数据
					while (readLength <= contentLength - bsize) {
						n = bis.read(bytes);
						readLength += n;
						out.write(bytes, 0, n);
					}
					if (readLength <= contentLength) {
						n = bis.read(bytes, 0, (int) (contentLength - readLength));
						out.write(bytes, 0, n);
					}
				} else {
					while ((n = bis.read(bytes)) != -1) {
						out.write(bytes, 0, n);
					}
				}
				out.flush();
				out.close();
				bis.close();
			}
		} catch (IOException ie) {
			// 忽略 ClientAbortException 之类的异常
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
     */

}

