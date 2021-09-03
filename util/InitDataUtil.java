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
        //��ʼ���û�����
        List<User> userList = new ArrayList<>();
        userList.add(new User(1001, "�Ŵ�", Constant.USER_OK, BigDecimal.TEN,false));
        userList.add(new User(1002, "����", Constant.USER_OK, BigDecimal.TEN,false));
        userList.add(new User(1002, "������", Constant.USER_OK, BigDecimal.TEN,false));
        initData(PathConstant.USER_PATH,userList);

        //��ʼ��ͼ������
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1,"java����","�����ڵ�",Constant.TYPE_COMPUTER,"946-3307-1","��е��ҵ������",Constant.STATUS_STORAGE));
        bookList.add(new Book(2,"java����","��Զ",Constant.TYPE_COMPUTER,"946-3307-2","��е��ҵ������",Constant.STATUS_STORAGE));
        bookList.add(new Book(3,"��ʫ������","�Ͻ�����",Constant.TYPE_LITERATURE,"937-1-8015","���ɹų�����",Constant.STATUS_STORAGE));
        bookList.add(new Book(4,"��¥��","��ѩ��",Constant.TYPE_LITERATURE,"937-1-3307","�ִ���ѧ������",Constant.STATUS_STORAGE));
        bookList.add(new Book(5,"��Хɽׯ","[Ӣ]��������������",Constant.TYPE_LITERATURE,"7-5402-1078-1","Ӣ��������",Constant.STATUS_STORAGE));
        bookList.add(new Book(6,"Ц������","��ӹ",Constant.TYPE_LITERATURE,"937-2-4056","���ֳ�����",Constant.STATUS_STORAGE));
        bookList.add(new Book(7,"Python�����ŵ���ͨ","��ҹ����",Constant.TYPE_COMPUTER,"946-2-7132","�����ѧ��������",Constant.STATUS_STORAGE));
        bookList.add(new Book(8,"������","�ǵ���˹��",Constant.TYPE_ECONOMY,"4774-5-6601","����������",Constant.STATUS_STORAGE));
        bookList.add(new Book(9,"׿�г�Ч�Ĺ�����","��³��",Constant.TYPE_MANAGEMENT,"912-1-2043","�¹���ѧ������",Constant.STATUS_STORAGE));
        bookList.add(new Book(9,"ֻ��ƫִ���������","���ϡ���³��",Constant.TYPE_MANAGEMENT,"912-1-2039","�����ƾ�������",Constant.STATUS_STORAGE));
        bookList.add(new Book(9,"��������","���ϡ���³��",Constant.TYPE_MANAGEMENT,"921-1-2040","�����ƾ�������",Constant.STATUS_STORAGE));
        initData(PathConstant.BOOK_PATH,bookList);

        //��ʼ����������
        List<Lend> lendList = new ArrayList<>();
        User user = new User(1001, "�Ŵ�", Constant.USER_OK, BigDecimal.TEN,true);
        Book book = new Book(1, "java����", "�����ڵ�", Constant.TYPE_COMPUTER, "946-3307-1", "��е��ҵ������", Constant.STATUS_STORAGE);

        Lend lend = new Lend();

        //ʹ��UUID���ɱ��
        lend.setId(UUID.randomUUID().toString());
        lend.setUser(user);
        lend.setBook(book);
        lend.setStatus(Constant.STATUS_LEND);
        LocalDate begin = LocalDate.now();
        lend.setLendDate(begin);
        //���ù黹����
        lend.setReturnDate(begin.plusDays(30));

        lendList.add(lend);

        initData(PathConstant.LEND_PATH,lendList);
    }

    /**
     * ��ʼ������
     */
    public static void initData(String path,List<?> list) {
        ObjectOutputStream oos = null;
        //��������ļ���
        try {
            File directory = new File(path.split("/")[0] + "/");
            File file = new File(path);
            //�ж��ļ����Ƿ����
            if (!directory.exists()) {
                directory.mkdir();
            }
            //�ж��ļ��Ƿ����
            if (!file.exists()) {
                file.createNewFile();
                //���ö����������list����д�����ļ���
                oos = new ObjectOutputStream(new FileOutputStream(path));
                oos.writeObject(list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //�ر���
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
