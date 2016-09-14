package com.yealink.uc.web.modules;

import java.util.List;
import java.util.Map;

import com.yealink.uc.common.modules.security.menu.vo.MenuResourceTree;
import com.yealink.uc.common.modules.security.menu.vo.MenuResourceTreeNode;

import org.springframework.util.StringUtils;

/**
 * @author ChNan
 */
public class MenuTreeBuilder {
    private Map<Long, MenuResourceTree> map;

    public MenuTreeBuilder(Map<Long, MenuResourceTree> map) {
        this.map = map;
    }

    private String getRealUrl(MenuResourceTreeNode node) {
        String oldUrl = node.getUrl();
        if (!StringUtils.hasText(oldUrl)) return "#";
        return oldUrl;
    }

    public String createMenuTreeNode() {
        StringBuilder builder = new StringBuilder(100);

        for (Map.Entry<Long, MenuResourceTree> entry : map.entrySet()) {
            MenuResourceTree menuTree = entry.getValue();
            List<MenuResourceTreeNode> treeNodes = menuTree.getChildNodeList();
            MenuResourceTreeNode rootNode = menuTree.getParentNode();
            builder.append("<ul class=\"nav navbar-nav\">\n<li class=\"dropdown\">\n<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">\n" +
                rootNode.getName()
                + "\n<b class=\"caret\"></b></a>");
            for (int i = 0; i < treeNodes.size(); i++) {
                if (i == 0) builder.append("<ul class=\"dropdown-menu\">");
                MenuResourceTreeNode treeNode = treeNodes.get(i);
                builder.append("<li><a href=\"")
                    .append(getRealUrl(treeNode))
                    .append("\">")
                    .append(treeNode.getName())
                    .append("</a></li>");
                if (i == treeNodes.size() - 1) builder.append("</ul>");
            }
            builder.append("</li>\n</ul>");
        }
        return builder.toString();
    }
}