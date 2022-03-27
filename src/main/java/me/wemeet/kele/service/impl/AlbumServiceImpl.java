package me.wemeet.kele.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.wemeet.kele.common.constant.KeleConstant;
import me.wemeet.kele.entity.Album;
import me.wemeet.kele.entity.AlbumExtends;
import me.wemeet.kele.entity.AlbumFavorite;
import me.wemeet.kele.mapper.AlbumExtendsMapper;
import me.wemeet.kele.mapper.AlbumFavoriteMapper;
import me.wemeet.kele.mapper.AlbumMapper;
import me.wemeet.kele.service.IAlbumService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Quino Wu
 * @since 2022-03-26
 */
@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements IAlbumService {

    private AlbumMapper albumMapper;

    @Autowired
    public void setAlbumMapper(AlbumMapper albumMapper) {
        this.albumMapper = albumMapper;
    }

    private AlbumFavoriteMapper albumFavoriteMapper;

    @Autowired
    public void setAlbumFavoriteMapper(AlbumFavoriteMapper albumFavoriteMapper) {
        this.albumFavoriteMapper = albumFavoriteMapper;
    }

    private AlbumExtendsMapper albumExtendsMapper;

    @Autowired
    public void setAlbumExtendsMapper(AlbumExtendsMapper albumExtendsMapper) {
        this.albumExtendsMapper = albumExtendsMapper;
    }

    @Override
    public IPage<Album> listByFavorite(Page<Album> page, long userId) {
        return albumMapper.listByFavorite(page, userId);
    }

    @Override
    public Album insertOrUpdate(Album album) {
        QueryWrapper<Album> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Album::getSource, album.getSource())
                .eq(Album::getMid, album.getMid());

        Album _album = albumMapper.selectOne(wrapper);
        if (_album == null) {
            save(album);
        } else {
            album.setId(_album.getId());
            saveOrUpdate(album);
        }
        return album;
    }

    @Override
    public void doFavorite(long userId, Album album) {
        if (album == null) return;

        if (album.getId() == null || album.getId() == 0) {
            album = insertOrUpdate(album);
        }

        if (album != null) {
            QueryWrapper<AlbumFavorite> wrapper = new QueryWrapper<>();
            wrapper.lambda()
                    .eq(AlbumFavorite::getUserId, userId)
                    .eq(AlbumFavorite::getAlbumId, album.getId());

            AlbumFavorite favorite = albumFavoriteMapper.selectOne(wrapper);
            if (favorite == null) {
                favorite = new AlbumFavorite();
                favorite.setUserId(userId);
                favorite.setAlbumId(album.getId());
                albumFavoriteMapper.insert(favorite);

                initAlbumExtends(album.getId(), KeleConstant.FAVORITE);
            }
        }
    }

    @Override
    public void unDoFavorite(long userId, long albumId) {
        QueryWrapper<AlbumFavorite> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(AlbumFavorite::getAlbumId, albumId)
                .eq(AlbumFavorite::getUserId, userId);
        albumFavoriteMapper.delete(wrapper);
    }

    @Override
    public void batchUnDoFavorite(long userId, List<Long> ids) {
        QueryWrapper<AlbumFavorite> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(AlbumFavorite::getUserId, userId)
                .in(AlbumFavorite::getAlbumId, ids);
        albumFavoriteMapper.delete(wrapper);
    }

    @Override
    public Album view(Album album) {
        if (album == null) return new Album();

        if (album.getId() == null || album.getId() == 0) {
            album = insertOrUpdate(album);
        }
        initAlbumExtends(album.getId(), KeleConstant.VIEW);

        return album;
    }

    @Override
    public void play(Album album) {
        if (album == null) return;

        if (album.getId() == null || album.getId() == 0) {
            album = insertOrUpdate(album);
        }
        initAlbumExtends(album.getId(), KeleConstant.PLAY);
    }

    @Override
    public boolean isFavorite(long userId, String source, String mid) {
        QueryWrapper<Album> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Album::getSource, source)
                .eq(Album::getMid, mid);

        Album album = albumMapper.selectOne(wrapper);

        if (album == null) {
            return false;
        }

        QueryWrapper<AlbumFavorite> favoriteQueryWrapper = new QueryWrapper<>();
        favoriteQueryWrapper.lambda()
                .eq(AlbumFavorite::getAlbumId, album.getId())
                .eq(AlbumFavorite::getUserId, userId);

        long count = albumFavoriteMapper.selectCount(favoriteQueryWrapper);

        return count > 0L;
    }

    private void initAlbumExtends(long albumId, String type) {
        QueryWrapper<AlbumExtends> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(AlbumExtends::getAlbumId, albumId);
        AlbumExtends ext = albumExtendsMapper.selectOne(wrapper);

        if (ext == null) {
            ext = new AlbumExtends();
            ext.setAlbumId(albumId);
            ext.setFavorites(type.equals(KeleConstant.FAVORITE) ? 1 : 0);
            ext.setViews(type.equals(KeleConstant.VIEW) ? 1 : 0);
            ext.setPlays(type.equals(KeleConstant.PLAY) ? 1 : 0);
            albumExtendsMapper.insert(ext);
        } else {
            switch (type) {
                case KeleConstant.FAVORITE:
                    ext.setFavorites(ext.getFavorites() + 1);
                    break;
                case KeleConstant.VIEW:
                    ext.setViews(ext.getViews() + 1);
                    break;
                case KeleConstant.PLAY:
                    ext.setPlays(ext.getPlays() + 1);
                    break;
                default:
                    break;
            }
            albumExtendsMapper.updateById(ext);
        }
    }
}
