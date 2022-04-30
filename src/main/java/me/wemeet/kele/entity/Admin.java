package me.wemeet.kele.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Quino Wu
 * @since 2022-04-30
 */
@Getter
@Setter
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String name;

    private String password;

    private String email;

    private String phone;

    private String nickName;

    private String avatarUrl;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateAt;


}
