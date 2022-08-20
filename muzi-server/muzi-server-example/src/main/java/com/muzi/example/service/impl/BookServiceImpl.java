package com.muzi.example.service.impl;

import com.muzi.example.entity.es.Book;
import com.muzi.example.service.BookService;
import lombok.extern.log4j.Log4j2;
import com.muzi.example.mapper.es.BookMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: BookServiceImpl
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/8/9 22:26
 * @Version: 1.0
 */
@Service
@Log4j2
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;
    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public void addBook(Book book) {
        //可以先往mysql数据库插入数据，获取到id后再插入es里
        book.setId(12222222L);
        book.setTitle("测试");
        book.setContent("测试es使用");
        bookMapper.insert(book);

    }

    @Override
    public List<Book> searchBook(String keyword) {
        // 查询出所有标题为老汉的文档列表
        com.xpc.easyes.core.conditions.LambdaEsQueryWrapper<com.muzi.example.entity.es.Book> wrapper = new com.xpc.easyes.core.conditions.LambdaEsQueryWrapper<>();
        wrapper.eq(Book::getTitle, "测试");
        return bookMapper.selectList(wrapper);
    }

}
