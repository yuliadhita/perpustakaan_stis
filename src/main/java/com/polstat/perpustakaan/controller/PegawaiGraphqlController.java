package com.polstat.perpustakaan.controller;

import com.polstat.perpustakaan.entity.Pegawai;
import com.polstat.perpustakaan.repository.PegawaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PegawaiGraphqlController {

    @Autowired
    private PegawaiRepository pegawaiRepository;

    // Query to get all employees
    @QueryMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Pegawai> getAllPegawais() {
        return (List<Pegawai>) pegawaiRepository.findAll();
    }

    // Query to get employee by NIP
    @QueryMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Pegawai getPegawaiByNip(@Argument String nip) {
        return pegawaiRepository.findByNip(nip).stream().findFirst().orElse(null); // Updated method name
    }

    // Query to get employee by Name
    @QueryMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Pegawai> getPegawaiByNama(@Argument String nama) {
        return pegawaiRepository.findByNama(nama); // Updated method name
    }

    // Mutation to add a new employee
    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Pegawai addPegawai(
            @Argument String nip,
            @Argument String nama,
            @Argument String email,
            @Argument String alamat,
            @Argument String noHp
    ) {
        Pegawai pegawai = new Pegawai();
        pegawai.setNip(nip);
        pegawai.setNama(nama);
        pegawai.setEmail(email);
        pegawai.setAlamat(alamat);
        pegawai.setNoHp(noHp);
        return pegawaiRepository.save(pegawai);
    }

    // Mutation to update an existing employee
    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Pegawai updatePegawai(
            @Argument String nip,
            @Argument String nama,
            @Argument String email,
            @Argument String alamat,
            @Argument String noHp
    ) {
        Pegawai pegawai = pegawaiRepository.findByNip(nip).stream().findFirst().orElse(null); // Updated method name
        if (pegawai != null) {
            pegawai.setNama(nama);
            pegawai.setEmail(email);
            pegawai.setAlamat(alamat);
            pegawai.setNoHp(noHp);
            return pegawaiRepository.save(pegawai);
        }
        return null;
    }

    // Mutation to delete an employee
    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Boolean deletePegawai(@Argument String nip) {
        Pegawai pegawai = pegawaiRepository.findByNip(nip).stream().findFirst().orElse(null); // Updated method name
        if (pegawai != null) {
            pegawaiRepository.delete(pegawai);
            return true;
        }
        return false;
    }
}
