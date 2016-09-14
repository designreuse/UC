package com.yealink.uc.web.modules.staff.dao;

import javax.annotation.Resource;

import com.yealink.uc.web.SpringTestInitializer;
import com.yealink.uc.web.modules.staff.request.QueryStaffRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** 
* StaffDao Tester. 
* 
* @author yewl
*/
public class StaffDaoTest extends SpringTestInitializer{
    @Resource
    private StaffDao staffDao;

    @Before
    public void before() throws Exception { 
    } 

    @After
    public void after() throws Exception { 
    } 
    
        /** 
    * 
    * Method: findAllAvailable(Long enterpriseId) 
    * 
    */ 
    @Test
    public void testFindAllAvailable() throws Exception { 
        //TODO: Test goes here... 
    } 
    
        /** 
    * 
    * Method: findStaffs(List<Long> staffIdList, Long enterpriseId) 
    * 
    */ 
    @Test
    public void testFindStaffs() throws Exception { 
        //TODO: Test goes here... 
    } 
    
        /** 
    * 
    * Method: findStaffIds(List<Long> staffIdList, Long enterpriseId) 
    * 
    */ 
    @Test
    public void testFindStaffIds() throws Exception { 
        //TODO: Test goes here... 
    } 
    
        /** 
    * 
    * Method: get(Long staffId) 
    * 
    */ 
    @Test
    public void testGetStaffId() throws Exception { 
        //TODO: Test goes here... 
    } 
    
        /** 
    * 
    * Method: get(Long staffId, Long enterpriseId) 
    * 
    */ 
    @Test
    public void testGetForStaffIdEnterpriseId() throws Exception { 
        //TODO: Test goes here... 
    } 
    
        /** 
    * 
    * Method: countUsername(String username, Long enterpriseId) 
    * 
    */ 
    @Test
    public void testCountUsername() throws Exception { 
        //TODO: Test goes here... 
    } 
    
        /** 
    * 
    * Method: countUsernameExceptSelf(String username, Long staffId, Long enterpriseId) 
    * 
    */ 
    @Test
    public void testCountUsernameExceptSelf() throws Exception { 
        //TODO: Test goes here... 
    } 
    
        /** 
    * 
    * Method: save(Staff staff) 
    * 
    */ 
    @Test
    public void testSave() throws Exception { 
        //TODO: Test goes here... 
    } 
    
        /** 
    * 
    * Method: updateStaff(Staff staff) 
    * 
    */ 
    @Test
    public void testUpdateStaff() throws Exception { 
        //TODO: Test goes here... 
    } 
    
        /** 
    * 
    * Method: updateStaffStatus(Long staffId, Long enterpriseId, StaffStatus status) 
    * 
    */ 
    @Test
    public void testUpdateStaffStatus() throws Exception { 
        //TODO: Test goes here... 
    } 
    
        /** 
    * 
    * Method: batchUpdateStaffStatus(List<Long> staffIds, Long enterpriseId, StaffStatus status) 
    * 
    */ 
    @Test
    public void testBatchUpdateStaffStatus() throws Exception { 
        //TODO: Test goes here... 
    } 
    
        /** 
    * 
    * Method: updateStaffPassword(Long staffId, Long enterpriseId, String newPassword) 
    * 
    */ 
    @Test
    public void testUpdateStaffPassword() throws Exception { 
        //TODO: Test goes here... 
    } 
    
        /** 
    * 
    * Method: moveStaff(Long staffId, Long enterpriseId, List<StaffOrgMapping> staffOrgMappings) 
    * 
    */ 
    @Test
    public void testMoveStaff() throws Exception { 
        //TODO: Test goes here... 
    } 
    
        /** 
    * 
    * Method: updateStaffIndexInOrg(Long staffId, StaffOrgMapping newStaffOrgMapping) 
    * 
    */ 
    @Test
    public void testUpdateStaffIndexInOrg() throws Exception { 
        //TODO: Test goes here... 
    } 
    
        /** 
    * 
    * Method: findExistUsername(List<String> usernameList, Long enterpriseId) 
    * 
    */ 
    @Test
    public void testFindExistUsername() throws Exception { 
        //TODO: Test goes here... 
    } 
    
        /** 
    * 
    * Method: batchCreateStaff(List<Staff> staffList) 
    * 
    */ 
    @Test
    public void testBatchCreateStaff() throws Exception { 
        //TODO: Test goes here... 
    } 
    
        /** 
    * 
    * Method: updateStaffDomain(String domain, Long enterpriseId) 
    * 
    */ 
    @Test
    public void testUpdateStaffDomain() throws Exception { 
        //TODO: Test goes here... 
    } 
    
        /** 
    * 
    * Method: queryStaffList(QueryStaffRequest queryStaffRequest) 
    * 
    */ 
    @Test
    public void testQueryStaffList() throws Exception {
        QueryStaffRequest queryStaffRequest = new QueryStaffRequest();
        queryStaffRequest.setSearchKey("zhangsan");
        queryStaffRequest.setPageNo(1);
        staffDao.queryStaffList(queryStaffRequest, 1L);
    }
    
        /**
    * 
    * Method: mapConvertToStaffs(List<Map<String, Object>> staffMapList) 
    * 
    */ 
    @Test
    public void testMapConvertToStaffs() throws Exception { 
    //TODO: Test goes here... 
        /* 
        try { 
           Method method = StaffDao.getClass().getMethod("mapConvertToStaffs", List<Map<String,.class); 
           method.setAccessible(true); 
           method.invoke(<Object>, <Parameters>); 
        } catch(NoSuchMethodException e) { 
        } catch(IllegalAccessException e) { 
        } catch(InvocationTargetException e) { 
        } 
        */ 
    }
}
