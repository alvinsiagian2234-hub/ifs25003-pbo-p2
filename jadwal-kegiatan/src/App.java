import adapter.presenter.ActivityPresenter;
import adapter.repository.ActivityRepository;
import domain.repository.IActivityRepository;
import framework.view.ActivityView;
import usecase.ActivityUseCase;

/**
 * Composition Root aplikasi Jadwal Kegiatan.
 * Satu-satunya tempat yang mengenal implementasi konkret dari setiap layer,
 * bertugas melakukan wiring dependency (Dependency Injection manual).
 */
public class App {
    public static void main(String[] args) {
        IActivityRepository activityRepository = new ActivityRepository();
        ActivityUseCase activityUseCase = new ActivityUseCase(activityRepository);
        ActivityPresenter presenter = new ActivityPresenter();
        ActivityView view = new ActivityView(activityUseCase, presenter);

        view.show();
    }
}
