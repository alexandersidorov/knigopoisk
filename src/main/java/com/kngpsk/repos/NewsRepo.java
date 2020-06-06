package com.kngpsk.repos;

import com.kngpsk.domain.News;
import com.kngpsk.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepo extends JpaRepository<News,Long> {
    List<News> findByAuthor(User author);
}
