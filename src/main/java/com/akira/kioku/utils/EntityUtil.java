package com.akira.kioku.utils;

import com.akira.kioku.dto.*;
import com.akira.kioku.enums.RoleEnum;
import com.akira.kioku.po.Code;
import com.akira.kioku.po.User;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 负责对pojo进行一些处理
 * @author Kripath
 * @date Created in 11:56 2019/2/3
 */
@Slf4j
public class EntityUtil {

    /**
     * 将用户信息封装成用户对象，同时对密码进行加密
     * @return 封装后的用户对象
     */
    public static User encryptAndStorageAsUser(String username, String password,
                                               String email, String code) {
        // 对密码进行加密，用户名做盐值
        password = TokenUtil.makeTEncryptTokenBySaltedMd5(password, username).toString();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setCode(code);

        return user;
    }

    /**
     * 将{@link User}对象封装成{@link UserInfo}对象
     * @param user User类型的对象
     * @return UserInfo类型的对象
     */
    public static UserInfo packageUserAsUserInfo(User user) {
        if(user == null) {
            return null;
        }

        return new UserInfo(user.getId(), user.getUsername(), user.getRole());
    }

    /**
     * 将抽象接口的列表转换成DTO返回
     * @param notes {@link NotePackageable}的List
     * @return {@link NoteInfo}的List
     */
    public static List<NoteInfo> packageAbstractNotesToInfoList(List<NotePackageable> notes) {
        List<NoteInfo> list = new ArrayList<>(notes.size());
        for (NotePackageable note : notes) {
            list.add(new NoteInfo(note));
        }
        return list;
    }

    /**
     * 将数据封装成{@link UserDetail}对象
     * @param user 用户po类，其中password和gmt_modified对象不需要
     * @param noteCount 用户创建的日记总数
     * @return {@link UserDetail}
     */
    public static UserDetail packageToUserDetail(User user, Long noteCount) {
        UserDetail detail = new UserDetail(user);
        detail.setNoteCount(noteCount);
        return detail;
    }

    /**
     * 将字符串形式的邀请码封装成对象
     * @param strings 邀请码组成的集合
     * @return {@link Code}集合
     */
    public static List<Code> packageStringToCodeInList(List<String> strings) {
        List<Code> codes = new ArrayList<>(strings.size());
        for (String string : strings) {
            codes.add(new Code(string));
        }
        return codes;
    }

    /**
     * 将Code集合封装成CodeInfo集合
     * @param codes {@link Code}集合
     * @return {@link CodeInfo}集合
     */
    public static List<CodeInfo> packageCodeToCodeInfoInList(List<Code> codes) {
        List<CodeInfo> infos = new ArrayList<>(codes.size());

        for (Code code :codes) {
            infos.add(new CodeInfo(code));
        }

        return infos;
    }
}
