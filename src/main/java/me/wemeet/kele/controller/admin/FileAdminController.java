package me.wemeet.kele.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.wemeet.kele.common.response.KeleResponseEntity;
import me.wemeet.kele.entity.File;
import me.wemeet.kele.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/file")
public class FileAdminController {
    private IFileService fileService;

    @Autowired
    public void setFileService(IFileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("")
    public KeleResponseEntity<IPage<File>> listFile(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "limit", required = false, defaultValue = "20") int limit
    ) {
        IPage<File> result = fileService.listFile(new Page<>(page, limit));
        return KeleResponseEntity.<IPage<File>>builder().ok(result).build();
    }

    @PostMapping("")
    public KeleResponseEntity<?> saveFile(@RequestBody File file) {
        fileService.saveOrUpdate(file);
        return KeleResponseEntity.builder().ok().build();
    }

    @DeleteMapping("")
    public KeleResponseEntity<?> deleteFile(@RequestBody File file) {
        fileService.removeById(file.getId());
        return KeleResponseEntity.builder().ok().build();
    }
}
