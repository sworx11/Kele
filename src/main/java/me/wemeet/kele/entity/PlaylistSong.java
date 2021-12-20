package me.wemeet.kele.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("playlist_song")
public class PlaylistSong implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long playlistId;

    private Long songId;

    private LocalDateTime addedDate;

}
