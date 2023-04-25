package com.example.demo.dao.mybatis;

import com.example.demo.model.MemberAccountDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MemberAccountMapper {

    @Insert("INSERT INTO db1.account (username, password) VALUES (#{username}, #{password})")
    int insert(MemberAccountDto memberAccountDto);

    @Select("SELECT id, username, password FROM db1.account WHERE username = #{username}")
    MemberAccountDto findByUsername(String username);

    @Update("UPDATE db1.account SET password = #{password} WHERE id = #{id}")
    int updatePassword(MemberAccountDto memberAccountDto);
}

