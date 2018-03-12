package me.liexing.apicenter.one.service;

import com.google.gson.JsonArray;
import me.liexing.apicenter.one.entity.OneEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OneService {
    private static String ONE_ID_LIST = "http://v3.wufazhuce.com:8000/api/onelist/idlist";
    private static String ONE_BY_ID = "http://v3.wufazhuce.com:8000/api/onelist/%s/0";

    public OneService() {
    }

    private JSONObject getIds(){
        //缓存
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap params = new LinkedMultiValueMap();
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        //  执行HTTP请求
        ResponseEntity<String> result = restTemplate.exchange(this.ONE_ID_LIST, HttpMethod.GET, requestEntity, String.class);

        //todo 异常处理
        return new JSONObject(result.getBody());
    }

    public OneEntity getIndexData(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap params = new LinkedMultiValueMap();
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        //  执行HTTP请求
        JSONObject ids = this.getIds();
        ResponseEntity<String> result = restTemplate.exchange(String.format(this.ONE_BY_ID, ((JSONArray)ids.get("data")).toList().get(0)), HttpMethod.GET, requestEntity, String.class);

        //todo 异常处理
        JSONObject body = (JSONObject) new JSONObject(result.getBody()).get("data");
        Date date = new Date();
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((String) body.get("date"));
        } catch (ParseException e) {

        }
        JSONArray content_list = (JSONArray) body.get("content_list");
        //todo 暂时只处理第一条
        Map content = (Map) content_list.toList().get(0);

        OneEntity one = new OneEntity();
        one.setCategory(Integer.valueOf((String) content.get("category")));
        one.setDate(date);
        one.setForward((String) content.get("forward"));
        one.setId(Integer.valueOf((String) content.get("id")));
        one.setImg_url((String) content.get("img_url"));
        one.setItem_id(Integer.valueOf((String) content.get("item_id")));
        one.setShare_url((String) content.get("share_url"));
        one.setVolume((String) content.get("volume"));
        one.setWords_info((String) content.get("words_info"));

        return one;
    }

    public static void main(String[] args){
        OneService oneService = new OneService();
        //JSONArray test = (JSONArray) oneService.getIds().get("data");
        //System.out.println(test.toList());
        oneService.getIndexData();
    }
}
