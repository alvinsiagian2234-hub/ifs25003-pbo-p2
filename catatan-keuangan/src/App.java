import adapter.presenter.FinancePresenter;
import adapter.repository.TransactionRepository;
import domain.repository.ITransactionRepository;
import framework.view.FinanceView;
import usecase.FinanceUseCase;

/**
 * Composition Root aplikasi Catatan Keuangan.
 * Satu-satunya tempat yang mengenal implementasi konkret dari setiap layer,
 * bertugas melakukan wiring dependency (Dependency Injection manual).
 */
public class App {
    public static void main(String[] args) {
        ITransactionRepository transactionRepository = new TransactionRepository();
        FinanceUseCase financeUseCase = new FinanceUseCase(transactionRepository);
        FinancePresenter presenter = new FinancePresenter();
        FinanceView view = new FinanceView(financeUseCase, presenter);

        view.show();
    }
}
