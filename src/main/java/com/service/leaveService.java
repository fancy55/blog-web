package com.service;

import com.dto.LeaveDTO;
import com.mapper.LeaveMapper;
import com.mapper.UserMapper;
import com.model.Leave;
import com.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service //组装usermapper和leavemapper需要一个中间层
public class leaveService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LeaveMapper leaveMapper;

    public List<LeaveDTO> getLeave() {
        List<Leave> leaves = leaveMapper.getLeave();
        List<LeaveDTO> leaveDTOS = new ArrayList<>();
        for(Leave leave:leaves){
            User user = userMapper.findById(leave.getCreator());
            LeaveDTO leaveDTO = new LeaveDTO();
            //把leave对象换到dto中
            BeanUtils.copyProperties(leave,leaveDTO);//(1,2)快速把1的所有属性拷贝到2中
            leaveDTO.setUser(user);
            //System.out.println(user.toString()); //下划线——驼峰
            leaveDTOS.add(leaveDTO);
        }
        return leaveDTOS;
    }
}
