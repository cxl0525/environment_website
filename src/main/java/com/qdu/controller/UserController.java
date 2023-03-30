package com.qdu.controller;

import com.qdu.mapper.UserMapper;
import com.qdu.pojo.Picture;
import com.qdu.pojo.Type;
import com.qdu.pojo.User;
import com.qdu.service.UserService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Value("${user.userAvatar}")
    private String userAvatar;

    @GetMapping("/queryUserList")
    public List<User> queryUserList(){
        List<User> userList = userService.queryUserList();
        for (User user : userList) {
            System.out.println(user);
        }
        return userList;
    }

    //根据id删除用户
    @GetMapping("/deleteUser")
    public String deleteUser(){
        userService.deleteUser(5);
        return "ok";
    }

    //跳转到注册界面
    @RequestMapping("/register")
    public String register(Model model){

        model.addAttribute("user2",new User());
        return "register";
    }


    @PostMapping("/user/register")
    public String userRegister(@RequestParam("province")String province,
                               @RequestParam("city")String city, User user, RedirectAttributes attributes, Model model) {
        System.out.println("province"+province+"city"+city);
        System.out.println("***************"+user+"--------------------");

        User u = userService.queryUserByUserName(user.getUserName());
        System.out.println("***************"+u+"--------------------");

        if(u!=null){//用户名已被注册
            System.out.println("----------"+u);
            model.addAttribute("message", "用户名已被注册");
            model.addAttribute("user2", user);
            return "register";
        }else{

            user.setUserAddress(province+"-"+city);
            user.setUserAvatar(userAvatar);
            user.setUserIsCheck(0);
            user.setUserCreateDate(new Date());
            System.out.println("***************"+user);

            userService.addUser(user);
            return "redirect:/login";

        }

    }


    //跳转到个人界面
    @RequestMapping("/person")
    public String person(Model model, HttpSession session){
       // User loginUser = (User) session.getAttribute("loginUser");
        //session里面有的基本信息 积分 还有等级名称
        //没有活动，日志，
        return "personal";
    }

    /**
     * 跳转到个人信息修改界面
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/user/personal/update")
    public String personUpdate(Model model, HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("user",loginUser);
           return "update_information";
    }

    /**
     * 修改基本信息
     * @param province
     * @param city
     * @param user
     * @param attributes
     * @param model
     * @param session
     * @return
     */
    @PostMapping("/user/update")
    public String userupdate(@RequestParam("province")String province,
                               @RequestParam("city")String city, User user, RedirectAttributes attributes, Model model,HttpSession session) {
        System.out.println("province"+province+"city"+city);
        User u = userService.queryUserByUserName(user.getUserName());
        User loginUser = (User) session.getAttribute("loginUser");
        if(u!=null&&!(user.getUserName().equals(loginUser.getUserName()))){//用户名已被注册
            System.out.println("----------"+u);
            model.addAttribute("message", "用户名已被注册");
            return "update_information";
        }else{

            user.setUserAddress(province+"-"+city);
            System.out.println("***************"+user);
            user.setUserId(loginUser.getUserId());
            userService.updateUserBasic(user);//修改基本信息
            user = userService.queryUserById(user.getUserId());
            session.setAttribute("loginUser",user);
            return "redirect:/user/personal/update";
        }
    }

    /**
     * 修改密码
     * @param user
     * @param attributes
     * @param model
     * @param session
     * @return
     */
    @PostMapping("/user/updatePwd")
    public String updatePwd( User user, RedirectAttributes attributes, Model model,HttpSession session) {
            User loginUser = (User) session.getAttribute("loginUser");
            user.setUserId(loginUser.getUserId());
            userService.updateUserPwd(user);//修改密码

            session.setAttribute("loginUser",user);
        return "redirect:/user/personal/update";
    }

    /**
     * 上传头像
     * @param picImagePath
     * @param session
     * @return
     * @throws ParseException
     * @throws IOException
     */
    @PostMapping("/user/updateAvatar")
    public String addPicture(MultipartFile picImagePath
            , HttpSession session) throws ParseException, IOException {
        //获取上传用户 ID
        User loginUser = (User) session.getAttribute("loginUser");
        //获取文件原名
        String oldFileName = picImagePath.getOriginalFilename();
        //获取文件后缀
        String extension = "."+ FilenameUtils.getExtension(picImagePath.getOriginalFilename());

        //生成新的文件名称
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ UUID.randomUUID().toString().replace("-", "") + extension;

        //文件大小
        Long size = picImagePath.getSize();

        //文件类型
        String type = picImagePath.getContentType();

        //处理根据日期生成目录
        String realPath = ResourceUtils.getURL("classpath:").getPath()+"/static/files-user-avatar";
        String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dateDirPath = realPath + "/" +dateFormat;
        File dateDir = new File(dateDirPath);
        if(!dateDir.exists())  dateDir.mkdirs();
        //处理上传文件
        picImagePath.transferTo(new File(dateDir,newFileName));
        //将文件信息放入数据库保存
        String isImg = type.startsWith("image") ? "是":"否";


        if(isImg=="是")  loginUser.setUserAvatar("/files-user-avatar/"+dateFormat+"/"+newFileName);
        else ;

        userService.updateUserAvatar(loginUser);
        session.setAttribute("loginUser",loginUser);

        return "redirect:/user/personal/update";
    }
}
