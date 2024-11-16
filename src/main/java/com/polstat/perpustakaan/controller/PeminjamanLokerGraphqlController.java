package com.polstat.perpustakaan.controller;

import com.polstat.perpustakaan.entity.Loker;
import com.polstat.perpustakaan.entity.Mahasiswa;
import com.polstat.perpustakaan.entity.PeminjamanLoker;
import com.polstat.perpustakaan.repository.LokerRepository;
import com.polstat.perpustakaan.repository.MahasiswaRepository;
import com.polstat.perpustakaan.repository.PeminjamanLokerRepository;
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
public class PeminjamanLokerGraphqlController {

    @Autowired
    private PeminjamanLokerRepository peminjamanLokerRepository;

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @Autowired
    private LokerRepository lokerRepository;

    // Query to get all locker borrowing records
    @QueryMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public List<PeminjamanLoker> getAllPeminjamanLoker() {
        return (List<PeminjamanLoker>) peminjamanLokerRepository.findAll();
    }

    // Query to find locker borrowing records by student's NIM
    @QueryMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public List<PeminjamanLoker> getPeminjamanLokerByNim(@Argument String nim) {
        return peminjamanLokerRepository.findByMahasiswaNim(nim);
    }

    // Query to find locker borrowing records by locker ID
    @QueryMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public List<PeminjamanLoker> getPeminjamanLokerByIdLoker(@Argument String id) {
        return peminjamanLokerRepository.findByLokerId(id);
    }

    // Query to find locker borrowing records by status
    @QueryMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public List<PeminjamanLoker> getPeminjamanLokerByStatus(@Argument String statusPeminjaman) {
        return peminjamanLokerRepository.findByStatusPeminjaman(statusPeminjaman);
    }

    // Mutation to create a new locker borrowing record
    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public PeminjamanLoker borrowLocker(
            @Argument String nim,
            @Argument String idLoker
    ) {
        Optional<Mahasiswa> mahasiswaOpt = mahasiswaRepository.findById(nim);
        Optional<Loker> lokerOpt = lokerRepository.findById(idLoker);

        if (mahasiswaOpt.isPresent() && lokerOpt.isPresent()) {
            PeminjamanLoker peminjamanLoker = new PeminjamanLoker();
            peminjamanLoker.setMahasiswa(mahasiswaOpt.get());
            peminjamanLoker.setLoker(lokerOpt.get());
            peminjamanLoker.setTglPinjam(LocalDate.now());
            peminjamanLoker.setStatusPeminjaman("SEDANG_DIPINJAM");

            return peminjamanLokerRepository.save(peminjamanLoker);
        }
        return null; // Return null if student or locker not found
    }

    // Mutation to return locker
    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public Boolean returnLocker(@Argument Long id) {
        Optional<PeminjamanLoker> peminjamanLokerOpt = peminjamanLokerRepository.findById(id);
        if (peminjamanLokerOpt.isPresent()) {
            PeminjamanLoker peminjamanLoker = peminjamanLokerOpt.get();
            peminjamanLoker.setTglKembali(LocalDate.now());
            peminjamanLoker.setStatusPeminjaman("SUDAH_DIKEMBALIKAN");

            // Menghitung keterlambatan jika pengembalian lebih dari tanggal peminjaman
            if (peminjamanLoker.getTglKembali().isAfter(peminjamanLoker.getTglPinjam().plusDays(0))) {
                long daysLate = peminjamanLoker.getTglKembali().toEpochDay() - peminjamanLoker.getTglPinjam().plusDays(0).toEpochDay();
                peminjamanLoker.setTerlambat((int) daysLate);
            } else {
                peminjamanLoker.setTerlambat(0);
            }

            peminjamanLokerRepository.save(peminjamanLoker);
            return true;  // Mengembalikan true jika peminjaman berhasil dikembalikan
        }
        return false;  // Mengembalikan false jika peminjaman tidak ditemukan
    }


    // Mutation to delete a locker borrowing record by ID
    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public Boolean deletePeminjamanLoker(@Argument Long id) {
        if (peminjamanLokerRepository.existsById(id)) {
            peminjamanLokerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
