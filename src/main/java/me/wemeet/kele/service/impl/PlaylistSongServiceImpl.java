package me.wemeet.kele.service.impl;

import me.wemeet.kele.entity.PlaylistSong;
import me.wemeet.kele.mapper.PlaylistSongMapper;
import me.wemeet.kele.service.IPlaylistSongService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Quino Wu
 * @since 2021-12-04
 */
@Service
public class PlaylistSongServiceImpl extends ServiceImpl<PlaylistSongMapper, PlaylistSong> implements IPlaylistSongService {

}
