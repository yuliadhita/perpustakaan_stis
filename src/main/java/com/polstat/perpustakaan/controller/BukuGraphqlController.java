package com.polstat.perpustakaan.controller;

import com.polstat.perpustakaan.entity.Buku;
import com.polstat.perpustakaan.repository.BukuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BukuGraphqlController {

    @Autowired
    private BukuRepository bukuRepository;

    /**
     * Query untuk mendapatkan semua buku.
     *
     * **Authorization:** Hanya pengguna dengan peran `ROLE_ADMIN`, `ROLE_MAHASISWA`, atau `ROLE_PEGAWAI` yang dapat mengakses.
     *
     * @return Daftar semua buku yang ada di database.
     */
    @QueryMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MAHASISWA', 'ROLE_PEGAWAI')")
    public List<Buku> getAllBuku() {
        return (List<Buku>) bukuRepository.findAll();
    }

    /**
     * Query untuk mendapatkan buku berdasarkan ID.
     *
     * **Authorization:** Hanya pengguna dengan peran `ROLE_ADMIN` atau `ROLE_PEGAWAI` yang dapat mengakses.
     *
     * @param id ID buku.
     * @return Buku yang sesuai dengan ID yang diberikan atau `null` jika tidak ditemukan.
     */
    @QueryMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public Buku getBukuById(@Argument String id) {
        return bukuRepository.findById(id).stream().findFirst().orElse(null);
    }

    /**
     * Query untuk mendapatkan buku berdasarkan judul.
     *
     * **Authorization:** Hanya pengguna dengan peran `ROLE_ADMIN`, `ROLE_MAHASISWA`, atau `ROLE_PEGAWAI` yang dapat mengakses.
     *
     * @param judul Judul buku.
     * @return Daftar buku yang memiliki judul yang sesuai.
     */
    @QueryMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MAHASISWA', 'ROLE_PEGAWAI')")
    public List<Buku> getBukuByJudul(@Argument String judul) {
        return bukuRepository.findByJudul(judul);
    }

    /**
     * Query untuk mendapatkan buku berdasarkan penulis.
     *
     * **Authorization:** Hanya pengguna dengan peran `ROLE_ADMIN`, `ROLE_MAHASISWA`, atau `ROLE_PEGAWAI` yang dapat mengakses.
     *
     * @param penulis Nama penulis buku.
     * @return Daftar buku yang ditulis oleh penulis yang sesuai.
     */
    @QueryMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MAHASISWA', 'ROLE_PEGAWAI')")
    public List<Buku> getBukuByPenulis(@Argument String penulis) {
        return bukuRepository.findByPenulis(penulis);
    }

    /**
     * Mutasi untuk menambahkan buku baru.
     *
     * **Authorization:** Hanya pengguna dengan peran `ROLE_ADMIN` atau `ROLE_PEGAWAI` yang dapat mengakses.
     *
     * @param id ID buku.
     * @param judul Judul buku.
     * @param penulis Nama penulis buku.
     * @param tahun_terbit Tahun terbit buku.
     * @param deskripsi Deskripsi buku.
     * @param jumlah Jumlah buku yang tersedia.
     * @return Buku yang baru ditambahkan.
     */
    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PEGAWAI')")
    public Buku addBuku(
            @Argument String id,
            @Argument String judul,
            @Argument String penulis,
            @Argument Integer tahun_terbit,
            @Argument String deskripsi,
            @Argument Integer jumlah
    ) {
        Buku buku = new Buku();
        buku.setId(id);
        buku.setJudul(judul);
        buku.setPenulis(penulis);
        buku.setTahun_terbit(tahun_terbit);
        buku.setDeskripsi(deskripsi);
        buku.setJumlah(jumlah);

        return bukuRepository.save(buku);
    }

    /**
     * Mutasi untuk memperbarui informasi buku yang sudah ada.
     *
     * **Authorization:** Hanya pengguna dengan peran `ROLE_ADMIN` atau `ROLE_PEGAWAI` yang dapat mengakses.
     *
     * @param id ID buku.
     * @param judul Judul buku yang baru.
     * @param penulis Nama penulis yang baru.
     * @param tahun_terbit Tahun terbit yang baru.
     * @param deskripsi Deskripsi yang baru.
     * @param jumlah Jumlah buku yang baru.
     * @return Buku yang diperbarui atau `null` jika buku tidak ditemukan.
     */
    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PEGAWAI')")
    public Buku updateBuku(
            @Argument String id,
            @Argument String judul,
            @Argument String penulis,
            @Argument Integer tahun_terbit,
            @Argument String deskripsi,
            @Argument Integer jumlah
    ) {
        Buku buku = bukuRepository.findById(id).stream().findFirst().orElse(null);
        if (buku != null) {
            buku.setJudul(judul);
            buku.setPenulis(penulis);
            buku.setTahun_terbit(tahun_terbit);
            buku.setDeskripsi(deskripsi);
            buku.setJumlah(jumlah);
            return bukuRepository.save(buku);
        }
        return null;
    }

    /**
     * Mutasi untuk menghapus buku berdasarkan ID.
     *
     * **Authorization:** Hanya pengguna dengan peran `ROLE_ADMIN` atau `ROLE_PEGAWAI` yang dapat mengakses.
     *
     * @param id ID buku yang akan dihapus.
     * @return `true` jika buku berhasil dihapus, `false` jika buku tidak ditemukan.
     */
    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PEGAWAI')")
    public Boolean deleteBuku(@Argument String id) {
        Buku buku = bukuRepository.findById(id).stream().findFirst().orElse(null);
        if (buku != null) {
            bukuRepository.delete(buku);
            return true;
        }
        return false;
    }
}
