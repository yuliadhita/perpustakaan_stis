package com.polstat.perpustakaan.repository;

import com.polstat.perpustakaan.entity.PeminjamanBuku;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeminjamanBukuRepository extends PagingAndSortingRepository<PeminjamanBuku, Long>, CrudRepository<PeminjamanBuku, Long> {
    List<PeminjamanBuku> findByStatusPeminjamanIgnoreCase(@Param("statusPeminjaman") String statusPeminjaman);
    List<PeminjamanBuku> findByMahasiswaNim(@Param("nim") String nim);
    List<PeminjamanBuku> findByBukuId(@Param("id") String id);
}
