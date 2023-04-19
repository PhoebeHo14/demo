package com.example.demo.mapper;

import com.example.demo.entity.MemberAccount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MemberAccountMapper {

    @Insert(" INSERT INTO test_project.member_account ( "
            + "	   username, password "
            + " ) "
            + " VALUE ( "
            + "	   #{username}, #{password}"
            + " ) ")
    public Integer insert(MemberAccount memberAccount);

    @Select(" SELECT "
            + "	   ID, USERNAME, PASSWORD "
            + " FROM "
            + "	  Account "
            + " WHERE "
            + "	   USERNAME = #{username} ")
    public MemberAccount findMemberAccountByUsername(String username);

    @Update(" UPDATE "
            + "	   Account "
            + " SET "
            + "	   PASSWORD = #{password} "
            + " WHERE "
            + "	   ID = #{id} ")
    public Integer update(MemberAccount memberAccount);

}
