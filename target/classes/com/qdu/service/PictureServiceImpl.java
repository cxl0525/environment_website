package com.qdu.service;

import com.github.pagehelper.Page;
import com.qdu.mapper.PictureMapper;
import com.qdu.pojo.Activity;
import com.qdu.pojo.Picture;
import com.qdu.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService{

    @Autowired
    private PictureMapper pictureMapper;


    @Override
    public Page<Picture> queryPictureListByPage() {
        return pictureMapper.queryPictureListByPage();
    }

    @Override
    public List<Type> queryAllTypeParent() {
        return pictureMapper.queryAllTypeParent();
    }

    @Override
    public List<Picture> queryPictureListByTypeId(int TypeId) {
        return pictureMapper.queryPictureListByTypeId(TypeId);
    }

    @Override
    public List<Picture> queryPictureListByUserId(int UserId) {
        return pictureMapper.queryPictureListByUserId(UserId);
    }

    @Override
    public void updatePicClicks(int PicId) {
        pictureMapper.updatePicClicks(PicId);
    }

    @Override
    public List<Type> queryAllTypeList() {
        return pictureMapper.queryAllTypeList();
    }

    @Override
    public void addPicture(Picture picture) {
        pictureMapper.addPicture(picture);
    }

    @Override
    public List<Picture> queryPictureListByParentTypeId(int typeId) {
        return pictureMapper.queryPictureListByParentTypeId(typeId);
    }

    @Override
    public List<Picture> queryPictureListByTop(int num) {
        return pictureMapper.queryPictureListByTop(num);
    }


}
