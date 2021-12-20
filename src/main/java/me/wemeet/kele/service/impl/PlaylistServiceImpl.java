package me.wemeet.kele.service.impl;

import me.wemeet.kele.entity.Playlist;
import me.wemeet.kele.mapper.PlaylistMapper;
import me.wemeet.kele.service.IPlaylistService;
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
public class PlaylistServiceImpl extends ServiceImpl<PlaylistMapper, Playlist> implements IPlaylistService {

}
