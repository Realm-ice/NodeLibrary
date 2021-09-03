package com.bjpowernode.util;

import com.bjpowernode.bean.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InitDataUtil {
    public static void main(String[] args) {
        //初始化用户数据
        List<User> userList = new ArrayList<>();
        userList.add(new User(1001, "张大虎", Constant.USER_OK, BigDecimal.TEN,false));
        userList.add(new User(1002, "刘大狗", Constant.USER_OK, BigDecimal.TEN,false));
        userList.add(new User(1002, "王大狼", Constant.USER_OK, BigDecimal.TEN,false));
        initData(PathConstant.USER_PATH,userList);

        //初始化图书数据
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1,"java入门","动力节点",Constant.TYPE_COMPUTER,"946-3307-1","机械工业出版社",Constant.STATUS_STORAGE));
        bookList.add(new Book(2,"java进阶","高远",Constant.TYPE_COMPUTER,"946-3307-2","机械工业出版社",Constant.STATUS_STORAGE));
        bookList.add(new Book(3,"唐诗三百首","上疆村民",Constant.TYPE_LITERATURE,"937-1-8015","内蒙古出版社",Constant.STATUS_STORAGE));
        bookList.add(new Book(4,"红楼梦","曹雪芹",Constant.TYPE_LITERATURE,"937-1-3307","现代文学出版社",Constant.STATUS_STORAGE));
        bookList.add(new Book(5,"呼啸山庄","[英]艾米丽·勃朗特",Constant.TYPE_LITERATURE,"7-5402-1078-1","英国出版社",Constant.STATUS_STORAGE));
        bookList.add(new Book(6,"笑傲江湖","金庸",Constant.TYPE_LITERATURE,"937-2-4056","吉林出版社",Constant.STATUS_STORAGE));
        bookList.add(new Book(7,"Python从入门到精通","白夜黑羽",Constant.TYPE_COMPUTER,"946-2-7132","计算机学报出版社",Constant.STATUS_STORAGE));
        bookList.add(new Book(8,"国富论","亚当·斯密",Constant.TYPE_ECONOMY,"4774-5-6601","北京出版社",Constant.STATUS_STORAGE));
        bookList.add(new Book(9,"卓有成效的管理者","德鲁克",Constant.TYPE_MANAGEMENT,"912-1-2043","德国文学出版社",Constant.STATUS_STORAGE));
        bookList.add(new Book(9,"只有偏执狂才能生存","安迪·格鲁夫",Constant.TYPE_MANAGEMENT,"912-1-2039","美国财经出版社",Constant.STATUS_STORAGE));
        bookList.add(new Book(9,"逆流而上","安迪·格鲁夫",Constant.TYPE_MANAGEMENT,"921-1-2040","美国财经出版社",Constant.STATUS_STORAGE));
        initData(PathConstant.BOOK_PATH,bookList);

        //初始化借阅数据
        List<Lend> lendList = new ArrayList<>();
        User user = new User(1001, "张大虎", Constant.USER_OK, BigDecimal.TEN,true);
        Book book = new Book(1, "java入门", "动力节点", Constant.TYPE_COMPUTER, "946-3307-1", "机械工业出版社", Constant.STATUS_STORAGE);

        Lend lend = new Lend();

        //使用UUID生成编号
        lend.setId(UUID.randomUUID().toString());
        lend.setUser(user);
        lend.setBook(book);
        lend.setStatus(Constant.STATUS_LEND);
        LocalDate begin = LocalDate.now();
        lend.setLendDate(begin);
        //设置归还日期
        lend.setReturnDate(begin.plusDays(30));

        lendList.add(lend);

        initData(PathConstant.LEND_PATH,lendList);
    }

    /**
     * 初始化数据
     */
    public static void initData(String path,List<?> list) {
        ObjectOutputStream oos = null;
        //创建相关文件夹
        try {
            File directory = new File(path.split("/")[0] + "/");
            File file = new File(path);
            //判断文件夹是否存在
            if (!directory.exists()) {
                directory.mkdir();
            }
            //判断文件是否存在
            if (!file.exists()) {
                file.createNewFile();
                //利用对象输出流将list数据写出到文件中
                oos = new ObjectOutputStream(new FileOutputStream(path));
                oos.writeObject(list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
