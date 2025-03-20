package com.tqs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Library {
    private List<Book> store;

    public Library(List<Book> store) {
        this.store = store;
    }

    public List<Book> findBooksByAuthor(String author) {
        return store.stream().filter(book -> book.getAuthor().equals(author)).toList();
    }

    public void addBook(Book book) {
        store.add(book);
    }

    public List<Book> findBooks(LocalDateTime from, LocalDateTime to) {
        return store.stream().filter(book -> book.getPublished().isAfter(from) && book.getPublished().isBefore(to)).toList();
    }
}
