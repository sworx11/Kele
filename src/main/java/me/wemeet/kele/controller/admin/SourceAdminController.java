package me.wemeet.kele.controller.admin;

import me.wemeet.kele.common.response.KeleResponseEntity;
import me.wemeet.kele.entity.Source;
import me.wemeet.kele.service.ISourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/source")
public class SourceAdminController {
    private ISourceService sourceService;

    @Autowired
    public void setSourceService(ISourceService sourceService) {
        this.sourceService = sourceService;
    }

    @GetMapping("")
    public KeleResponseEntity<List<Source>> listAllSource() {
        return KeleResponseEntity.<List<Source>>builder().ok(sourceService.list()).build();
    }

    @PostMapping("")
    public KeleResponseEntity<Source> saveSource(@RequestBody Source entity) {
        sourceService.saveOrUpdate(entity);
        return KeleResponseEntity.<Source>builder().ok(entity).build();
    }

    @DeleteMapping("")
    public KeleResponseEntity<Source> deleteSource(@RequestBody Source entity) {
        sourceService.removeById(entity);
        return KeleResponseEntity.<Source>builder().ok(entity).build();
    }
}
