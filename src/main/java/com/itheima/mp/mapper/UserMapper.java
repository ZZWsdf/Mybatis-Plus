package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.mp.domain.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    List<User> queryUserByIds(@Param("ids") List<Long> ids);

    void updateBalanceByIds(@Param("ew") QueryWrapper<User> queryWrapper, int amount);
    @Update("update tb_user set balance=balance=#{money} where id=#{id}")
    void deductBalance(@Param("id") Long id, @Param("money")Integer money);
}
