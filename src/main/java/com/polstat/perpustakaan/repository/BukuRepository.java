package com.polstat.perpustakaan.repository;

import com.polstat.perpustakaan.entity.Buku;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "buku",path = "buku")
public interface BukuRepository extends PagingAndSortingRepository<Buku, String>, CrudRepository<Buku,String> {
    Optional<Buku> findById(@Param("id") String id);
    List<Buku> findByJudul(@Param("judul") String judul);
    List<Buku> findByPenulis(@Param("penulis") String penulis);
}
