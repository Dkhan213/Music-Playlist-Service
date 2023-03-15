package music.playlist.service.converters;

import music.playlist.service.dynamodb.models.AlbumTrack;
import music.playlist.service.dynamodb.models.Playlist;
import music.playlist.service.models.PlaylistModel;
import music.playlist.service.models.SongModel;
import com.beust.jcommander.internal.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModelConverter {
    /**
     * Converts a provided {@link Playlist} into a {@link PlaylistModel} representation.
     * @param playlist the playlist to convert
     * @return the converted playlist
     */
    public PlaylistModel toPlaylistModel(Playlist playlist) {
        List<String> tags;
        if (playlist.getTags() == null) {
            tags = Collections.emptyList();
        } else {
            tags = Lists.newArrayList(playlist.getTags());
        }
        return PlaylistModel.builder()
            .withId(playlist.getId())
            .withName(playlist.getName())
            .withSongCount(playlist.getSongCount())
            .withTags(tags)
            .withCustomerId(playlist.getCustomerId())
            .build();
    }

    public List<SongModel> toSongModelList(List<AlbumTrack> albumTracks) {
        List<SongModel> songModelList = new ArrayList<>();
        for (AlbumTrack albumTrack : albumTracks) {
            SongModel songModel = SongModel.builder()
                    .withAsin(albumTrack.getAsin())
                    .withAlbum(albumTrack.getAlbumName())
                    .withTitle(albumTrack.getSongTitle())
                    .withTrackNumber(albumTrack.getTrackNumber())
                    .build();
            songModelList.add(songModel);
        }
        return songModelList;
    }
}
