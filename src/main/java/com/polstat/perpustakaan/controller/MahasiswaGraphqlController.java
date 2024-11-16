package com.polstat.perpustakaan.controller;

import com.polstat.perpustakaan.entity.Mahasiswa;
import com.polstat.perpustakaan.repository.MahasiswaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MahasiswaGraphqlController {

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    // Query to get all students
    @QueryMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public List<Mahasiswa> getAllMahasiswas() {
        return (List<Mahasiswa>) mahasiswaRepository.findAll();
    }

    // Query to get student by NIM
    @QueryMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public Mahasiswa getMahasiswaByNim(@Argument String nim) {
        return mahasiswaRepository.findByNim(nim).stream().findFirst().orElse(null); // Updated method name
    }

    // Query to get student by Name
    @QueryMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public List<Mahasiswa> getMahasiswaByNama(@Argument String nama) {
        return mahasiswaRepository.findByNama(nama); // Updated method name
    }

    // Mutation to add a new student
    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public Mahasiswa addMahasiswa(
            @Argument String nim,
            @Argument String nama,
            @Argument String email,
            @Argument String alamat,
            @Argument String noHp
    ) {
        Mahasiswa mahasiswa = new Mahasiswa();
        mahasiswa.setNim(nim);
        mahasiswa.setNama(nama);
        mahasiswa.setEmail(email);
        mahasiswa.setAlamat(alamat);
        mahasiswa.setNoHp(noHp);
        return mahasiswaRepository.save(mahasiswa);
    }

    // Mutation to update an existing student
    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public Mahasiswa updateMahasiswa(
            @Argument String nim,
            @Argument String nama,
            @Argument String email,
            @Argument String alamat,
            @Argument String noHp
    ) {
        Mahasiswa mahasiswa = mahasiswaRepository.findByNim(nim).stream().findFirst().orElse(null);
        if (mahasiswa != null) {
            mahasiswa.setNama(nama);
            mahasiswa.setEmail(email);
            mahasiswa.setAlamat(alamat);
            mahasiswa.setNoHp(noHp);
            return mahasiswaRepository.save(mahasiswa);
        }
        return null;
    }


    // Mutation to delete a student
    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public Boolean deleteMahasiswa(@Argument String nim) {
        Mahasiswa mahasiswa = mahasiswaRepository.findByNim(nim).stream().findFirst().orElse(null); // Updated method name
        if (mahasiswa != null) {
            mahasiswaRepository.delete(mahasiswa);
            return true;
        }
        return false;
    }
}
