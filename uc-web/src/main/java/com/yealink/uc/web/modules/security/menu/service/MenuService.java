package com.yealink.uc.web.modules.security.menu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yealink.uc.common.modules.security.menu.entity.Menu;
import com.yealink.uc.common.modules.security.menu.vo.MenuResourceTreeNode;
import com.yealink.uc.common.modules.common.NodeType;
import com.yealink.uc.common.modules.security.module.entity.Module;
import com.yealink.uc.common.modules.security.project.entity.Project;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.EntityUtil;
import com.yealink.uc.web.modules.common.dao.IdGeneratorDao;
import com.yealink.uc.web.modules.security.menu.dao.MenuDao;
import com.yealink.uc.web.modules.security.menu.request.CreateMenuRequest;
import com.yealink.uc.web.modules.security.menu.request.EditMenuRequest;
import com.yealink.uc.web.modules.security.module.dao.ModuleDao;
import com.yealink.uc.web.modules.security.project.dao.ProjectDao;
import com.yealink.uc.web.modules.security.rolepermission.dao.RolePermissionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author ChNan
 */
@Service
public class MenuService {
    @Autowired
    MenuDao menuDao;
    @Autowired
    ProjectDao projectDao;
    @Autowired
    ModuleDao moduleDao;

    @Autowired
    IdGeneratorDao idGeneratorDao;
    @Autowired
    RolePermissionDao rolePermissionDao;

    public Menu get(Long menuId) {
        return menuDao.get(menuId);
    }

    public List<Menu> findAllByModule(Long moduleId) {
        return menuDao.findByModule(moduleId);
    }

    public List<Menu> findAllByProject(Long projectId) {
        return menuDao.findByProject(projectId);
    }
    public List<Menu> findByIds(List<Long> menuIds) {
        return menuDao.findByIds(menuIds);
    }

    public List<MenuResourceTreeNode> showTreeNodes(Long projectId, Long enterpriseId) {
        Project project = projectDao.get(projectId, enterpriseId);
        if (projectDao.get(projectId, enterpriseId) == null) {
            throw new BusinessHandleException("project.exception.not.exist");
        }
        List<Menu> menuList = menuDao.findByProject(projectId);
        List<Module> moduleList = moduleDao.findByProject(projectId);

        Map<Long, Boolean> moduleExistMap = new HashMap<>();
        List<MenuResourceTreeNode> menuTreeNodeList = new ArrayList<>();
        menuTreeNodeList.add(new MenuResourceTreeNode(0L, project.getName(), -1L, "#", NodeType.PROJECT.getType(), ""));
        if (!CollectionUtils.isEmpty(moduleList)) {
            for (Module module : moduleList) {
                moduleExistMap.put(module.get_id(), true);
                if (module.getParentId() == 0) {
                    menuTreeNodeList.add(new MenuResourceTreeNode(module.get_id(), module.getName(), module.getParentId(), "#", NodeType.MODULE.getType(), NodeType.PROJECT.getType()));
                } else {
                    menuTreeNodeList.add(new MenuResourceTreeNode(module.get_id(), module.getName(), module.getParentId(), "#", NodeType.MODULE.getType(), NodeType.MODULE.getType()));
                }
            }
            if (!CollectionUtils.isEmpty(menuList)) {
                for (Menu menu : menuList) {
                    if (moduleExistMap.get(menu.getModuleId())) {
                        menuTreeNodeList.add(createMenuNode(menu));
                    }
                }
            }
        }
        return menuTreeNodeList;
    }


    private MenuResourceTreeNode createMenuNode(Menu menu) {
        return new MenuResourceTreeNode(menu.get_id(), menu.getName(), menu.getModuleId(), menu.getUrl(), NodeType.MENU.getType(), NodeType.MODULE.getType());
    }

    public boolean create(CreateMenuRequest createMenuRequest, Long enterpriseId) {
        Project project = projectDao.get(createMenuRequest.getProjectId(), enterpriseId);
        if (project == null)
            throw new BusinessHandleException("project.exception.not.exist");
        Module module = moduleDao.get(createMenuRequest.getModuleId());
        if (module == null)
            throw new BusinessHandleException("module.exception.not.exist", createMenuRequest.getModuleId());
        if (menuDao.checkNameExist(createMenuRequest.getName(), createMenuRequest.getModuleId()))
            throw new BusinessHandleException("menu.exception.name.duplicate", module.getName());
        Menu menu = DataConverter.copy(new Menu(), createMenuRequest);
        menu.set_id(idGeneratorDao.nextId(EntityUtil.getEntityName(Menu.class)));
        menu.setIndex(menuDao.nextIndex(createMenuRequest.getModuleId()));
        return menuDao.save(menu) > 0;
    }

    public boolean edit(EditMenuRequest editMenuRequest, Long enterpriseId) {
        Menu menu = menuDao.get(editMenuRequest.getId());
        Module module = moduleDao.get(editMenuRequest.getModuleId());
        if (module == null)
            throw new BusinessHandleException("module.exception.not.exist", editMenuRequest.getModuleId());
        if (menuDao.checkNameExistExceptItSelf(editMenuRequest.getName(), editMenuRequest.getModuleId(), editMenuRequest.getId()))
            throw new BusinessHandleException("menu.exception.name.duplicate", module.getName());

        menu = DataConverter.copy(menu, editMenuRequest);
        return menuDao.update(menu) > 0;
    }

    //todo add check
    public boolean delete(Long menuId, Long enterpriseId) {
        long rolePermissionCount = rolePermissionDao.countByMenu(menuId);
        if (rolePermissionCount > 0)
            throw new BusinessHandleException("operation.exception.inused");
        return menuDao.delete(menuId) > 0;
    }

}
