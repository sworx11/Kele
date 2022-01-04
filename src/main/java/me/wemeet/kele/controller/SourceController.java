package me.wemeet.kele.controller;

import me.wemeet.kele.entity.Source;
import me.wemeet.kele.service.ISourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Quino Wu
 * @since 2021-12-04
 */
@RestController
@RequestMapping("/source")
public class SourceController {

    private ISourceService sourceService;

    @Autowired
    public void setSourceService(ISourceService sourceService) {
        this.sourceService = sourceService;
    }

    @GetMapping("ordered/weight")
    public List<Source> listAllOrderByWeight() {
        return sourceService.listAllOrderByWeight();
    }

    @GetMapping("")
    public List<Source> listAll() {
        return sourceService.list();
    }

    @GetMapping("{id}")
    public Source getById(@PathVariable String id) {
        return sourceService.getById(id);
    }

    @PostMapping("")
    public Source save(Source entity) {
        sourceService.save(entity);
        return entity;
    }

    @DeleteMapping("")
    public Source remove(Source entity) {
        sourceService.removeById(entity);
        return entity;
    }
}
