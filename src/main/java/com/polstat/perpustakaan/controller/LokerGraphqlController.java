package com.polstat.perpustakaan.controller;

import com.polstat.perpustakaan.entity.Loker;
import com.polstat.perpustakaan.repository.LokerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class LokerGraphqlController {

    @Autowired
    private LokerRepository lokerRepository;

    // Query to get all lockers
    @QueryMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MAHASISWA', 'ROLE_PEGAWAI')")
    public List<Loker> getAllLokers() {
        return (List<Loker>) lokerRepository.findAll();
    }

    // Query to get a locker by ID
    @QueryMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public Loker getLokerById(@Argument String id) {
        return lokerRepository.findById(id).stream().findFirst().orElse(null); // Updated method name
    }

    // Mutation to add a new locker
    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public Loker addLoker(
            @Argument String id,
            @Argument Integer noLoker
    ) {
        Loker loker = new Loker();
        loker.setId(id);
        loker.setNoLoker(noLoker);
        return lokerRepository.save(loker);
    }

    // Mutation to update an existing locker
    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public Loker updateLoker(
            @Argument String id,
            @Argument Integer noLoker
    ) {
        Loker loker = lokerRepository.findById(id).stream().findFirst().orElse(null); // Updated method name
        if (loker != null) {
            loker.setNoLoker(noLoker);
            return lokerRepository.save(loker);
        }
        return null;
    }

    // Mutation to delete a locker
    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PEGAWAI')")
    public Boolean deleteLoker(@Argument String id) {
        Loker loker = lokerRepository.findById(id).stream().findFirst().orElse(null); // Updated method name
        if (loker != null) {
            lokerRepository.delete(loker);
            return true;
        }
        return false;
    }
}
