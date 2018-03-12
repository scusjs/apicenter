package me.liexing.apicenter.one.controller;

import me.liexing.apicenter.general.entity.RestfulResult;
import me.liexing.apicenter.one.entity.OneEntity;
import me.liexing.apicenter.one.service.OneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/one")
public class One {

    @Autowired
    private OneService oneService;

    @PostMapping()
    public RestfulResult getOneInfo() {
        OneEntity oneEntity = oneService.getIndexData();
        RestfulResult restfulResult = new RestfulResult();
        restfulResult.setCode(0);
        restfulResult.setData(oneEntity);
        return restfulResult;
    }
}
