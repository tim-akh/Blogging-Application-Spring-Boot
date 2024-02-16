package com.timakh.blog_app.repository;

import com.timakh.blog_app.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
    Publication findPublicationById(Long id);

}
