package music.playlist.service.lambda;

import music.playlist.service.activity.UpdatePlaylistActivity;
import music.playlist.service.dependency.DaggerServiceComponent;
import music.playlist.service.dependency.ServiceComponent;
import music.playlist.service.models.requests.UpdatePlaylistRequest;
import music.playlist.service.models.results.UpdatePlaylistResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class UpdatePlaylistActivityProvider implements RequestHandler<UpdatePlaylistRequest, UpdatePlaylistResult> {

    public UpdatePlaylistActivityProvider() {

    }

    @Override
    public UpdatePlaylistResult handleRequest(final UpdatePlaylistRequest updatePlaylistRequest, Context context) {
        return provideUpdatePlaylistActivity().handleRequest(updatePlaylistRequest, context);
    }

    private UpdatePlaylistActivity provideUpdatePlaylistActivity() {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideUpdatePlaylistActivity();
    }
}
