package com.yealink.uc.web.modules.security.resourceoperation.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yealink.uc.common.modules.common.NodeType;
import com.yealink.uc.common.modules.security.menu.vo.MenuResourceTreeNode;
import com.yealink.uc.common.modules.security.module.entity.Module;
import com.yealink.uc.common.modules.security.operation.entity.Operation;
import com.yealink.uc.common.modules.security.project.entity.Project;
import com.yealink.uc.common.modules.security.resource.entity.Resource;
import com.yealink.uc.common.modules.security.resourceoperation.ResourceOperation;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.EntityUtil;
import com.yealink.uc.web.modules.common.dao.IdGeneratorDao;
import com.yealink.uc.web.modules.security.module.dao.ModuleDao;
import com.yealink.uc.web.modules.security.operation.dao.OperationDao;
import com.yealink.uc.web.modules.security.project.dao.ProjectDao;
import com.yealink.uc.web.modules.security.resource.dao.ResourceDao;
import com.yealink.uc.web.modules.security.resourceoperation.dao.ResourceOperationDao;
import com.yealink.uc.web.modules.security.resourceoperation.request.CreateResourceOperationRequest;
import com.yealink.uc.web.modules.security.rolepermission.dao.RolePermissionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author ChNan
 */
@Service
public class ResourceOperationService {
    @Autowired
    ResourceOperationDao resourceOperationDao;
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

    public ResourceOperation get(Long resourceId) {
        return resourceOperationDao.get(resourceId);
    }

    public List<ResourceOperation> find(List<Long> resourceOperationIds) {
        return resourceOperationDao.findByIds(resourceOperationIds);
    }

    public List<MenuResourceTreeNode> showTreeNodes(Long projectId, Long enterpriseId) {
        List<ResourceOperation> resourceOperationList = resourceOperationDao.findByEnterprise(enterpriseId);
        Map<Long, List<ResourceOperation>> resourceOperationsMap = createResourceOperationsMap(resourceOperationList);
        List<Long> resourceIds = Lists.transform(resourceOperationList, new Function<ResourceOperation, Long>() {
            @Override
            public Long apply(final ResourceOperation input) {
                return input.getResourceId();
            }
        });
        List<Long> operationIds = Lists.transform(resourceOperationList, new Function<ResourceOperation, Long>() {
            @Override
            public Long apply(final ResourceOperation input) {
                return input.getOperationId();
            }
        });
        List<Resource> resources = resourceDao.findByIds(resourceIds);
        List<Operation> operations = operationDao.findByIds(operationIds);
        Map<Long, Resource> resourceIdMap = extractResourceMap(resources);
        Map<Long, Operation> operationIdMap = extractOperationMap(operations);
        Project project = projectDao.get(projectId, enterpriseId);
        if (projectDao.get(projectId, enterpriseId) == null) {
            throw new BusinessHandleException("project.exception.not.exist");
        }
        List<Module> moduleList = moduleDao.findByProject(projectId);
        Map<Long, Module> moduleExistMap = new HashMap<>();
        List<MenuResourceTreeNode> resourceTreeNodeList = new ArrayList<>();
        createProjectNode(project, resourceTreeNodeList);
        if (!CollectionUtils.isEmpty(moduleList)) {
            createModuleNode(moduleList, moduleExistMap, resourceTreeNodeList);
            if (!CollectionUtils.isEmpty(resourceOperationList)) {
                createResourceOperationNode(resourceOperationsMap, resourceIdMap, operationIdMap, moduleExistMap, resourceTreeNodeList);
            }
        }
        return resourceTreeNodeList;
    }

