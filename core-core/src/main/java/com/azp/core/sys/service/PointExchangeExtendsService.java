package com.azp.core.sys.service;

import com.azp.core.sys.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/2/26 21:06
 */
@Service
public class PointExchangeExtendsService {

    @Autowired
    PointExchangeService pointExchangeService;
    @Autowired
    PointStoreService pointStoreService;
    @Autowired
    UserService userService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    GroupService groupService;

    //判空！！！！！！！！！
    public List<Map<String,Object>> getPointExchangeRecord(PointExchangeFilterMapper pointExchangeFilterMapper){
        List<Map<String,Object>> reList = new ArrayList<>();
        List<PointStore> pointStoreList = pointStoreService.getListByFilter(new PointStoreFilterMapper());
        List<PointExchange> pointExchangeList = pointExchangeService.getListByFilter(pointExchangeFilterMapper);

        List<User> userList = userService.getListByFilter(new UserFilterMapper());
        List<Department> departmentList = departmentService.getListByFilter(new DepartmentFilterMapper());
        List<Group> groupList = groupService.getListByFilter(new GroupFilterMapper());

        String userName = "";
        String userCode = "";
        String departmentName = "";
        String groupName = "";

        if (pointStoreList.isEmpty()||pointExchangeList.isEmpty()) return new ArrayList<>();

        if(userList.isEmpty()) PointExchangeRelationListEnum.User_List.setNotNUll(false);
        if(departmentList.isEmpty()) PointExchangeRelationListEnum.Department_List.setNotNUll(false);
        if(groupList.isEmpty()) PointExchangeRelationListEnum.Group_List.setNotNUll(false);

        for (PointExchange pointExchange : pointExchangeList){
            Map<String,Object> pointExchangeRecordMap = new HashMap<>();
            pointExchangeRecordMap.putAll(PointExchangeDetailMapper.buildMap(pointExchange));

            PointStore pointStore = getGoods(pointStoreList,pointExchange.getProductId());
            pointExchangeRecordMap.putAll(PointStoreDetailMapper.buildMap(pointStore));

            //以下是对用到的List和对象的判空处理
            if(PointExchangeRelationListEnum.User_List.isNotNUll()){
                User user = getUser(userList,pointExchange.getUserId());
                //user是其他的基础，所以判空要凌驾于部门和组别之上，而且如果user非空，则departmentId和groupId必定不是空
                if(user.getId()!=null){
                    userName = user.getName();
                    userCode = user.getUserCode();

                    if(PointExchangeRelationListEnum.Department_List.isNotNUll()){
                        Department department = getDepartment(departmentList,user.getDepartmentId());
                        if (department.getId()!=null){
                            departmentName = department.getName();
                        }
                    }


                    if(PointExchangeRelationListEnum.Group_List.isNotNUll()){
                        Group group = getGroup(groupList,user.getGroupId());
                        if(group.getId()!=null){
                            groupName = group.getGroupName();
                        }
                    }

                }

            }


            pointExchangeRecordMap.put("exchangeUser",userName);
            pointExchangeRecordMap.put("userCode",userCode);
            pointExchangeRecordMap.put("departmentName",departmentName);
            pointExchangeRecordMap.put("groupName",groupName);

            reList.add(pointExchangeRecordMap);


        }
        return reList;
    }





    public PointStore getGoods(List<PointStore> pointStoreList,String productId){
        for (PointStore entity:pointStoreList) {
            if(entity.getId().equals(productId)){
                return entity;
            }

        }

        return new PointStore();
    }

    public User getUser(List<User> userList,String userId){
        for (User entity:userList) {
            if(entity.getId().equals(userId)){
                return entity;
            }

        }

        return new User();
    }

    public Department getDepartment(List<Department> departmentList,String departmentId){
        for (Department entity:departmentList) {
            if(entity.getId().equals(departmentId)){
                return entity;
            }

        }

        return new Department();


    }

    public Group getGroup( List<Group> groupList,String groupId){
        for (Group entity:groupList) {
            if(entity.getId().equals(groupId)){
                return entity;
            }

        }

        return new Group();


    }
}
