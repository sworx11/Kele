package me.wemeet.kele.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.wemeet.kele.common.util.Hash;
import me.wemeet.kele.entity.File;
import me.wemeet.kele.mapper.FileMapper;
import me.wemeet.kele.service.IFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Quino Wu
 * @since 2022-05-14
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

    private FileMapper fileMapper;

    @Autowired
    public void setFileMapper(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @Value("${file.save-path}")
    private String savePath;

    @Value("${file.max-size}")
    private long maxSize;

    @Value("${file.illegal-path}")
    private String illegalPath;

    @Override
    public String uploadFiles(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (!StringUtils.hasText(fileName)) {
            return null;
        }

        if (file.getSize() > maxSize){
            return null;
        }

        String hash = "";
        try {
            hash = toHex(Objects.requireNonNull(Hash.SHA256.checksum(file.getInputStream())));
            QueryWrapper<File> wrapper = new QueryWrapper<>();
            wrapper.lambda().select(File::getId).eq(File::getHash, hash);
            File test = getOne(wrapper);
            if (test != null) {
                return String.valueOf(test.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long fileId = IdWorker.getId();
        String ext = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : "";
        String newName = fileId + ext;
        java.io.File newFile = new java.io.File(generateSavePath(), newName);
        if (!newFile.getParentFile().exists()) {
            if (!newFile.getParentFile().mkdirs()) {
                return null;
            }
        }
        try {
            file.transferTo(newFile);
            File entity = new File();
            entity.setId(fileId);
            entity.setFileName(newName);
            entity.setFilePath(newFile.getPath());
            entity.setExt(ext);
            entity.setHash(hash);
            save(entity);
            return String.valueOf(fileId);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public File getFileById(String id) {
        return getById(id);
    }

    @Override
    public InputStream getFileInputStream(File file) {
        String filePath = file.getFilePath();
        if (!file.getActive().equalsIgnoreCase("Y")) {
            filePath = illegalPath;
        }
        java.io.File _file = new java.io.File(filePath);
        try {
            return new FileInputStream(_file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public IPage<File> listFile(Page<File> page) {
        QueryWrapper<File> wrapper = new QueryWrapper<>();
        wrapper.lambda().orderByDesc(File::getId);
        return fileMapper.selectPage(page, wrapper);
    }

    private String toHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            result.append(String.format("%02x", aByte));
        }
        return result.toString();
    }

    private String generateSavePath() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR) % 1000;
        int month = calendar.get(Calendar.MONTH);
        return savePath + year + "/" + month + "/";
    }
}
