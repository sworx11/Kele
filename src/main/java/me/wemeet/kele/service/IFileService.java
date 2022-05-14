package me.wemeet.kele.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.wemeet.kele.entity.File;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Quino Wu
 * @since 2022-05-14
 */
public interface IFileService extends IService<File> {
    String uploadFiles(MultipartFile file);
    File getFileById(String id);
    InputStream getFileInputStream(File file);
    IPage<File> listFile(Page<File> page);
}
