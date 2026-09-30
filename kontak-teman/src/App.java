import adapter.presenter.ContactPresenter;
import adapter.repository.ContactRepository;
import domain.repository.IContactRepository;
import framework.view.ContactView;
import usecase.ContactUseCase;

/**
 * Composition Root aplikasi Kontak Teman.
 * Satu-satunya tempat yang mengenal implementasi konkret dari setiap layer,
 * bertugas melakukan wiring dependency (Dependency Injection manual).
 */
public class App {
    public static void main(String[] args) {
        IContactRepository contactRepository = new ContactRepository();
        ContactUseCase contactUseCase = new ContactUseCase(contactRepository);
        ContactPresenter presenter = new ContactPresenter();
        ContactView view = new ContactView(contactUseCase, presenter);

        view.show();
    }
}
