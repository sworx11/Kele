package me.wemeet.kele.entity.dto;

import lombok.Getter;
import lombok.Setter;
import me.wemeet.kele.entity.Playlist;
import me.wemeet.kele.entity.Song;

import java.util.List;

@Getter
@Setter
public class PlaylistDTO {
    private Playlist playlist;

    private List<Song> songs;
}
