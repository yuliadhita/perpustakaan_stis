package com.polstat.perpustakaan.repository;

import com.polstat.perpustakaan.entity.Mahasiswa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "mahasiswa", path = "mahasiswa")
public interface MahasiswaRepository extends PagingAndSortingRepository<Mahasiswa, String>, CrudRepository<Mahasiswa, String> {
    List<Mahasiswa> findByNama(@Param("nama") String nama); // Updated to use JPA naming conventions
    List<Mahasiswa> findByNim(@Param("nim") String nim); // Updated to use JPA naming conventions
}
