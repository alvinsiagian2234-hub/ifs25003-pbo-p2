package framework.util;

/**
 * Dilempar oleh {@link InputUtil} ketika input habis (EOF) sebelum user memilih keluar.
 * Ditangani secara seragam di setiap view agar program berhenti dengan pesan yang jelas,
 * bukan dengan stack trace mentah dari {@link java.util.Scanner}.
 */
public class InputClosedException extends RuntimeException {
    public InputClosedException() {
        super("Input berakhir sebelum program dihentikan.");
    }
}
