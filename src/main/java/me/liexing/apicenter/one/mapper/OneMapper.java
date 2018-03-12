package me.liexing.apicenter.one.mapper;

import me.liexing.apicenter.one.entity.OneEntity;
import org.apache.ibatis.annotations.*;

import java.util.Date;


public interface OneMapper {
    @Insert("INSERT INTO ones(id, category,item_id,forward,img_url,volume,words_info,share_url,date) " +
            "VALUES(#{id}, #{category}, #{item_id}, #{forward}, #{img_url}, #{volume}, #{words_info}, #{share_url},#{date})")
    int insert(OneEntity record);

    @Select("SELECT * FROM ones WHERE date = #{date}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "category", column = "category"),
            @Result(property = "item_id", column = "item_id"),
            @Result(property = "forward", column = "forward"),
            @Result(property = "img_url", column = "img_url"),
            @Result(property = "volume", column = "volume"),
            @Result(property = "words_info", column = "words_info"),
            @Result(property = "share_url", column = "share_url"),

    })
    OneEntity getOneByDate(Date date);

}
