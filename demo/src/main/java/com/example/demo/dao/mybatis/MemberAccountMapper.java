package com.example.demo.dao.mybatis;

import com.example.demo.dao.repository.pojo.MemberAccountDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MemberAccountMapper {

    @Select("SELECT id, username, password FROM db1.account WHERE username = #{username}")
    MemberAccountDo findByUsername(String username);
    @Select("SELECT id, username, password FROM db1.account WHERE username = #{username}")
    MemberAccountDo findById(Integer userId);
    @Update("UPDATE db1.account SET password = #{password} WHERE id = #{id}")
    int updatePassword(MemberAccountDo memberAccountDo);
}

