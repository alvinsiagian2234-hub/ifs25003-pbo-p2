#!/usr/bin/env bash
# Compile setiap proyek lalu jalankan semua test case (test-cases/TC-*.tc).
# Sebuah test lulus jika seluruh baris di TC-*.expected muncul berurutan pada output program.
#
# Pemakaian: ./run-tests.sh
set -u

ROOT="$(cd "$(dirname "$0")" && pwd)"
passed=0
failed=0

for project in "$ROOT"/*/; do
    [ -d "${project}src" ] || continue
    name="$(basename "$project")"
    out="$(mktemp -d)"

    if ! javac -d "$out" -sourcepath "${project}src" "${project}src/App.java"; then
        echo "[FAIL] $name: gagal compile"
        failed=$((failed + 1))
        continue
    fi

    for tc in "${project}"test-cases/TC-*.tc; do
        [ -e "$tc" ] || continue
        expected="${tc%.tc}.expected"

        result="$(java -cp "$out" App < "$tc" | awk -v expected_file="$expected" '
            # Baris expected dikelompokkan: header ("...:" / "Hasil Pencarian") beserta baris data
            # di bawahnya harus muncul berurutan rapat (agar urutan hasil sort benar-benar diperiksa);
            # pesan lain ("Berhasil...", "[!]...", "Saldo...") cukup muncul berurutan.
            function is_header(l) { return l ~ /:$/ || l ~ /^Hasil Pencarian/ }
            function is_message(l) { return l ~ /^(Berhasil|\[!\]|Saldo|- )/ || is_header(l) }
            # Prompt tidak diakhiri newline, jadi output bisa diawali "label : "
            function same(actual, wanted,   suffix) {
                suffix = " : " wanted
                return actual == wanted || (length(actual) >= length(suffix) && substr(actual, length(actual) - length(suffix) + 1) == suffix)
            }
            BEGIN {
                while ((getline line < expected_file) > 0) {
                    total++
                    wanted[total] = line
                    if (is_message(line) || !in_header) { group++; in_header = is_header(line) }
                    group_of[total] = group
                }
            }
            { output[++count] = $0 }
            END {
                position = 1
                i = 1
                while (i <= total) {
                    j = i
                    while (j < total && group_of[j + 1] == group_of[i]) j++
                    found = 0
                    for (start = position; start + (j - i) <= count && !found; start++) {
                        found = 1
                        for (k = i; k <= j; k++) if (!same(output[start + k - i], wanted[k])) { found = 0; break }
                        if (found) position = start + (j - i) + 1
                    }
                    if (!found) { print "gagal di baris expected ke-" i ": " wanted[i]; exit }
                    i = j + 1
                }
                print "ok"
            }
        ')"

        if [ "$result" = "ok" ]; then
            echo "[PASS] $name/$(basename "$tc")"
            passed=$((passed + 1))
        else
            echo "[FAIL] $name/$(basename "$tc") ($result)"
            failed=$((failed + 1))
        fi
    done

    rm -rf "$out"
done

echo
echo "Hasil: $passed lulus, $failed gagal"
[ "$failed" -eq 0 ]
