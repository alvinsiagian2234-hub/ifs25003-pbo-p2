import adapter.presenter.ItemPresenter;
import adapter.repository.ItemRepository;
import domain.repository.IItemRepository;
import framework.view.ItemView;
import usecase.ItemUseCase;

/**
 * Composition Root aplikasi Inventaris Barang.
 * Satu-satunya tempat yang mengenal implementasi konkret dari setiap layer,
 * bertugas melakukan wiring dependency (Dependency Injection manual).
 */
public class App {
    public static void main(String[] args) {
        IItemRepository itemRepository = new ItemRepository();
        ItemUseCase itemUseCase = new ItemUseCase(itemRepository);
        ItemPresenter presenter = new ItemPresenter();
        ItemView view = new ItemView(itemUseCase, presenter);

        view.show();
    }
}
