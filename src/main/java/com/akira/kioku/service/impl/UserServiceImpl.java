package com.akira.kioku.service.impl;

import com.akira.kioku.constant.UserConstant;
import com.akira.kioku.dto.UserDetail;
import com.akira.kioku.po.User;
import com.akira.kioku.repository.UserRepository;
import com.akira.kioku.service.NoteService;
import com.akira.kioku.service.UserService;
import com.akira.kioku.utils.EntityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Kripath
 * @date Created in 16:58 2019/2/1
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final NoteService noteService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, NoteService noteService) {
        this.userRepository = userRepository;
        this.noteService = noteService;
    }

    @Override
    public String findUsernameById(Long uid) {
        Optional<User> user = userRepository.findById(uid);
        return user.get().getUsername();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Integer findRoleByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            return null;
        }
        return user.getRole();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User registerUser(User user) {
        // 设置权限为普通用户
        user.setRole(UserConstant.USER_ROLE);
        return userRepository.save(user);
    }

    @RequiresRoles({"admin"})
    @Override
    public List<UserDetail> listAllDetailInPage(Integer page) {
        Pageable pageable = PageRequest.of(page, UserConstant.PAGE_NUM);

        // 返回Page对象
        Page<User> pages = userRepository.listAllInPage(pageable);
        // 转换成list集合
        List<User> users = pages.getContent();

        int size = users.size();
        List<UserDetail> details = new ArrayList<>(size);

        for (User user : users) {
            details.add(EntityUtil.packageToUserDetail(
                    user, noteService.countNoteByUid(user.getId())));
        }

        return details;
    }

    @RequiresRoles({"admin"})
    @Override
    public Long countUser() {
        return userRepository.count();
    }

    @Override
    public UserDetail queryUserDetailByUsername(String username) {
        User user = userRepository.findByUsername(username);

        return EntityUtil.packageToUserDetail(user,
                noteService.countNoteByUid(user.getId()));
    }

    @Transactional(rollbackFor = Exception.class)
    @RequiresRoles({"admin"})
    @Override
    public boolean deleteByUsername(String username) {
        // 先查询是否有该用户
        User user = userRepository.findByUsername(username);
        if(user == null) {

            return false;
        }
        userRepository.delete(user);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @RequiresRoles({"admin"})
    @Override
    public boolean lockByUsername(String username) {
        return updateUserRole(username, UserConstant.LOCKED_ROLE);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequiresRoles({"admin"})
    @Override
    public boolean resetByUsername(String username) {
        return updateUserRole(username, UserConstant.USER_ROLE);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequiresRoles({"sp_admin"})
    @Override
    public boolean authorizeByUsername(String username) {
        return updateUserRole(username, UserConstant.ADMIN_ROLE);
    }

    @RequiresRoles({"admin"})
    private boolean updateUserRole(String username, Integer role) {
        // 先查询是否有该用户
        User user = userRepository.findByUsername(username);
        if(user == null) {
            return false;
        }
        user.setRole(role);
        userRepository.saveAndFlush(user);
        return true;
    }
}
