package me.wemeet.kele.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.wemeet.kele.entity.Album;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Quino Wu
 * @since 2022-03-26
 */
@Repository
public interface AlbumMapper extends BaseMapper<Album> {
    IPage<Album> listByFavorite(Page<Album> page, long userId);
}
