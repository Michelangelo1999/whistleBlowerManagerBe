package com.whistleblowermanagerbe.repo;

import com.whistleblowermanagerbe.model.ChatAsincrona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<ChatAsincrona, Integer> {
    @Query(value = "select * from chat_asincrona where fk_info_segnalazione = :idInfoSegnalazione", nativeQuery = true)
    Optional<ChatAsincrona> findByInfoSegnalazione(Integer idInfoSegnalazione);
}
