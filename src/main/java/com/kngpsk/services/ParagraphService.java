package com.kngpsk.services;

import com.kngpsk.domain.News;
import com.kngpsk.domain.Paragraph;
import com.kngpsk.repos.ParagraphRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
public class ParagraphService {

    @Autowired
    ParagraphRepo paragraphRepo;

    @Autowired
    FileSaver fileSaver;

    public List<Paragraph> findByNews(News news){
        return paragraphRepo.findByNews(news);
    }

    public boolean addParagraph(Paragraph paragraph){
        paragraphRepo.save(paragraph);
        return true;
    }

    public boolean addParagraph(News news,String text,MultipartFile pic)throws IOException {
        Paragraph paragraph = new Paragraph();
        paragraph.setNews(news);
        paragraph.setText(text);
        fileSaver.saveFile(paragraph,pic);

        addParagraph(paragraph);

        return true;
    }

    public boolean updateParagraph(Paragraph paragraph,String text,MultipartFile pic) throws IOException {

        //обновление текста
        String textOld = paragraph.getText();
        boolean isTextChanged = (text != null && !textOld.equals(text));
        if(isTextChanged)paragraph.setText(text);

        //обновление картинки
        if(pic!=null){
            String headPicOldPath = paragraph.getPic();
            if(headPicOldPath!=null) {
                File oldPic = new File(headPicOldPath);
                oldPic.delete();
            }
            fileSaver.saveFile(paragraph,pic);
        }

        paragraphRepo.save(paragraph);

        return true;
    }

}
