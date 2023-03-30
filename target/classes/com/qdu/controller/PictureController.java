package com.qdu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qdu.pojo.*;
import com.qdu.service.PictureService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class PictureController {

    @Autowired
    private PictureService pictureService;


    @RequestMapping("/gallery")
    public String toGallery(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model){
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Picture> pageInfo = new PageInfo<>(pictureService.queryPictureListByPage(),8);
        model.addAttribute("pageInfo",pageInfo);
        List<Type> types = pictureService.queryAllTypeParent();
        List<Type> types1 = pictureService.queryAllTypeList();
        for (Type type : types) {
            for (Type type1 : types1) {
                if(type1.getTypeParent().getTypeId()==type.getTypeId()){
                    type.getTypeList().add(type1);
                }
            }
        }
        model.addAttribute("types",types);
        return "gallery";
    }

    @GetMapping("/pictureByType/{typeId}")
    public String pictureByType(@PathVariable int typeId, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) {
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Picture> pageInfo = new PageInfo<>(pictureService.queryPictureListByTypeId(typeId),8);
        model.addAttribute("pageInfo",pageInfo);
        List<Type> types = pictureService.queryAllTypeParent();
        List<Type> types1 = pictureService.queryAllTypeList();
        for (Type type : types) {
            for (Type type1 : types1) {
                if(type1.getTypeParent().getTypeId()==type.getTypeId()){
                    type.getTypeList().add(type1);
                }
            }
        }
        model.addAttribute("types",types);
        return "gallery";
    }

    @GetMapping("/pictureByParentType/{typeId}")
    public String pictureByParentType(@PathVariable int typeId, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) {
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Picture> pageInfo = new PageInfo<>(pictureService.queryPictureListByParentTypeId(typeId),8);
        model.addAttribute("pageInfo",pageInfo);
        List<Type> types = pictureService.queryAllTypeParent();
        List<Type> types1 = pictureService.queryAllTypeList();
        for (Type type : types) {
            for (Type type1 : types1) {
                if(type1.getTypeParent().getTypeId()==type.getTypeId()){
                    type.getTypeList().add(type1);
                }
            }
        }
        model.addAttribute("types",types);
        return "gallery";
    }


    @GetMapping("/pictureByUser/{userId}")
    public String pictureByUser(@PathVariable int userId, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) {
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Picture> pageInfo = new PageInfo<>(pictureService.queryPictureListByUserId(userId),8);
        model.addAttribute("pageInfo",pageInfo);
        List<Type> types = pictureService.queryAllTypeParent();
        List<Type> types1 = pictureService.queryAllTypeList();
        for (Type type : types) {
            for (Type type1 : types1) {
                if(type1.getTypeParent().getTypeId()==type.getTypeId()){
                    type.getTypeList().add(type1);
                }
            }
        }
        model.addAttribute("types",types);
        return "gallery";
    }

    @ResponseBody
    @PostMapping("/updatePicClicks")
    public String updatePicClicks(@RequestBody Picture picture){
        pictureService.updatePicClicks(picture.getPicId());
        String message="";
        return  message;
    }


    @PostMapping("/addPicture")
    public String addPicture(MultipartFile picImage
            , @RequestParam("picName")String picName
            , @RequestParam("picType")String picType
            , HttpSession session) throws ParseException, IOException {
        //获取上传用户 ID
        User loginUser = (User) session.getAttribute("loginUser");
        //获取文件原名
        String oldFileName = picImage.getOriginalFilename();
        //获取文件后缀
        String extension = "."+ FilenameUtils.getExtension(picImage.getOriginalFilename());

        //生成新的文件名称
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ UUID.randomUUID().toString().replace("-", "") + extension;

        //文件大小
        Long size = picImage.getSize();

        //文件类型
        String type = picImage.getContentType();

        //处理根据日期生成目录
        String realPath = ResourceUtils.getURL("classpath:").getPath()+"/static/files-pic";
        String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dateDirPath = realPath + "/" +dateFormat;
        File dateDir = new File(dateDirPath);
        if(!dateDir.exists())  dateDir.mkdirs();
        //处理上传文件
        picImage.transferTo(new File(dateDir,newFileName));
        //将文件信息放入数据库保存
        String isImg = type.startsWith("image") ? "是":"否";

        Picture picture = new Picture();
        if(isImg=="是")  picture.setPicImagePath("/files-pic/"+dateFormat+"/"+newFileName);
        else ;

        picture.setPicClicks(0);
        picture.setPicUser(loginUser);
        picture.setPicName(picName);

        Type type1 = new Type();
        type1.setTypeId(Integer.parseInt(picType));
        picture.setPicType(type1);

        pictureService.addPicture(picture);

        return "redirect:/gallery";
    }
}
