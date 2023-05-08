package com.example.demo.controller.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "修改密碼")
public class UpdatePasswordDto {

    @Schema(description = "密碼", required = true, example = "ccc")
    private String password;

}
