package music.playlist.service.lambda;

import music.playlist.service.activity.GetPlaylistActivity;
import music.playlist.service.dependency.DaggerServiceComponent;
import music.playlist.service.dependency.ServiceComponent;
import music.playlist.service.models.requests.GetPlaylistRequest;
import music.playlist.service.models.results.GetPlaylistResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetPlaylistActivityProvider implements RequestHandler<GetPlaylistRequest, GetPlaylistResult> {

    public GetPlaylistActivityProvider() {

    }

    @Override
    public GetPlaylistResult handleRequest(final GetPlaylistRequest getPlaylistRequest, Context context) {
        return provideGetPlaylistActivity().handleRequest(getPlaylistRequest, context);
    }

    private GetPlaylistActivity provideGetPlaylistActivity() {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideGetPlaylistActivity();
    }
}
