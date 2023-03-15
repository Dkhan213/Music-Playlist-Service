package music.playlist.service.activity;

import music.playlist.service.converters.ModelConverter;
import music.playlist.service.dynamodb.AlbumTrackDao;
import music.playlist.service.dynamodb.PlaylistDao;
import music.playlist.service.dynamodb.models.AlbumTrack;
import music.playlist.service.dynamodb.models.Playlist;
import music.playlist.service.exceptions.AlbumTrackNotFoundException;
import music.playlist.service.exceptions.PlaylistNotFoundException;
import music.playlist.service.models.SongModel;
import music.playlist.service.models.requests.AddSongToPlaylistRequest;
import music.playlist.service.models.results.AddSongToPlaylistResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of the AddSongToPlaylistActivity for the MusicPlaylistService's AddSongToPlaylist API.
 *
 * This API allows the customer to add a song to their existing playlist.
 */
public class AddSongToPlaylistActivity implements RequestHandler<AddSongToPlaylistRequest, AddSongToPlaylistResult> {
    private final Logger log = LogManager.getLogger();
    private final PlaylistDao playlistDao;
    private final AlbumTrackDao albumTrackDao;

    /**
     * Instantiates a new AddSongToPlaylistActivity object.
     *
     * @param playlistDao PlaylistDao to access the playlist table.
     * @param albumTrackDao AlbumTrackDao to access the album_track table.
     */
    @Inject
    public AddSongToPlaylistActivity(PlaylistDao playlistDao, AlbumTrackDao albumTrackDao) {
        this.playlistDao = playlistDao;
        this.albumTrackDao = albumTrackDao;
    }

    /**
     * This method handles the incoming request by adding an additional song
     * to a playlist and persisting the updated playlist.
     * <p>
     * It then returns the updated song list of the playlist.
     * <p>
     * If the playlist does not exist, this should throw a PlaylistNotFoundException.
     * <p>
     * If the album track does not exist, this should throw an AlbumTrackNotFoundException.
     *
     * @param addSongToPlaylistRequest request object containing the playlist ID and an asin and track number
     *                                 to retrieve the song data
     * @return addSongToPlaylistResult result object containing the playlist's updated list of
     *                                 API defined {@link SongModel}s
     */
    @Override
    public AddSongToPlaylistResult handleRequest(final AddSongToPlaylistRequest addSongToPlaylistRequest, Context context) {
        log.info("Received AddSongToPlaylistRequest {} ", addSongToPlaylistRequest);

        Playlist playlist = playlistDao.getPlaylist(addSongToPlaylistRequest.getId());
        if (playlist == null) {
            throw new PlaylistNotFoundException();
        }
        AlbumTrack albumTrack = albumTrackDao.getAlbumTrack(addSongToPlaylistRequest.getAsin(), addSongToPlaylistRequest.getTrackNumber());
        if (albumTrack == null) {
            throw new AlbumTrackNotFoundException();
        }

        List<AlbumTrack> songList = playlist.getSongList();
        if (addSongToPlaylistRequest.isQueueNext()) {
            songList.add(0, albumTrack);
        } else {
            songList.add(albumTrack);
        }
        playlist.setSongList(songList);
        playlistDao.savePlaylist(playlist);

        return AddSongToPlaylistResult.builder()
                .withSongList(new ModelConverter().toSongModelList(songList))
                .build();
    }
}
