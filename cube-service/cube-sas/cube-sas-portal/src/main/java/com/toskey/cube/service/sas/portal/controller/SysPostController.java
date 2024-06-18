package com.toskey.cube.service.sas.portal.controller;

import com.toskey.cube.common.core.base.PageData;
import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.service.sas.business.service.SysPostService;
import com.toskey.cube.service.sas.business.vo.post.PostFormVO;
import com.toskey.cube.service.sas.business.vo.post.PostQueryResultVO;
import com.toskey.cube.service.sas.business.vo.post.PostQueryVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * SysPostController
 *
 * @author toskey
 * @version 1.0.0
 */
@RestController
@RequestMapping("/sys/post")
public class SysPostController {

    private final SysPostService postService;

    public SysPostController(final SysPostService postService) {
        this.postService = postService;
    }

    @GetMapping("/page")
    public RestResult<PageData<PostQueryResultVO>> page(PostQueryVO query) {
        return RestResult.success(postService.findPage(query));
    }

    @GetMapping("/{id}")
    public RestResult<PostQueryResultVO> info(@PathVariable("id") String id) {
        return RestResult.success(postService.findById(id));
    }

    @PostMapping
    public RestResult<Boolean> save(@Validated @RequestBody PostFormVO form) {
        return RestResult.success(postService.savePost(form));
    }

    @PutMapping
    public RestResult<Boolean> update(@Validated @RequestBody PostFormVO form) {
        return RestResult.success(postService.updatePost(form));
    }

    @DeleteMapping
    public RestResult<Boolean> remove(@RequestParam String[] ids) {
        return RestResult.success(postService.removePost(ids));
    }


}
