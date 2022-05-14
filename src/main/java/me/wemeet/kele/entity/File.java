package me.wemeet.kele.entity;

import java.io.Serializable;

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
 * @since 2022-05-14
 */
@Getter
@Setter
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String filePath;

    private String fileName;

    private String ext;

    private String hash;

    @TableField(fill = FieldFill.INSERT)
    private String active;
}
