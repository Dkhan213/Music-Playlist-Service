package music.playlist.service.lambda;

import music.playlist.service.activity.GetPlaylistSongsActivity;
import music.playlist.service.dependency.DaggerServiceComponent;
import music.playlist.service.dependency.ServiceComponent;
import music.playlist.service.models.requests.GetPlaylistSongsRequest;
import music.playlist.service.models.results.GetPlaylistSongsResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetPlaylistSongsActivityProvider implements RequestHandler<GetPlaylistSongsRequest, GetPlaylistSongsResult> {

    public GetPlaylistSongsActivityProvider() {

    }

    @Override
    public GetPlaylistSongsResult handleRequest(final GetPlaylistSongsRequest getPlaylistSongsRequest, Context context) {
        return provideGetPlaylistSongsActivity().handleRequest(getPlaylistSongsRequest, context);
    }

    private GetPlaylistSongsActivity provideGetPlaylistSongsActivity() {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideGetPlaylistSongsActivity();
    }
}
