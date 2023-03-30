package com.qdu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type {
    private int typeId;
    private String typeName;
    private Type typeParent;

    private List<Type> typeList = new ArrayList<>();
}
