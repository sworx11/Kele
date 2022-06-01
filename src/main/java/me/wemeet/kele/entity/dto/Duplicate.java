package me.wemeet.kele.entity.dto;

import lombok.Getter;
import lombok.Setter;
import me.wemeet.kele.entity.Album;
import me.wemeet.kele.entity.Playlist;
import me.wemeet.kele.entity.Song;

import java.util.List;

@Getter
@Setter
public class Duplicate {
    private List<Song> songs;
    private List<Playlist> playlists;
    private List<Album> albums;
}
