package com.whistleblowermanagerbe.repo;

import com.whistleblowermanagerbe.model.RichiestaIdentita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RichiestaIdentitaRepository extends JpaRepository<RichiestaIdentita, Integer> {
}
