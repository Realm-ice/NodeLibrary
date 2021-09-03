package com.bjpowernode.service.impl;

import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.Constant;
import com.bjpowernode.bean.Lend;
import com.bjpowernode.bean.User;
import com.bjpowernode.dao.BookDao;
import com.bjpowernode.dao.LendDao;
import com.bjpowernode.dao.UserDao;
import com.bjpowernode.dao.impl.BookDaoImpl;
import com.bjpowernode.dao.impl.LendDaoImpl;
import com.bjpowernode.dao.impl.UserDaoImpl;
import com.bjpowernode.service.LendService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class LendServiceImpl implements LendService {

    private LendDao lendDao = new LendDaoImpl();
    private BookDao bookDao = new BookDaoImpl();
    private UserDao userDao = new UserDaoImpl();


    @Override
    public List<Lend> select(Lend lend) {
        return lendDao.select(lend);
    }

    /**
     * 添加借阅数据
     * @param userId
     * @param bookId
     */
    @Override
    public void add(int userId, int bookId) {
        //查询图书数据
        Book paramBook = new Book();
        paramBook.setId(bookId);
        List<Book> booklist = bookDao.select(paramBook);

        //查询用户数据
        User paramUser = new User();
        paramUser.setId(userId);
        List<User> userlist = userDao.select(paramUser);

        Lend lend = new Lend();
        //生成编号
        lend.setId(UUID.randomUUID().toString());

        //生成图书数据
        Book book = booklist.get(0);
        book.setStatus(Constant.STATUS_LEND);
        lend.setBook(book);

        //生成用户数据
        User user = userlist.get(0);
        user.setLend(true);
        lend.setUser(user);

        lend.setStatus(Constant.STATUS_LEND);
        //设置出借日期和归还日期
        LocalDate begin = LocalDate.now();
        lend.setLendDate(begin);
        lend.setReturnDate(begin.plusDays(30));//一个月期限

        //更新图书和用户数据:状态信息
        userDao.update(user);
        bookDao.update(book);
        //添加数据
        lendDao.add(lend);
    }

    @Override
    public List<Lend> returnBook(Lend lend) {
        //获取用户和图书对象
        Book book = lend.getBook();
        User user = lend.getUser();
        //修改状态
        book.setStatus(Constant.STATUS_STORAGE);
        user.setLend(false);
        //修改数据
        userDao.update(user);
        bookDao.update(book);
        lendDao.delete(lend.getId());
        //返回修改后的查询结果
        return lendDao.select(null);
    }

    @Override
    public void update(Lend lend) {
        lendDao.update(lend);
    }

    @Override
    public void delete(Lend lend) {
        lendDao.delete(lend.getId());
    }
}
