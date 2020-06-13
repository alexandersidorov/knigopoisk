package com.kngpsk.services;

import com.kngpsk.domain.News;
import com.kngpsk.domain.Paragraph;
import com.kngpsk.domain.User;
import com.kngpsk.repos.NewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class NewsService {

    @Autowired
    NewsRepo newsRepo;

    @Autowired
    FileSaver fileSaver;

    public List<News> findAll(boolean reverse){
        List<News> newsList = newsRepo.findAll();
        if(reverse)Collections.reverse(newsList);
        return newsList;
    }

    public List<News> findAllByAuthor(User author, boolean reverse){
        List<News> newsList = newsRepo.findByAuthor(author);
        if(reverse)Collections.reverse(newsList);
        return newsList;
    }

    public boolean addNews(News news){
        newsRepo.save(news);
        return true;
    }

    public News addNews(User author, String head, String text, MultipartFile headPic/*, Set<Paragraph>paragraphs*/)throws IOException{
        News news = new News();
        news.setAuthor(author);
        news.setHead(head);
        news.setText(text);
        //news.setParagraphs(paragraphs);
        fileSaver.saveFile(news,headPic);

        addNews(news);

        return news;
    }

    public boolean updateNews(News news, String head, String text, MultipartFile headPic/*,Set<Paragraph>paragraphs*/) throws IOException {

        //обновление заголовка
        String headOld = news.getHead();
        boolean isHeadChanged = (head != null && !headOld.equals(head));
        if(isHeadChanged)news.setHead(head);

        //обновление текста
        String textOld = news.getText();
        boolean isTextChanged = (text != null && !textOld.equals(text));
        if(isTextChanged)news.setText(text);

        //обновление главной картинки
        if(headPic!=null){
            String headPicOldPath = news.getHeadPic();
            if(headPicOldPath!=null) {
                File oldPic = new File(headPicOldPath);
                oldPic.delete();
            }
            fileSaver.saveFile(news,headPic);
        }

        //news.setParagraphs(paragraphs);

        newsRepo.save(news);

        return true;
    }
}
