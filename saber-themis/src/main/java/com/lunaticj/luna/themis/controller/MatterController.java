package com.lunaticj.luna.themis.controller;

import com.lunaticj.luna.themis.entity.MatterEntity;
import com.lunaticj.luna.themis.service.MatterService;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * matter controller
 *
 * @author kuro
 * @create 2021-01-23
 **/
@RestController
@RequestMapping("/matter")
public class MatterController {

    @Resource
    private MatterService matterService;

    @GetMapping("/all")
    public List<MatterEntity> getAll() {
        return matterService.getAllList();
    }
}
