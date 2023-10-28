package com.whistleblowermanagerbe.repo;

import com.whistleblowermanagerbe.model.Messaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessaggioRepository extends JpaRepository<Messaggio, Integer> {
}
