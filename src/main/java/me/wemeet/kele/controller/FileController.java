package me.wemeet.kele.controller;

import me.wemeet.kele.common.response.KeleResponseEntity;
import me.wemeet.kele.common.response.KeleResponseStatus;
import me.wemeet.kele.entity.File;
import me.wemeet.kele.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Quino Wu
 * @since 2022-05-14
 */
@RestController
@RequestMapping("/file")
public class FileController {
    private IFileService fileService;

    @Autowired
    public void setFileService(IFileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/upload")
    public KeleResponseEntity<String> upLoadFiles(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()){
            return KeleResponseEntity.<String>builder().status(KeleResponseStatus.REQUEST_PARAMETER_ERROR).build();
        }
        String fileId = fileService.uploadFiles(multipartFile);
        if (!StringUtils.hasText(fileId)) {
            return KeleResponseEntity.<String>builder().status(KeleResponseStatus.WARN).build();
        }
        return KeleResponseEntity.<String>builder().ok(fileId).build();
    }

    @GetMapping(value = "/download/{id}")
    public void downloadFiles(@PathVariable("id") String id, HttpServletResponse response) {
        OutputStream outputStream = null;
        InputStream inputStream;
        File files = fileService.getFileById(id);
        inputStream = fileService.getFileInputStream(files);
        try {
            outputStream = response.getOutputStream();
            byte[] buffer = new byte[4096];
            while (inputStream.read(buffer) > 0) {
                outputStream.write(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null){
                    inputStream.close();
                }
                if (outputStream != null){
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
