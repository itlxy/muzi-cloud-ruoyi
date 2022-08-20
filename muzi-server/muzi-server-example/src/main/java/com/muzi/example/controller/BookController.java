package com.muzi.example.controller;

import com.muzi.common.core.web.domain.AjaxResult;
import com.muzi.example.entity.es.Book;
import com.muzi.example.service.BookService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import javax.annotation.Resource;

/**
 * @ClassName: BookController
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/8/9 22:33
 * @Version: 1.0
 */
@RestController
@RequestMapping("/book")
public class BookController {
    @Resource
    private BookService bookService;

    @PostMapping("/addbook")
    public AjaxResult addBook(@RequestBody Book book){
        bookService.addBook(book);
        return AjaxResult.success();
    }

    @GetMapping("/search")
    public List<Book> search(String key){
        return bookService.searchBook(key);
    }
}
