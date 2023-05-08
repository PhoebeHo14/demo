package com.example.demo.controller.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "用戶", description = "用戶實體定義")
public class MemberAccountDto {

    @Schema(description = "用戶名", required = true, example = "aaa")
    private String username;
    @Schema(description = "密碼", required = true, example = "bbb")
    private String password;

}
