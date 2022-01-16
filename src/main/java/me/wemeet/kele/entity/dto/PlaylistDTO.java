package me.wemeet.kele.entity.dto;

import lombok.Getter;
import lombok.Setter;
import me.wemeet.kele.entity.Playlist;
import me.wemeet.kele.entity.PlaylistExtends;
import me.wemeet.kele.entity.Song;
import me.wemeet.kele.entity.User;

import java.util.List;

@Getter
@Setter
public class PlaylistDTO {
    private Playlist playlist;
    private PlaylistExtends ext;
    private List<Song> songs;
    private User user;
}
