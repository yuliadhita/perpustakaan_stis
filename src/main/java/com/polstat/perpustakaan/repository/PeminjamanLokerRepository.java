package com.polstat.perpustakaan.repository;

import com.polstat.perpustakaan.entity.PeminjamanLoker;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "peminjamanLoker", path = "peminjamanLoker")
public interface PeminjamanLokerRepository extends PagingAndSortingRepository<PeminjamanLoker, Long>, CrudRepository<PeminjamanLoker, Long> {

    // Find by borrowing status
    List<PeminjamanLoker> findByStatusPeminjaman(@Param("statusPeminjaman") String statusPeminjaman);

    // Find by student's NIM
    List<PeminjamanLoker> findByMahasiswaNim(@Param("nim") String nim);

    // Find by locker ID
    List<PeminjamanLoker> findByLokerId(@Param("id") String id);
}
