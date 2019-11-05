package com.wandao.myapplication.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

import java.io.Serializable;

@Entity
public class User implements Serializable {
    private static final long serialVersionUID = 4541192821635855878L;
    @Unique
            @Id
    Long id;     //用户id
            @NotNull
            String name;
    @NotNull
    String tel;
    String email;
    String 	department;     //用户部门
    boolean controller;          //是否拥有特殊开门权限
    int ImageId;
    int boxId;
//    @ToOne(joinProperty = "name")
//    IdCard student;
@Generated(hash = 259269328)
public User(Long id, @NotNull String name, @NotNull String tel, String email,
        String department, boolean controller, int ImageId, int boxId) {
    this.id = id;
    this.name = name;
    this.tel = tel;
    this.email = email;
    this.department = department;
    this.controller = controller;
    this.ImageId = ImageId;
    this.boxId = boxId;
}
@Generated(hash = 586692638)
public User() {
}
public Long getId() {
    return this.id;
}
public void setId(Long id) {
    this.id = id;
}
public String getName() {
    return this.name;
}
public void setName(String name) {
    this.name = name;
}
public String getTel() {
    return this.tel;
}
public void setTel(String tel) {
    this.tel = tel;
}
public String getEmail() {
    return this.email;
}
public void setEmail(String email) {
    this.email = email;
}
public String getDepartment() {
    return this.department;
}
public void setDepartment(String department) {
    this.department = department;
}
public boolean getController() {
    return this.controller;
}
public void setController(boolean controller) {
    this.controller = controller;
}
public int getImageId() {
    return this.ImageId;
}
public void setImageId(int ImageId) {
    this.ImageId = ImageId;
}
public int getBoxId() {
    return this.boxId;
}
public void setBoxId(int boxId) {
    this.boxId = boxId;
}
  
}
