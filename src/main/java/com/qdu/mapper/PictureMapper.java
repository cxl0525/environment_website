package com.qdu.mapper;

import com.github.pagehelper.Page;
import com.qdu.pojo.Picture;
import com.qdu.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PictureMapper {
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
