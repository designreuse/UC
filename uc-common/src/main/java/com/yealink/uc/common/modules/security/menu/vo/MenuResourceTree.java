package com.yealink.uc.common.modules.security.menu.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示根节点和根节点下面的子节点的集合的类
 */
public class MenuResourceTree {
    MenuResourceTreeNode parentNode;
    List<MenuResourceTreeNode> childNodeList = new ArrayList<>();
    MenuResourceTree childTree;

    public MenuResourceTree(final MenuResourceTreeNode parentNode) {
        this.parentNode = parentNode;
    }

    public MenuResourceTree() {
    }

    public MenuResourceTreeNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(final MenuResourceTreeNode parentNode) {
        this.parentNode = parentNode;
    }

    public List<MenuResourceTreeNode> getChildNodeList() {
        return childNodeList;
    }

    public void setChildNodeList(final List<MenuResourceTreeNode> childNodeList) {
        this.childNodeList = childNodeList;
    }

    public MenuResourceTree getChildTree() {
        return childTree;
    }

    public void setChildTree(final MenuResourceTree childTree) {
        this.childTree = childTree;
    }
}
