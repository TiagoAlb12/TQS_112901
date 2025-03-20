package com.tqs;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Book {
    private LocalDateTime published;
    private String author;
    private String title;

    public Book(LocalDateTime published, String author, String title) {
        this.published = published;
        this.author = author;
        this.title = title;
    }
}