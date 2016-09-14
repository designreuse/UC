package com.yealink.uc.web.modules.security.module.service;

import java.util.ArrayList;
import java.util.List;

import com.yealink.uc.common.modules.common.NodeType;
import com.yealink.uc.common.modules.security.module.entity.Module;
import com.yealink.uc.common.modules.security.module.vo.ModuleTreeNode;
import com.yealink.uc.common.modules.security.project.entity.Project;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.EntityUtil;
import com.yealink.uc.web.modules.common.dao.IdGeneratorDao;
import com.yealink.uc.web.modules.security.menu.dao.MenuDao;
import com.yealink.uc.web.modules.security.module.dao.ModuleDao;
import com.yealink.uc.web.modules.security.module.request.CreateModuleRequest;
import com.yealink.uc.web.modules.security.module.request.EditModuleRequest;
import com.yealink.uc.web.modules.security.project.dao.ProjectDao;
import com.yealink.uc.web.modules.security.resource.dao.ResourceDao;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class ModuleService {
    @Autowired
    ModuleDao moduleDao;
    @Autowired
    ProjectDao projectDao;
    @Autowired
    IdGeneratorDao idGeneratorDao;
    @Autowired
    MenuDao menuDao;
    @Autowired
    ResourceDao resourceDao;

    public List<Module> findAll(Long projectId) {
        return moduleDao.findByProject(projectId);
    }

    public List<Module> findByIds(List<Long> moduleIds) {
        return moduleDao.findByIds(moduleIds);
    }

    public Module get(Long moduleId) {
        return moduleDao.get(moduleId);
    }

    public List<ModuleTreeNode> showTreeNodes(Long projectId, Long enterpriseId) {
        Project project = projectDao.get(projectId, enterpriseId);
        if (projectDao.get(projectId, enterpriseId) == null) {
            throw new BusinessHandleException("project.exception.not.exist");
        }
        List<Module> moduleList = moduleDao.findByProject(projectId);
        return createModuleNodeList(moduleList, project);
    }

    public List<ModuleTreeNode> showTreeNodesForEdit(Long projectId, Long enterpriseId, Long moduleId) {
        Project project = projectDao.get(projectId, enterpriseId);
        if (projectDao.get(projectId, enterpriseId) == null) {
            throw new BusinessHandleException("project.exception.not.exist");
        }
        List<Module> moduleList = moduleDao.findByProject(projectId);

        List<Module> moduleResultList = new ArrayList<>(moduleList);
        List<Long> excludeModuleIds = new ArrayList<>();
        excludeModuleIds.add(moduleId);

        for (Module module : moduleList) {
            if (excludeModuleIds.contains(module.getParentId()) || excludeModuleIds.contains(module.get_id())) {
                excludeModuleIds.add(module.get_id());
                moduleResultList.remove(module);
            }
        }
        return createModuleNodeList(moduleResultList, project);
    }

    private List<ModuleTreeNode> createModuleNodeList(List<Module> moduleList, Project project) {
        List<ModuleTreeNode> moduleTreeNodes = new ArrayList<>();
        moduleTreeNodes.add(new ModuleTreeNode(0L, project.getName(), -1L, NodeType.PROJECT.getType(), ""));
        if (CollectionUtils.isNotEmpty(moduleList)) {
            for (Module module : moduleList) {
                if (module.getParentId() == 0) {
                    moduleTreeNodes.add(createModuleNode(module, NodeType.PROJECT.getType()));
                } else {
                    moduleTreeNodes.add(createModuleNode(module, NodeType.MODULE.getType()));
                }
            }
        }
        return moduleTreeNodes;
    }

    private ModuleTreeNode createModuleNode(Module module, String parentType) {
        return new ModuleTreeNode(module.get_id(), module.getName(),
            module.getParentId(), NodeType.MODULE.getType(), parentType);
    }


    public boolean create(CreateModuleRequest createModuleRequest, Long enterpriseId) {
        Project project = projectDao.get(createModuleRequest.getProjectId(), enterpriseId);
        if (project == null)
            throw new BusinessHandleException("project.exception.not.exist");
        if (moduleDao.checkNameExist(createModuleRequest.getName(), createModuleRequest.getProjectId()))
            throw new BusinessHandleException("module.exception.name.duplicate", createModuleRequest.getName());
        Module module = DataConverter.copy(new Module(), createModuleRequest);
        module.set_id(idGeneratorDao.nextId(EntityUtil.getEntityName(Module.class)));
        return moduleDao.save(module) > 0;
    }

    public boolean edit(EditModuleRequest editModuleRequest, Long enterpriseId) {
        Module module = moduleDao.get(editModuleRequest.getId());
        if (module == null)
            throw new BusinessHandleException("module.exception.not.exist", editModuleRequest.getName());
        if (moduleDao.checkNameExistExceptItSelf(editModuleRequest.getName(), module.getProjectId(), editModuleRequest.getId()))
            throw new BusinessHandleException("module.exception.name.duplicate", module.getName());

        module = DataConverter.copy(module, editModuleRequest);
        return moduleDao.update(module) > 0;
    }

    //todo add check
    public boolean delete(Long moduleId, Long enterpriseId) {
        long menuCount = menuDao.count(moduleId);
        if (menuCount > 0)
            throw new BusinessHandleException("module.exception.inused.menu");

        long resourceCount = resourceDao.count(moduleId);
        if (resourceCount > 0)
            throw new BusinessHandleException("module.exception.inused.resource");

        return moduleDao.delete(moduleId) > 0;
    }
}
