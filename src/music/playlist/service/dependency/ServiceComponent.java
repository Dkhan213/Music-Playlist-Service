package music.playlist.service.dependency;


import com.amazon.ata.music.playlist.service.activity.*;
import music.playlist.service.activity.*;
import music.playlist.service.dynamodb.AlbumTrackDao;
import music.playlist.service.dynamodb.PlaylistDao;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DaoModule.class})
public interface ServiceComponent {

    CreatePlaylistActivity provideCreatePlaylistActivity();

    GetPlaylistActivity provideGetPlaylistActivity();

    UpdatePlaylistActivity provideUpdatePlaylistActivity();

    AddSongToPlaylistActivity provideAddSongToPlaylistActivity();

    GetPlaylistSongsActivity provideGetPlaylistSongsActivity();

    PlaylistDao providePlaylistDao();

    AlbumTrackDao provideAlbumTrackDao();
}
