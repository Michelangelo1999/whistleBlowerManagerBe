package com.whistleblowermanagerbe.repo;

import com.whistleblowermanagerbe.model.Commento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentoRepository extends JpaRepository<Commento, Integer> {

}
