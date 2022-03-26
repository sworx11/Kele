package me.wemeet.kele.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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
 * @since 2022-03-26
 */
@Getter
@Setter
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String mid;

    private String source;

    @TableField("`name`")
    private String name;

    private String singerName;

    private String description;

    private String coverUrl;

    private Integer trackCount;

    private String company;

    private LocalDateTime publishAt;


}
