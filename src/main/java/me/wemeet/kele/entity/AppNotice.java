package me.wemeet.kele.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("app_notice")
public class AppNotice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String title;

    private String content;

    private String type;

    private String remark;

    private Long createBy;

    private Long updateBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateAt;

    @TableField(fill = FieldFill.INSERT)
    private String active;


}
