package org.han.examination.pojo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginInfoDTO {
    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^1([3-9])[0-9]{9}$", message = "请输入正确的手机号")
    private String phone;
    @NotNull(message = "密码不能为空")
    @Pattern(regexp = ".{6,16}", message = "密码必须为6-16位字符")
    private String password;
}
