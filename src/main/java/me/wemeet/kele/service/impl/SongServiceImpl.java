package me.wemeet.kele.service.impl;

import me.wemeet.kele.entity.Song;
import me.wemeet.kele.mapper.SongMapper;
import me.wemeet.kele.service.ISongService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    private SongMapper songMapper;

    @Autowired
    public void setSongMapper(SongMapper songMapper) {
        this.songMapper = songMapper;
    }

    @Override
    public Song insertOrUpdate(Song song) {
        Song _song = songMapper.getBySongmidAndSource(song.getSongmid(), song.getSource());
        if (_song == null) {
            songMapper.insert(song);
            _song = song;
        } else {
            songMapper.updateById(_song);
        }
        return _song;
    }

    @Override
    public Song getBySongmidAndSource(String songmid, String source) {
        return songMapper.getBySongmidAndSource(songmid, source);
    }
}
