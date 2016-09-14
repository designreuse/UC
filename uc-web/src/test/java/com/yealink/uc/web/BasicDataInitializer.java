package com.yealink.uc.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.uc.platform.utils.EntityUtil;
import com.yealink.uc.common.modules.enterprise.entity.Enterprise;
import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.web.modules.common.dao.IdGeneratorDao;
import com.yealink.uc.web.modules.org.dao.OrgDao;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ChNan
 */
public class BasicDataInitializer {
    private IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();
    @Autowired
    IdGeneratorDao idGeneratorDao;
    @Autowired
    OrgDao orgDao;
    Map<String, Object> dataMap = new HashMap<>();

    @PostConstruct
    public void initData() {
        Enterprise enterprise = mockEnterprise();
        Org rootOrg = mockRootOrg(enterprise);
    }

    private Enterprise mockEnterprise() {
        Enterprise enterprise = new Enterprise();
        enterprise.set_id(Long.MAX_VALUE);
        enterprise.setName("厦门亿联网络技术股份有限公司_Test_" + enterprise.get_id());
        enterprise.setDomain("yealink.com");
        enterprise.setModificationDate(System.currentTimeMillis());
        enterprise.setEmail("fangwq@yealink.com");
        remoteDataService.insertOne(EntityUtil.getEntityName(Enterprise.class), enterprise);
        dataMap.put("enterprise", enterprise);
        return enterprise;
    }

    private Org mockRootOrg(Enterprise enterprise) {
        Org rootOrg = new Org();
        rootOrg.set_id(Long.MAX_VALUE);
        rootOrg.setIndex(1);
        rootOrg.setName(enterprise.getName());
        rootOrg.setModificationDate(System.currentTimeMillis());
        rootOrg.setOrgPath(String.valueOf(rootOrg.get_id()));
        rootOrg.setIsExtAssistance(false);
        rootOrg.setMail(enterprise.getEmail());
        rootOrg.setEnterpriseId(enterprise.get_id());
        rootOrg.setParentId(0L);
        remoteDataService.insertOne(EntityUtil.getEntityName(Org.class), rootOrg);
        dataMap.put("rootOrg", rootOrg);
        return rootOrg;
    }

    // todo 1:独立一台单独的单元测试数据库出来，测试完成之后，整个数据库全部清空
    @PreDestroy
    public void destroyData() {
        Enterprise enterprise = (Enterprise) dataMap.get("enterprise");
        remoteDataService.deleteOne(EntityUtil.getEntityName(Enterprise.class), enterprise.get_id());
        List<Org> orgList = orgDao.findAll(Long.MAX_VALUE);
        for (Org org : orgList) {
            orgDao.delete(org.get_id());
        }
    }
}
