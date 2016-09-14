package com.yealink.uc.web.modules.security.resource.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yealink.uc.common.modules.common.NodeType;
import com.yealink.uc.common.modules.security.menu.vo.MenuResourceTreeNode;
import com.yealink.uc.common.modules.security.module.entity.Module;
import com.yealink.uc.common.modules.security.project.entity.Project;
import com.yealink.uc.common.modules.security.resource.entity.Resource;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.EntityUtil;
import com.yealink.uc.web.modules.common.dao.IdGeneratorDao;
import com.yealink.uc.web.modules.security.module.dao.ModuleDao;
import com.yealink.uc.web.modules.security.operation.dao.OperationDao;
import com.yealink.uc.web.modules.security.project.dao.ProjectDao;
import com.yealink.uc.web.modules.security.resource.dao.ResourceDao;
import com.yealink.uc.web.modules.security.resource.request.CreateResourceRequest;
import com.yealink.uc.web.modules.security.resource.request.EditResourceRequest;
import com.yealink.uc.web.modules.security.resourceoperation.dao.ResourceOperationDao;
import com.yealink.uc.web.modules.security.rolepermission.dao.RolePermissionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author ChNan
 */
@Service
public class ResourceService {
    @Autowired
    ResourceDao resourceDao;
    @Autowired
    ProjectDao projectDao;
    @Autowired
    ModuleDao moduleDao;

    @Autowired
    IdGeneratorDao idGeneratorDao;

    @Autowired
    OperationDao operationDao;
    @Autowired
    RolePermissionDao rolePermissionDao;
    @Autowired
    ResourceOperationDao resourceOperationDao;

    public Resource get(Long resourceId) {
        return resourceDao.get(resourceId);
    }

    public List<MenuResourceTreeNode> showTreeNodes(Long projectId, Long enterpriseId) {
        Project project = projectDao.get(projectId, enterpriseId);
        if (projectDao.get(projectId, enterpriseId) == null) {
            throw new BusinessHandleException("project.exception.not.exist");
        }
        List<Resource> resourceList = resourceDao.findByProject(projectId);
        List<Module> moduleList = moduleDao.findByProject(projectId);

        Map<Long, Boolean> moduleExistMap = new HashMap<>();
        List<MenuResourceTreeNode> resourceTreeNodeList = new ArrayList<>();
        resourceTreeNodeList.add(new MenuResourceTreeNode(0L, project.getName(), -1L, "#", NodeType.PROJECT.getType(), ""));
        if (!CollectionUtils.isEmpty(moduleList)) {
            for (Module module : moduleList) {
                moduleExistMap.put(module.get_id(), true);
                if (module.getParentId() == 0) {
                    resourceTreeNodeList.add(new MenuResourceTreeNode(module.get_id(), module.getName(), module.getParentId(), "#", NodeType.MODULE.getType(), NodeType.PROJECT.getType()));
                } else {
                    resourceTreeNodeList.add(new MenuResourceTreeNode(module.get_id(), module.getName(), module.getParentId(), "#", NodeType.MODULE.getType(), NodeType.MODULE.getType()));
                }
            }
            if (!CollectionUtils.isEmpty(resourceList)) {
                for (Resource resource : resourceList) {
                    if (moduleExistMap.get(resource.getModuleId())) {
                        resourceTreeNodeList.add(createResourceNode(resource));
                    }
                }
            }

        }
        return resourceTreeNodeList;
    }

    private MenuResourceTreeNode createResourceNode(Resource resource) {
        return new MenuResourceTreeNode(resource.get_id(), resource.getName(), resource.getModuleId(), "", NodeType.RESOURCE.getType(), NodeType.MODULE.getType());
    }

    public boolean create(CreateResourceRequest createResourceRequest, Long enterpriseId) {
        Project project = projectDao.get(createResourceRequest.getProjectId(), enterpriseId);
        if (project == null)
            throw new BusinessHandleException("project.exception.not.exist");
        Module module = moduleDao.get(createResourceRequest.getModuleId());
        if (module == null)
            throw new BusinessHandleException("module.exception.not.exist", createResourceRequest.getModuleId());
        if (resourceDao.checkNameExist(createResourceRequest.getName(), createResourceRequest.getModuleId()))
            throw new BusinessHandleException("resource.exception.create.error.name.duplicate", module.getName());
        Resource resource = DataConverter.copy(new Resource(), createResourceRequest);
        resource.set_id(idGeneratorDao.nextId(EntityUtil.getEntityName(Resource.class)));
        return resourceDao.save(resource) > 0;
    }

    public boolean edit(EditResourceRequest editResourceRequest, Long enterpriseId) {
        Resource resource = resourceDao.get(editResourceRequest.getId());
        Module module = moduleDao.get(editResourceRequest.getModuleId());
        if (module == null)
            throw new BusinessHandleException("module.exception.not.exist", editResourceRequest.getModuleId());
        if (resourceDao.checkNameExistExceptItSelf(editResourceRequest.getName(), editResourceRequest.getModuleId(), editResourceRequest.getId()))
            throw new BusinessHandleException("resource.exception.name.duplicate", module.getName());

        resource = DataConverter.copy(resource, editResourceRequest);
        return resourceDao.update(resource) > 0;
    }

    //todo add check
    public boolean delete(Long resourceId, Long enterpriseId) {
        long resourceOperationCount = resourceOperationDao.countByResource(resourceId);
        if (resourceOperationCount > 0)
            throw new BusinessHandleException("resource.exception.inused");
        return resourceDao.delete(resourceId) > 0;
    }

}
