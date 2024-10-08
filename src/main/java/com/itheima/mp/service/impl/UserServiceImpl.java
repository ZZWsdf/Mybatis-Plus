package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
    @Transactional
    public void deductBalance(Long id, Integer money) {
        //1.查询用户
        User user = this.getById(id);
        //2.校验用户状态
        if(user==null||user.getStatus()==2){
            throw new RuntimeException("用户状态异常");
        }
        //3.校验余额是否充足
        if(user.getBalance()<money){
            throw new RuntimeException("用户余额不足");
        }
        //4.扣除余额update tb_user set balance=balance-{money}
//        baseMapper.deductBalance(id,money);
        int remainBalance= user.getBalance()-money;
        lambdaUpdate()
                .set(User::getBalance,remainBalance)
                .set(remainBalance==0,User::getStatus,2)
                .eq(User::getId,id)
                .update();
    }

    @Override
    public List<User> queryUsers(String name, Integer status, Integer maxBalance, Integer minBalance) {
        List<User> list = lambdaQuery()
                .like(name != null, User::getUsername, name)
                .eq(status != null, User::getStatus, status)
                .le(maxBalance != null, User::getBalance, maxBalance)
                .ge(minBalance != null, User::getBalance, minBalance)
                .list();


        return list;
    }
}
