package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.QueryPageParam;
import com.example.demo.common.Result;
import com.example.demo.entity.Menu;
import com.example.demo.entity.User;
import com.example.demo.service.MenuService;
import com.example.demo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author demo
 * @since 2023-03-25
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public List<User> list(){
        return userService.list();
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody User user){
        return userService.save(user) ? Result.suc():Result.fail();
    }
    //修改
//    @PostMapping("/mod")
//    public boolean mod(@RequestBody User user){
//        return userService.updateById(user);
//    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody User user){
        return userService.updateById(user) ? Result.suc() : Result.fail();
    }
    //新增或修改
//    @PostMapping("/saveOrMod")
//    public boolean saveOrMod(@RequestBody User user){
//        return userService.saveOrUpdate(user);
//    }
    //删除
//    @GetMapping("/delete")
//    public boolean delete(Integer id){
//        return userService.removeById(id);
//    }
    //删除
    @GetMapping("/delete")
    public Result delete(@RequestParam String id){
        return userService.removeById(id) ? Result.suc() : Result.fail();
    }
    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        //匹配账号密码
        List list = userService.lambdaQuery().eq(User::getNo, user.getNo()).eq(User::getPassword, user.getPassword()).list();
        if (list.size()>0){
            //匹配对应权限
            User user1 = (User)list.get(0);
            List menuList = menuService.lambdaQuery().like(Menu::getMenuright, user1.getRoleId()).list();
            //应为要返回user1和menuList，所以用HashMap，而不是原来的方式
            HashMap res = new HashMap();
            res.put("user", user1);
            res.put("menu", menuList);
            return Result.suc(res);
        }
        return Result.fail();
    }
    //查询（模糊like、匹配eq）
    @PostMapping("/listP")
    public Result listP(@RequestBody User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(user.getName())){
            lambdaQueryWrapper.like(User::getName, user.getName());
        }

        return Result.suc(userService.list(lambdaQueryWrapper));
    }

    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no){
        List list = userService.lambdaQuery().eq(User::getNo, no).list();
        return list.size() > 0 ? Result.suc(list) : Result.fail();
    }

    @PostMapping("/listPage")
//    HashMap方法
//    public List<User> listPage(@RequestBody HashMap map){
//        System.out.println(map);
//        System.out.println("num==="+(String)map.get("pageSize"));
//        return null;
//    }
    //自定义类QueryPageParam方法，将数据封装到类中，不固定的数据依然要用HashMap方法
    public List<User> listPage(@RequestBody QueryPageParam query){
//        System.out.println(query);
//        System.out.println("num==="+query.getPageNum());
//        System.out.println("size==="+query.getPageSize());
        //利用page做分页
        HashMap param = query.getParam();
        System.out.println("name==="+(String)param.get("name"));
//        System.out.println("no=="+(String)param.get("no"));
        String name = (String)param.get("name");
        Page<User> page = new Page();
        //上面参数加了，下面可以不用加这两条
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName, name);

        IPage result = userService.page(page, lambdaQueryWrapper);
        System.out.println("total=="+result.getTotal());
        return result.getRecords();
    }

    @PostMapping("/listPageC")
    //自定义类QueryPageParam方法，将数据封装到类中，不固定的数据依然要用HashMap方法
    public Result listPageC(@RequestBody QueryPageParam query){
//        System.out.println(query);
//        System.out.println("num==="+query.getPageNum());
//        System.out.println("size==="+query.getPageSize());
        //利用page做分页
        HashMap param = query.getParam();
        System.out.println("name==="+(String)param.get("name"));
//        System.out.println("no=="+(String)param.get("no"));
        String name = (String)param.get("name");
        Page<User> page = new Page();
        //上面参数加了，下面可以不用加这两条
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

//        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
//        lambdaQueryWrapper.like(User::getName, name);

        IPage result = userService.pageC(page);
        System.out.println("total=="+result.getTotal());
        return Result.suc(result.getRecords(), result.getTotal());
    }


    @PostMapping("/listPageC1")
    //自定义类QueryPageParam方法，将数据封装到类中，不固定的数据依然要用HashMap方法
    public Result listPageC1(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String sex = (String)param.get("sex");
        String roleId = (String)param.get("roleId");
        Page<User> page = new Page();
        //上面参数加了，下面可以不用加这两条
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(User::getName, name);
        }
        if(StringUtils.isNotBlank(sex)){
            lambdaQueryWrapper.eq(User::getSex, sex);
        }
        if(StringUtils.isNotBlank(roleId)){
            lambdaQueryWrapper.eq(User::getRoleId, roleId);
        }

        IPage result = userService.pageC1(page, lambdaQueryWrapper);
        return Result.suc(result.getRecords(), result.getTotal());
    }
}