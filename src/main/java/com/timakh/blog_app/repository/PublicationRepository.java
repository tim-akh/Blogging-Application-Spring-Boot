package com.timakh.blog_app.repository;

import com.timakh.blog_app.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
    Optional<Publication> findById(Long id);

}
