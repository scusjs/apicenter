package me.liexing.apicenter.general.service;

import me.liexing.apicenter.general.entity.SkEntity;
import me.liexing.apicenter.general.mapper.SkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class SkService {
    @Autowired
    private SkMapper skMapper;

    @Cacheable(cacheNames = "sk_cache", key = "#skcode+#origin")
    public boolean isValid(String skcode, String origin) {
        if(origin == null)
            return false;

        SkEntity skEntity = skMapper.getBySk(skcode);
        if (skEntity == null){
            return false;
        }
        try {
            String url = new URL(origin).getHost();
            if (! url.equals(skEntity.getUrl())){
                return false;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
