package com.bjpowernode.service.impl;

import com.bjpowernode.bean.Constant;
import com.bjpowernode.bean.Lend;
import com.bjpowernode.bean.User;
import com.bjpowernode.dao.LendDao;
import com.bjpowernode.dao.UserDao;
import com.bjpowernode.dao.impl.LendDaoImpl;
import com.bjpowernode.dao.impl.UserDaoImpl;
import com.bjpowernode.service.UserService;

import java.math.BigDecimal;
import java.util.List;

/**
 *  用户服务类
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    private LendDao lendDao = new LendDaoImpl();

    /**
     *  查询
     * @return
     */
    @Override
    public List<User> select() {
        //调用Dao层写好的方法即可
        return userDao.select();
    }

    /**
     *  添加
     * @param user
     */
    @Override
    public void add(User user) {
        userDao.add(user);
    }

    /**
     *  修改
     * @param user
     */
    @Override
    public void update(User user) {
        userDao.update(user);
    }

    /**
     *  删除
     * @param id
     */
    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    /**
     *  冻结
     * @param id
     */
    @Override
    public void frozen(int id) {
        userDao.frozen(id);
    }

    @Override
    public List<User> selectUserToLend() {
        return userDao.selectUserToLend();
    }

    /**
     * 充值
     * @param user
     * @param money
     * @return
     */
    @Override
    public User charge(User user, BigDecimal money) {
        //计算充值后的余额
        BigDecimal sum = money.add(user.getMoney());
        //判断是否需要修改状态
        if(BigDecimal.ZERO.compareTo(sum) < 0){
            //修改用户状态
            user.setStatus(Constant.USER_OK);
        }
        user.setMoney(sum);
        //更新数据
        userDao.update(user);

        //由于借阅文件中也存有用户数据，还需要修改借阅文件
        List<Lend> lendList = lendDao.select(null);
        for (int i = 0; i < lendList.size(); i++) {
            Lend lend = lendList.get(i);
            if(lend.getUser().getId() == user.getId()){
                lend.setUser(user);
                lendDao.update(lend);
                break;
            }
        }
        return user;
    }
}
