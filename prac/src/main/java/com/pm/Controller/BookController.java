package com.pm.Controller;

import com.pm.Model.Book;
import com.pm.Repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookRepo bookRepo;

    @GetMapping
    public List<Book> getBooks(){
        return bookRepo.findAll();
    }
}
