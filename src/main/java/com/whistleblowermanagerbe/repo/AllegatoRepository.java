package com.whistleblowermanagerbe.repo;

import com.whistleblowermanagerbe.model.Allegato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllegatoRepository extends JpaRepository<Allegato, Integer> {

    @Query(value = "select * from allegato where fk_info_segnalazione = :idInfo", nativeQuery = true)
    List<Allegato> findAllByInfoId(Integer idInfo);
}
