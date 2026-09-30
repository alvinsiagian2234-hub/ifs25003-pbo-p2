import adapter.presenter.GuestPresenter;
import adapter.repository.GuestRepository;
import domain.repository.IGuestRepository;
import framework.view.GuestView;
import usecase.GuestUseCase;

/**
 * Composition Root aplikasi Buku Tamu.
 * Satu-satunya tempat yang mengenal implementasi konkret dari setiap layer,
 * bertugas melakukan wiring dependency (Dependency Injection manual).
 */
public class App {
    public static void main(String[] args) {
        IGuestRepository guestRepository = new GuestRepository();
        GuestUseCase guestUseCase = new GuestUseCase(guestRepository);
        GuestPresenter presenter = new GuestPresenter();
        GuestView view = new GuestView(guestUseCase, presenter);

        view.show();
    }
}
