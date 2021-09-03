package com.bjpowernode.dao.impl;

import com.bjpowernode.bean.Lend;
import com.bjpowernode.bean.PathConstant;
import com.bjpowernode.bean.User;
import com.bjpowernode.dao.LendDao;
import com.bjpowernode.util.BeanUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LendDaoImpl implements LendDao {

    /**
     *  查询
     * @param lend
     * @return
     */
    @Override
    public List<Lend> select(Lend lend) {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PathConstant.LEND_PATH))) {

            List<Lend> list = (List<Lend>)ois.readObject();
            if (lend == null || "".equals(lend.getId())) {
                return list;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        return new ArrayList<>();
    }

    /**
     * 添加借阅处理到磁盘文件
     * @param lend
     */
    @Override
    public void add(Lend lend) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try{
            ois = new ObjectInputStream(new FileInputStream(PathConstant.LEND_PATH));
            List<Lend> list = (List<Lend>)ois.readObject();
            list.add(lend);
            oos = new ObjectOutputStream(new FileOutputStream(PathConstant.LEND_PATH));
            oos.writeObject(list);
        }catch(Exception e){
            e.printStackTrace();
            //当数据写入错误时控制层可以捕获到错误
            throw new RuntimeException();
        }finally {
            try{
                if(ois != null){
                    ois.close();
                }
                if(oos != null){
                    oos.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }

    }

    /**
     * 删除归还的lend对象
     * @param id
     */
    @Override
    public void delete(String id) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try{
            ois = new ObjectInputStream(new FileInputStream(PathConstant.LEND_PATH));
            List<Lend> list = (List<Lend>)ois.readObject();
            Lend lend = list.stream().filter(l -> l.getId().equals(id)).findFirst().get();
            list.remove(lend);
            oos = new ObjectOutputStream(new FileOutputStream(PathConstant.LEND_PATH));
            oos.writeObject(list);
        }catch(Exception e){
            e.printStackTrace();
            //当数据写入错误时控制层可以捕获到错误
            throw new RuntimeException();
        }finally {
            try{
                if(ois != null){
                    ois.close();
                }
                if(oos != null){
                    oos.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }

    }

    /**
     * 更新数据
     * @param lend
     */
    @Override
    public void update(Lend lend) {
        //将list数据从文件中查出来
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.LEND_PATH));
            List<Lend> list = (List<Lend>) ois.readObject();
            if (list != null) {
                //从list中查找要修改的数据
                Lend originLend = list.stream().filter(l -> l.getId().equals(lend.getId())).findFirst().get();
                //修改数据
                BeanUtil.populate(originLend,lend);

                //将数据持久化到文件中
                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.LEND_PATH));
                oos.writeObject(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
