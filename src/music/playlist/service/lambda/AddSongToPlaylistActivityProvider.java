package music.playlist.service.lambda;

import music.playlist.service.activity.AddSongToPlaylistActivity;
import music.playlist.service.dependency.DaggerServiceComponent;
import music.playlist.service.dependency.ServiceComponent;
import music.playlist.service.models.requests.AddSongToPlaylistRequest;
import music.playlist.service.models.results.AddSongToPlaylistResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class AddSongToPlaylistActivityProvider implements RequestHandler<AddSongToPlaylistRequest, AddSongToPlaylistResult> {

    public AddSongToPlaylistActivityProvider() {

    }

    @Override
    public AddSongToPlaylistResult handleRequest(final AddSongToPlaylistRequest addSongToPlaylistRequest, Context context) {
        return provideAddSongToPlaylistActivity().handleRequest(addSongToPlaylistRequest, context);
    }

    private AddSongToPlaylistActivity provideAddSongToPlaylistActivity() {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideAddSongToPlaylistActivity();
    }
}
