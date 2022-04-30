package me.wemeet.kele.controller.admin;

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
    public List<Source> listAllSource() {
        return sourceService.list();
    }

    @PostMapping("")
    public Source saveSource(Source entity) {
        sourceService.saveOrUpdate(entity);
        return entity;
    }
}
