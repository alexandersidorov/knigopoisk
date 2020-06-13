package com.kngpsk.domain;

import com.kngpsk.other.FileSetter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Paragraph implements FileSetter, Serializable, Comparable<Paragraph> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String pic;
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "news_id")
    private News news;

    public Paragraph() {}

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public String getPic() {return pic;}
    public void setPic(String pic) {this.pic = pic;}

    public String getText() {return text;}
    public void setText(String text) {this.text = text;}

    public News getNews() {return news;}
    public void setNews(News news) {this.news = news;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paragraph paragraph = (Paragraph) o;
        return id == paragraph.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Paragraph paragraph) {
        return Long.compare(id,paragraph.getId());
    }

    @Override
    public void setFile(String filename) {
        setPic(filename);
    }
}
