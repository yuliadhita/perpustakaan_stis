package com.polstat.perpustakaan.repository;

import com.polstat.perpustakaan.entity.Pegawai;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "pegawai", path = "pegawai")
public interface PegawaiRepository extends PagingAndSortingRepository<Pegawai, String>, CrudRepository<Pegawai, String> {
    List<Pegawai> findByNama(@Param("nama") String nama);  // Updated to follow JPA convention
    List<Pegawai> findByNip(@Param("nip") String nip);    // Updated to follow JPA convention
}
