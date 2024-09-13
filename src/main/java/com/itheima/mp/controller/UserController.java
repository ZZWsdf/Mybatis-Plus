package com.itheima.mp.controller;

import cn.hutool.core.bean.BeanUtil;
import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @ApiOperation("新增用户接口")
    @PostMapping
    public void saveUser(@RequestBody UserFormDTO userFormDTO){
        //1.把DTO拷贝为PO
        User user = BeanUtil.copyProperties(userFormDTO, User.class);
        //2.新增
        userService.save(user);
    }
    @ApiOperation("删除用户接口")
    @DeleteMapping("{id}")
    public void deleteUserById(@ApiParam("用户id") @PathVariable("id") Long id){
            userService.removeById(id);
    }
    @ApiOperation("根据id查询用户接口")
    @GetMapping("{id}")
    public UserVO queryUserById(@ApiParam("用户id") @PathVariable("id") Long id){
        //查询用户
        User user = userService.getById(id);
        //把PO拷贝到VO
        return BeanUtil.copyProperties(user,UserVO.class);
    }
    @ApiOperation("根据id批量查询用户接口")
    @GetMapping
    public List<UserVO> queryUserByIds(@ApiParam("用户id集合") @RequestParam("ids") List<Long> ids){
        //查询用户
        List<User> users = userService.listByIds(ids);
        //把PO拷贝到VO
        return BeanUtil.copyToList(users,UserVO.class);
    }
    @ApiOperation("扣减用户余额接口")
    @DeleteMapping("{id}/deduction/{money}")
    public void deductMoneyById(@ApiParam("用户id") @PathVariable("id") Long id,@ApiParam("扣减的金额") @PathVariable("money") Integer money){
        userService.deductBalance(id,money);
    }
}
