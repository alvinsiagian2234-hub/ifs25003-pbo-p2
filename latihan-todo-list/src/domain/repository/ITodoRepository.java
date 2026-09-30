package domain.repository;

import domain.entity.Todo;
import java.util.List;
import java.util.Optional;

/**
 * Port (kontrak) penyimpanan data todo.
 * Interface ini berada di domain agar use case tidak bergantung pada
 * implementasi konkret maupun struktur data yang dipakai untuk menyimpan.
 */
public interface ITodoRepository {
    /** Mengambil semua data todo dari penyimpanan. */
    List<Todo> findAll();

    /** Mencari satu todo berdasarkan ID. Mengembalikan empty jika tidak ditemukan. */
    Optional<Todo> findById(int id);

    /**
     * Menyimpan todo baru. Implementasi bertanggung jawab memberi ID unik.
     *
     * @param title judul todo yang akan disimpan
     * @return todo yang tersimpan (lengkap dengan ID)
     */
    Todo save(String title);

    /** Menghapus todo berdasarkan ID. Mengembalikan true jika berhasil. */
    boolean deleteById(int id);

    /**
     * Menyimpan perubahan pada todo yang sudah ada.
     * Diperlukan agar kontrak tetap benar untuk implementasi yang butuh
     * operasi persist eksplisit (misalnya database).
     */
    void update(Todo todo);
}
