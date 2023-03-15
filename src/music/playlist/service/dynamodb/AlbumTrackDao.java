package music.playlist.service.dynamodb;

import music.playlist.service.dynamodb.models.AlbumTrack;
import music.playlist.service.exceptions.AlbumTrackNotFoundException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;

/**
 * Accesses data for an album using {@link AlbumTrack} to represent the model in DynamoDB.
 */
public class AlbumTrackDao {
    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates an AlbumTrackDao object.
     *
     * @param dynamoDbMapper the {@link DynamoDBMapper} used to interact with the album_track table
     */
    @Inject
    public AlbumTrackDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    public AlbumTrack getAlbumTrack(String asin, int trackNumber) {
        AlbumTrack albumTrack = this.dynamoDbMapper.load(AlbumTrack.class, asin);

        if (albumTrack == null) {
            throw new AlbumTrackNotFoundException("Could not find album track with asin " + asin);
        }

        return albumTrack;
    }
}
