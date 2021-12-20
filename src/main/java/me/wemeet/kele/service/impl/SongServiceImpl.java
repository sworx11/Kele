package me.wemeet.kele.service.impl;

import me.wemeet.kele.entity.Song;
import me.wemeet.kele.mapper.SongMapper;
import me.wemeet.kele.service.ISongService;
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
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements ISongService {

}
