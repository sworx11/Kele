package me.wemeet.kele.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2021-12-04
 */
@Getter
@Setter
public class Favorite implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private Long userId;

    private Long songId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createAt;

}
