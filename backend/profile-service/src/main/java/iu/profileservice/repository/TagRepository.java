package iu.profileservice.repository;

import iu.profileservice.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {

    Set<Tag> findAllByNameIn(Collection<String> names);

    Optional<Tag> findByName(String name);

    Set<Tag> findAllByNameContainsIgnoreCase(String prefix);
}
