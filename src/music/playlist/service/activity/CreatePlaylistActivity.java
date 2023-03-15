package music.playlist.service.activity;

import music.playlist.service.converters.ModelConverter;
import music.playlist.service.dynamodb.models.Playlist;
import music.playlist.service.exceptions.InvalidAttributeValueException;
import music.playlist.service.models.requests.CreatePlaylistRequest;
import music.playlist.service.models.results.CreatePlaylistResult;
import music.playlist.service.models.PlaylistModel;
import music.playlist.service.dynamodb.PlaylistDao;

import music.playlist.service.util.MusicPlaylistServiceUtils;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of the CreatePlaylistActivity for the MusicPlaylistService's CreatePlaylist API.
 *
 * This API allows the customer to create a new playlist with no songs.
 */
public class CreatePlaylistActivity implements RequestHandler<CreatePlaylistRequest, CreatePlaylistResult> {
    private final Logger log = LogManager.getLogger();
    private final PlaylistDao playlistDao;

    /**
     * Instantiates a new CreatePlaylistActivity object.
     *
     * @param playlistDao PlaylistDao to access the playlists table.
     */
    @Inject
    public CreatePlaylistActivity(PlaylistDao playlistDao) {
        this.playlistDao = playlistDao;
    }

    /**
     * This method handles the incoming request by persisting a new playlist
     * with the provided playlist name and customer ID from the request.
     * <p>
     * It then returns the newly created playlist.
     * <p>
     * If the provided playlist name or customer ID has invalid characters, throws an
     * InvalidAttributeValueException
     *
     * @param createPlaylistRequest request object containing the playlist name and customer ID
     *                              associated with it
     * @return createPlaylistResult result object containing the API defined {@link PlaylistModel}
     */
    @Override
    public CreatePlaylistResult handleRequest(final CreatePlaylistRequest createPlaylistRequest, Context context) {
        log.info("Received CreatePlaylistRequest {}", createPlaylistRequest);

        if (!MusicPlaylistServiceUtils.isValidString(createPlaylistRequest.getName()) || !MusicPlaylistServiceUtils.isValidString(createPlaylistRequest.getCustomerId())) {
            throw new InvalidAttributeValueException("Invalid characters in name or customer Id.");
        }

        String playlistId = MusicPlaylistServiceUtils.generatePlaylistId();

        Playlist playlist = new Playlist();
        playlist.setCustomerId(createPlaylistRequest.getCustomerId());
        playlist.setName(createPlaylistRequest.getName());
        if (createPlaylistRequest.getTags() == null || createPlaylistRequest.getTags().size() < 1) {
            playlist.setTags(null);
        } else {
            Set<String> tags = new HashSet<>(createPlaylistRequest.getTags());
            playlist.setTags(tags);
        }
        playlist.setId(playlistId);
        playlist.setSongList(Collections.emptyList());
        playlist.setSongCount(0);
        playlistDao.savePlaylist(playlist);
        PlaylistModel playlistModel = new ModelConverter().toPlaylistModel(playlist);

        return CreatePlaylistResult.builder()
                .withPlaylist(playlistModel)
                .build();
    }
}
