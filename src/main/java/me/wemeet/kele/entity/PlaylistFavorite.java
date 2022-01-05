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
 * @since 2022-01-05
 */
@Getter
@Setter
@TableName("playlist_favorite")
public class PlaylistFavorite implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private Long playlistId;

    private LocalDateTime createAt;


}
