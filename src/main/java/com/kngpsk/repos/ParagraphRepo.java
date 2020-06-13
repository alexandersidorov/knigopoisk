package com.kngpsk.repos;

import com.kngpsk.domain.News;
import com.kngpsk.domain.Paragraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParagraphRepo extends JpaRepository<Paragraph,Long> {
    List<Paragraph> findByNews(News news);
}
