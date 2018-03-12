package me.liexing.apicenter.one.service.impl;

import com.google.gson.Gson;
import me.liexing.apicenter.one.entity.OneEntity;
import me.liexing.apicenter.one.mapper.OneMapper;
import me.liexing.apicenter.one.service.OneService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class OneServiceImpl implements OneService{
    private static Logger logger = LoggerFactory.getLogger(OneServiceImpl.class);
    private static String ONE_ID_LIST = "http://v3.wufazhuce.com:8000/api/onelist/idlist";
    private static String ONE_BY_ID = "http://v3.wufazhuce.com:8000/api/onelist/%s/0";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private OneMapper oneMapper;

    public OneServiceImpl() {
    }

    private List<Object> getIds(){
        //缓存
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String redis_key = "id_list" + df.format(new Date());
        ValueOperations<String, String> redis = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(redis_key)){
            return Arrays.asList(redis.get(redis_key).split(","));
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap params = new LinkedMultiValueMap();
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        //  执行HTTP请求
        ResponseEntity<String> result = restTemplate.exchange(this.ONE_ID_LIST, HttpMethod.GET, requestEntity, String.class);

        //todo 异常处理
        List result_list =  ((JSONArray)new JSONObject(result.getBody()).get("data")).toList();
        String tmp_result_string = result_list.toString();
        redis.set(redis_key, tmp_result_string.substring(1, tmp_result_string.length()-1), 1, TimeUnit.HOURS);
        return result_list;
    }

    public OneEntity getIndexData(){
        //检查缓存
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String redis_key = "one_result_" + df.format(new Date());
        ValueOperations<String, String> redis = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(redis_key)){
            logger.info("get one from redis");
            return new Gson().fromJson(redis.get(redis_key), OneEntity.class);
        }
        //检查数据库
        df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        try {
            OneEntity one = oneMapper.getOneByDate((df.parse(df.format(new Date()))));
            if(one != null){
                logger.info("get one from db");
                redis.set(redis_key, new Gson().toJson(one), 1, TimeUnit.HOURS);
                logger.info("save one to redis");
                return one;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap params = new LinkedMultiValueMap();
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        //  执行HTTP请求
        List ids = this.getIds();
        ResponseEntity<String> result = restTemplate.exchange(String.format(this.ONE_BY_ID, ids.get(0)), HttpMethod.GET, requestEntity, String.class);

        //todo 异常处理
        JSONObject body = (JSONObject) new JSONObject(result.getBody()).get("data");
        Date date = new Date();
        try {
            date = new SimpleDateFormat("yyyy-MM-dd 00:00:00").parse((String) body.get("date"));
        } catch (ParseException e) {

        }
        JSONArray content_list = (JSONArray) body.get("content_list");
        //todo 暂时只处理第一条
        Map content = (Map) content_list.toList().get(0);
        String tmp_content = new Gson().toJson(content);
        OneEntity one =  new Gson().fromJson(tmp_content, OneEntity.class);
        one.setDate(date);
        redis.set(redis_key, new Gson().toJson(one), 1, TimeUnit.HOURS);
        logger.info("save one to redis");
        oneMapper.insert(one);
        logger.info("save one to db");
        return one;
    }
}
