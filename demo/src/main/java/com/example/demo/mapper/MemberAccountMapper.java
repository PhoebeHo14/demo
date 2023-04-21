package com.example.demo.mapper;

import com.example.demo.entity.MemberAccount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MemberAccountMapper {

    @Insert("INSERT INTO db1.account (username, password) VALUES (#{username}, #{password})")
    int insert(MemberAccount memberAccount);

    @Select("SELECT id, username, password FROM db1.account WHERE username = #{username}")
    MemberAccount findByUsername(String username);

    @Update("UPDATE db1.account SET password = #{password} WHERE id = #{id}")
    int update(MemberAccount memberAccount);
}

