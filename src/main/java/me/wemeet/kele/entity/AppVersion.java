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
@TableName("app_version")
public class AppVersion implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String appId;

    private String appName;

    private String versionCode;

    private String versionName;

    private String fileHash;

    private int fileSize;

    private String updateLog;

    private String platform;

    private String updateStatus;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateAt;

    private String downloadUrl;


}