    public List<MenuResourceTreeNode> showTreeNodesForAuthorize(Long projectId, Long enterpriseId) {
        List<ResourceOperation> resourceOperationList = resourceOperationDao.findByEnterprise(enterpriseId);
        Map<Long, List<ResourceOperation>> resourceOperationsMap = createResourceOperationsMap(resourceOperationList);
        List<Long> resourceIds = Lists.transform(resourceOperationList, new Function<ResourceOperation, Long>() {
            @Override
            public Long apply(final ResourceOperation input) {
                return input.getResourceId();
            }
        });
        List<Long> operationIds = Lists.transform(resourceOperationList, new Function<ResourceOperation, Long>() {
            @Override
            public Long apply(final ResourceOperation input) {
                return input.getOperationId();
            }
        });
        List<Resource> resources = resourceDao.findByIds(resourceIds);
        List<Operation> operations = operationDao.findByIds(operationIds);
        Map<Long, Resource> resourceIdMap = extractResourceMap(resources);
        Map<Long, Operation> operationIdMap = extractOperationMap(operations);
        Project project = projectDao.get(projectId, enterpriseId);
        if (projectDao.get(projectId, enterpriseId) == null) {
            throw new BusinessHandleException("project.exception.not.exist");
        }
        List<Module> moduleList = moduleDao.findByProject(projectId);
        Map<Long, Module> moduleExistMap = new HashMap<>();
        List<MenuResourceTreeNode> resourceTreeNodeList = new ArrayList<>();
        createProjectNode(project, resourceTreeNodeList);
        if (!CollectionUtils.isEmpty(moduleList)) {
            createModuleNode(moduleList, moduleExistMap, resourceTreeNodeList);
            if (!CollectionUtils.isEmpty(resourceOperationList)) {
                createResourceOperationNodeAuthorize(resourceOperationsMap, resourceIdMap, operationIdMap, moduleExistMap, resourceTreeNodeList);
            }
        }
        return resourceTreeNodeList;
    }

    private Map<Long, Operation> extractOperationMap(final List<Operation> operations) {
        return Maps.uniqueIndex(operations.iterator(), new Function<Operation, Long>() {
            @Override
            public Long apply(final Operation input) {
                return input.get_id();
            }
        });
    }

    private Map<Long, Resource> extractResourceMap(final List<Resource> resources) {
        return Maps.uniqueIndex(resources.iterator(), new Function<Resource, Long>() {
            @Override
            public Long apply(final Resource input) {
                return input.get_id();
            }
        });
    }

    private Map<Long, List<ResourceOperation>> createResourceOperationsMap(final List<ResourceOperation> resourceOperationList) {
        Map<Long, List<ResourceOperation>> resourceOperationsMap = new HashMap<>();
        for (ResourceOperation resourceOperation : resourceOperationList) {
            if (resourceOperationsMap.get(resourceOperation.getResourceId()) == null) {
                List<ResourceOperation> resourceOperations = new ArrayList<>();
                resourceOperations.add(resourceOperation);
                resourceOperationsMap.put(resourceOperation.getResourceId(), resourceOperations);
            } else {
                resourceOperationsMap.get(resourceOperation.getResourceId()).add(resourceOperation);
            }
        }
        return resourceOperationsMap;
    }

    private void createProjectNode(final Project project, final List<MenuResourceTreeNode> resourceTreeNodeList) {
        resourceTreeNodeList.add(new MenuResourceTreeNode(0L, project.getName(), -1L, "#", NodeType.PROJECT.getType(), ""));
    }

    private void createModuleNode(final List<Module> moduleList, final Map<Long, Module> moduleExistMap, final List<MenuResourceTreeNode> resourceTreeNodeList) {
        for (Module module : moduleList) {
            moduleExistMap.put(module.get_id(), module);
            if (module.getParentId() == 0) {
                resourceTreeNodeList.add(new MenuResourceTreeNode(module.get_id(), module.getName(), module.getParentId(), "#", NodeType.MODULE.getType(), NodeType.PROJECT.getType()));
            } else {
                resourceTreeNodeList.add(new MenuResourceTreeNode(module.get_id(), module.getName(), module.getParentId(), "#", NodeType.MODULE.getType(), NodeType.MODULE.getType()));
            }
        }
    }

