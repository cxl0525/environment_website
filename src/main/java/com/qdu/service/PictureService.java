package com.qdu.service;

import com.github.pagehelper.Page;
import com.qdu.pojo.Activity;
import com.qdu.pojo.Picture;
import com.qdu.pojo.Type;

import java.util.List;

public interface PictureService {
    Page<Picture> queryPictureListByPage();
    List<Type> queryAllTypeParent();
    List<Picture> queryPictureListByTypeId(int TypeId);
    List<Picture> queryPictureListByUserId(int UserId);
    void updatePicClicks(int PicId);

    List<Type> queryAllTypeList();

    void addPicture(Picture picture);


    List<Picture> queryPictureListByParentTypeId(int typeId);
    List<Picture> queryPictureListByTop(int num);
}
