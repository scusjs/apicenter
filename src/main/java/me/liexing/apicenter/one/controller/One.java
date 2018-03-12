package me.liexing.apicenter.one.controller;

import me.liexing.apicenter.general.entity.RestfulResult;
import me.liexing.apicenter.one.entity.OneEntity;
import me.liexing.apicenter.one.service.impl.OneServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/one")
public class One {

    @Autowired
    private OneServiceImpl oneService;

    @PostMapping
    public RestfulResult postOneInfo() {
        OneEntity oneEntity = oneService.getIndexData();
        RestfulResult restfulResult = new RestfulResult();
        restfulResult.setCode(0);
        restfulResult.setData(oneEntity);
        return restfulResult;
    }

    @RequestMapping(method = RequestMethod.GET)
    public RestfulResult getOneInfo() {
        OneEntity oneEntity = oneService.getIndexData();
        RestfulResult restfulResult = new RestfulResult();
        restfulResult.setCode(0);
        restfulResult.setData(oneEntity);
        return restfulResult;
    }
}
