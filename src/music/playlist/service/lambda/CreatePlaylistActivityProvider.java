package music.playlist.service.lambda;

import music.playlist.service.activity.CreatePlaylistActivity;
import music.playlist.service.dependency.DaggerServiceComponent;
import music.playlist.service.dependency.ServiceComponent;
import music.playlist.service.models.requests.CreatePlaylistRequest;
import music.playlist.service.models.results.CreatePlaylistResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CreatePlaylistActivityProvider implements RequestHandler<CreatePlaylistRequest, CreatePlaylistResult> {

    public CreatePlaylistActivityProvider() {

    }

    @Override
    public CreatePlaylistResult handleRequest(final CreatePlaylistRequest createPlaylistRequest, Context context) {
        return provideCreatePlaylistActivity().handleRequest(createPlaylistRequest, context);
    }

    private CreatePlaylistActivity provideCreatePlaylistActivity() {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideCreatePlaylistActivity();
    }
}
