package com.kngpsk.domain;

import com.kngpsk.other.FileSetter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class News implements FileSetter, Serializable,Comparable<News> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private String head;
    private String headPic;
    private String text;

    public News(User author, String head, String headpic, String text) {
        this.author = author;
        this.head = head;
        this.headPic = headpic;
        this.text = text;
    }

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public User getAuthor() {return author;}
    public void setAuthor(User author) {this.author = author;}

    public String getHead() {return head;}
    public void setHead(String head) {this.head = head;}

    public String getHeadpic() {return headPic;}
    public void setHeadpic(String headpic) {this.headPic = headpic;}

    public String getText() {return text;}
    public void setText(String text) {this.text = text;}

    @Override
    public void setFile(String filename) {
        setHeadpic(filename);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return id == news.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(News news) {
        return (int) (id-news.getId());
    }
}
