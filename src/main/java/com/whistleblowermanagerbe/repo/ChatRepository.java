package com.whistleblowermanagerbe.repo;

import com.whistleblowermanagerbe.model.ChatAsincrona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<ChatAsincrona, Integer> {
}
