package com.kngpsk.domain;

import com.kngpsk.other.FileSetter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@Entity
public class News implements FileSetter, Serializable,Comparable<News> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @NotBlank(message = "Fill the head.")
    private String head;

    @NotBlank(message = "Fill the text.")
    private String text;

    private String headPic;

    @OneToMany(mappedBy = "news",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Paragraph> paragraphs = new TreeSet<>();

    public News(User author, String head, String text) {
        this.author = author;
        this.head = head;
        this.text = text;
    }

    public News(){}

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public User getAuthor() {return author;}
    public void setAuthor(User author) {this.author = author;}

    public String getHead() {return head;}
    public void setHead(String head) {this.head = head;}

    public String getHeadPic() {return headPic;}
    public void setHeadPic(String headPic) {this.headPic = headPic;}

    public String getText() {return text;}
    public void setText(String text) {this.text = text;}

    public Set<Paragraph> getParagraphs() {return paragraphs;}
    public void setParagraphs(Set<Paragraph> paragraphs) {this.paragraphs = paragraphs;}

    @Override
    public void setFile(String filename) {
        setHeadPic(filename);
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
        return Long.compare(id,news.getId());
    }
}
