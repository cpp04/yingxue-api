package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 分类(Category)实体类
 *
 * @author makejava
 * @since 2020-12-14 15:37:22
 */
@JsonInclude(JsonInclude.Include.NON_NULL) //修饰范围: 属性 类
// 属性上:代表当前属性的值为空时,不再转换的json字符串中体现
// 类上: 类中某个属性为空时均不在转换json中体现
public class Category implements Serializable {
    private static final long serialVersionUID = 133205130298635131L;

    private Integer id;
    /**
     * 名称
     */
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    /**
     * 父级分类id
     */
    @JsonProperty(value = "parent_id")
    private Integer parentId; //parent_id

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("deleted_at")
    private Date deletedAt;


    //关系属性
    private List<Category> children; //孩子



    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

}