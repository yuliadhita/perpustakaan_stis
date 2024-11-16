package com.polstat.perpustakaan.repository;

import com.polstat.perpustakaan.entity.Loker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "loker", path = "loker")
public interface LokerRepository extends PagingAndSortingRepository<Loker,String>, CrudRepository<Loker, String> {
    Optional<Loker> findById(@Param("id") String id); // Correct method name
    List<Loker> findByNoLoker(@Param("noLoker") Integer noLoker); // Correct method name
}
