package com.yealink.uc.web.modules.security.project.service;

import java.util.List;

import com.yealink.uc.common.modules.security.project.entity.Project;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.EntityUtil;
import com.yealink.uc.web.modules.common.dao.IdGeneratorDao;
import com.yealink.uc.web.modules.security.menu.service.MenuService;
import com.yealink.uc.web.modules.security.module.dao.ModuleDao;
import com.yealink.uc.web.modules.security.project.dao.ProjectDao;
import com.yealink.uc.web.modules.security.project.request.CreateProjectRequest;
import com.yealink.uc.web.modules.security.project.request.EditProjectRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class ProjectService {
    @Autowired
    ProjectDao projectDao;
    @Autowired
    IdGeneratorDao idGeneratorDao;

    @Autowired
    MenuService menuService;
    @Autowired
    ModuleDao moduleDao;

    public Project get(Long projectId, Long enterpriseId) {
        return projectDao.get(projectId, enterpriseId);
    }

    public List<Project> findAllByEnterprise(Long enterpriseId) {
        return projectDao.findByEnterprise(enterpriseId);
    }

    public List<Long> findIdsByEnterprise(Long enterpriseId) {
        return projectDao.findIdsByEnterprise(enterpriseId);
    }

    public boolean create(CreateProjectRequest createProjectRequest, Long enterpriseId) {
        validateNameDuplicate(createProjectRequest.getName(), enterpriseId);
        Project project = DataConverter.copy(new Project(), createProjectRequest);
        project.set_id(idGeneratorDao.nextId(EntityUtil.getEntityName(Project.class)));
        project.setPlatformEnterpriseId(enterpriseId);
        return projectDao.save(project) > 0;
    }

    public boolean edit(EditProjectRequest editProjectRequest, Long enterpriseId) {
        Project project = projectDao.get(editProjectRequest.getId(), enterpriseId);
        if (project == null)
            throw new BusinessHandleException("project.exception.not.exist");
        validateNameDuplicate(editProjectRequest.getName(), enterpriseId, editProjectRequest.getId());
        project = DataConverter.copy(project, editProjectRequest);
        return projectDao.update(project) > 0;
    }

    private void validateNameDuplicate(String name, Long enterpriseId) {
        if (projectDao.checkNameExist(name, enterpriseId))
            throw new BusinessHandleException("project.exception.name.duplicate", name);
    }

    private void validateNameDuplicate(String name, Long enterpriseId, Long projectId) {
        if (projectDao.checkNameExceptItSelt(name, enterpriseId, projectId))
            throw new BusinessHandleException("project.exception.name.duplicate", name);
    }

    public boolean delete(Long projectId, Long enterpriseId) {
        long projectCount = moduleDao.count(projectId);
        if (projectCount > 0)
            throw new BusinessHandleException("project.exception.inused");
        return projectDao.delete(projectId, enterpriseId) > 0;
    }
}
