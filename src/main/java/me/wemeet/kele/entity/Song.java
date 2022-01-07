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
 * @since 2021-12-04
 */
@Getter
@Setter
public class Song implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String songmid;

    private String source;

    private String singer;

    @TableField("`name`")
    private String name;

    private String albumName;

    private String albumId;

    private String albumMid;

    private String strMediaMid;

    @TableField("`interval`")
    private int interval;

    private String songId;

    private String copyrightId;

    private String img;

    private String lrc;

    private String lrcUrl;

    private String songUrl;

    @TableField("`hash`")
    private String hash;

    private String types;

}