    private void createResourceOperationNode(final Map<Long, List<ResourceOperation>> resourceOperationMap, final Map<Long, Resource> resourceMap, final Map<Long, Operation> operationMap, final Map<Long, Module> moduleExistMap, final List<MenuResourceTreeNode> resourceTreeNodeList) {
        for (Map.Entry<Long, List<ResourceOperation>> entry : resourceOperationMap.entrySet()) {
            Resource resource = resourceMap.get(entry.getKey());
            if (moduleExistMap.get(resource.getModuleId()) != null) {
                resourceTreeNodeList.add(createResourceNode(resource));
                List<ResourceOperation> operationsInResource = entry.getValue();
                for (ResourceOperation resourceOperation : operationsInResource) {
                    Operation operation = operationMap.get(resourceOperation.getOperationId());
                    resourceTreeNodeList.add(createOperationNode(resource, operation));
                }
            }
        }
    }

    private void createResourceOperationNodeAuthorize(final Map<Long, List<ResourceOperation>> resourceOperationMap, final Map<Long, Resource> resourceMap, final Map<Long, Operation> operationMap,
                                                      final Map<Long, Module> moduleExistMap, final List<MenuResourceTreeNode> resourceTreeNodeList) {
        for (Map.Entry<Long, List<ResourceOperation>> entry : resourceOperationMap.entrySet()) {
            Resource resource = resourceMap.get(entry.getKey());
            if (moduleExistMap.get(resource.getModuleId()) != null) {
                List<ResourceOperation> operationsInResource = entry.getValue();
                for (ResourceOperation resourceOperation : operationsInResource) {
                    Operation operation = operationMap.get(resourceOperation.getOperationId());
                    resourceTreeNodeList.add(createResourceOperationNode(resourceOperation, resource, operation));
                }
            }
        }
    }

    private MenuResourceTreeNode createResourceNode(Resource resource) {
        return new MenuResourceTreeNode(resource.get_id(), resource.getName(), resource.getModuleId(), "", NodeType.RESOURCE.getType(), NodeType.MODULE.getType());
    }

    private MenuResourceTreeNode createOperationNode(Resource resource, Operation operation) {
        return new MenuResourceTreeNode(operation.get_id(), operation.getName(), resource.get_id(), "", NodeType.OPERATION.getType(), NodeType.RESOURCE.getType());
    }

    private MenuResourceTreeNode createResourceOperationNode(ResourceOperation resourceOperation, Resource resource, Operation operation) {
        return new MenuResourceTreeNode(resourceOperation.get_id(), operation.getName() + resource.getName(), resource.getModuleId(), "", NodeType.RESOURCE_OPERATION.getType(), NodeType.MODULE.getType());
    }

    public boolean create(CreateResourceOperationRequest createResourceOperationRequest, Long enterpriseId) {
        Resource resource = resourceDao.get(createResourceOperationRequest.getResourceId());
        if (resource == null)
            throw new BusinessHandleException("resource.exception.not.exist");
        Operation operation = operationDao.get(createResourceOperationRequest.getOperationId());
        if (operation == null)
            throw new BusinessHandleException("operation.exception.not.exist");
        if (resourceOperationDao.checkExist(createResourceOperationRequest.getResourceId(), createResourceOperationRequest.getResourceId(), enterpriseId)) {
            throw new BusinessHandleException("resource.operation.exception.exist");
        }
        ResourceOperation resourceOperation = DataConverter.copy(new ResourceOperation(), createResourceOperationRequest);
        resourceOperation.set_id(idGeneratorDao.nextId(EntityUtil.getEntityName(ResourceOperation.class)));
        resourceOperation.setPlatformEnterpriseId(enterpriseId);
        return resourceOperationDao.save(resourceOperation) > 0;
    }

    //todo add check
    public boolean delete(Long resourceId, Long operationId, Long enterpriseId) {
        ResourceOperation resourceOperation = resourceOperationDao.get(resourceId, operationId, enterpriseId);
        if (rolePermissionDao.countByOperation(resourceOperation.get_id()) > 0) {
            throw new BusinessHandleException("resource.operation.exception.in.use");
        }
        return resourceOperationDao.delete(resourceId, operationId, enterpriseId) > 0;
    }

}
