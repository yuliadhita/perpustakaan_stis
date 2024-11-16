package com.polstat.perpustakaan.controller;

import com.polstat.perpustakaan.entity.Buku;
import com.polstat.perpustakaan.entity.Mahasiswa;
import com.polstat.perpustakaan.entity.PeminjamanBuku;
import com.polstat.perpustakaan.repository.BukuRepository;
import com.polstat.perpustakaan.repository.MahasiswaRepository;
import com.polstat.perpustakaan.repository.PeminjamanBukuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class PeminjamanBukuGraphqlController {

    @Autowired
    private PeminjamanBukuRepository peminjamanBukuRepository;

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @Autowired
    private BukuRepository bukuRepository;

    // Query to get all borrowing records
    @QueryMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public List<PeminjamanBuku> getAllPeminjamanBuku() {
        return (List<PeminjamanBuku>) peminjamanBukuRepository.findAll();
    }

    // Query to find borrowing records by student's NIM
    @QueryMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public List<PeminjamanBuku> getPeminjamanByNim(@Argument String nim) {
        return peminjamanBukuRepository.findByMahasiswaNim(nim);
    }

    // Query to find borrowing records by book ID
    @QueryMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public List<PeminjamanBuku> getPeminjamanByIdBuku(@Argument String id) {
        return peminjamanBukuRepository.findByBukuId(id);
    }

    // Query to find borrowing records by status
    @QueryMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public List<PeminjamanBuku> getPeminjamanByStatus(@Argument String statusPeminjaman) {
        return peminjamanBukuRepository.findByStatusPeminjamanIgnoreCase(statusPeminjaman);
    }

    // Mutation to create a new borrowing record
    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public PeminjamanBuku borrowBook(
            @Argument String nim,
            @Argument List<String> idBukuList // Use List instead of Set
    ) {
        Optional<Mahasiswa> mahasiswaOpt = mahasiswaRepository.findById(nim);
        if (mahasiswaOpt.isPresent() && !idBukuList.isEmpty()) {
            List<Buku> bukuList = (List<Buku>) bukuRepository.findAllById(idBukuList); // This already returns a List
            if (!bukuList.isEmpty()) {
                PeminjamanBuku peminjamanBuku = new PeminjamanBuku();
                peminjamanBuku.setMahasiswa(mahasiswaOpt.get());
                peminjamanBuku.setBuku(bukuList); // Directly assign the List<Buku>
                peminjamanBuku.setTglPinjam(LocalDate.now());
                peminjamanBuku.setStatusPeminjaman("SEDANG_DIPINJAM");
                return peminjamanBukuRepository.save(peminjamanBuku);
            }
        }
        return null; // Return null if student or books not found
    }

    // Mutation to return borrowed books
    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public Boolean returnBook(@Argument Long id) {
        Optional<PeminjamanBuku> peminjamanBukuOpt = peminjamanBukuRepository.findById(id);
        if (peminjamanBukuOpt.isPresent()) {
            PeminjamanBuku peminjamanBuku = peminjamanBukuOpt.get();
            peminjamanBuku.setTglKembali(LocalDate.now());
            peminjamanBuku.setStatusPeminjaman("SUDAH_DIKEMBALIKAN");

            // Hitung keterlambatan jika pengembalian melebihi batas waktu (misalnya, 7 hari)
            if (peminjamanBuku.getTglKembali().isAfter(peminjamanBuku.getTglPinjam().plusDays(7))) {
                long daysLate = peminjamanBuku.getTglKembali().toEpochDay() - peminjamanBuku.getTglPinjam().plusDays(7).toEpochDay();
                peminjamanBuku.setTerlambat((int) daysLate);
            } else {
                peminjamanBuku.setTerlambat(0);
            }

            peminjamanBukuRepository.save(peminjamanBuku);
            return true;  // Mengembalikan true jika buku berhasil dikembalikan
        }
        return false;  // Mengembalikan false jika peminjaman buku tidak ditemukan
    }


    // Mutation to delete a borrowing record by ID
    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public Boolean deletePeminjamanBuku(@Argument Long id) {
        if (peminjamanBukuRepository.existsById(id)) {
            peminjamanBukuRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
