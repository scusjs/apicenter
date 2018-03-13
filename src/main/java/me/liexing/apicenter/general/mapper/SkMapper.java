package me.liexing.apicenter.general.mapper;

import me.liexing.apicenter.general.entity.SkEntity;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;


public interface SkMapper {
    @Select("select * from sks where skcode=#{skcode}")
    @Results({
            @Result(property="skcode", column="skcode"),
            @Result(property="url", column="url"),
            @Result(property="name", column="name"),
            @Result(property="phone", column="phone"),
            @Result(property="email", column="email"),
            @Result(property="valid", column="valid"),
            @Result(property="created_time", column="created_time"),
    })
    SkEntity getBySk(String skcode);
}
