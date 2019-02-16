package com.akira.kioku.service.impl;

import com.akira.kioku.dto.CodeInfo;
import com.akira.kioku.po.Code;
import com.akira.kioku.repository.CodeRepository;
import com.akira.kioku.service.CodeService;
import com.akira.kioku.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kripath
 * @date Created in 11:07 2019/2/3
 */
@Service
@Slf4j
public class CodeServiceImpl implements CodeService {

    private final CodeRepository codeRepository;

    private final UserService userService;

    @Autowired
    public CodeServiceImpl(CodeRepository codeRepository, UserService userService) {
        this.codeRepository = codeRepository;
        this.userService = userService;
    }

    @Override
    public Code findByCode(String code) {
        return codeRepository.findByCode(code);
    }

    @Override
    public void setUsed(String code, Long uid) {
        Code record = codeRepository.findByCode(code);
        record.setUid(uid);

        codeRepository.save(record);
    }

    @Override
    public List<CodeInfo> listAllAsCodeInfo() {
        List<Code> codes = codeRepository.findAllOrderByIdDesc();
        List<CodeInfo> infos = new ArrayList<>(codes.size());

        for (Code code :codes) {
            if(code.getUid() == null) {
                infos.add(new CodeInfo(code.getId(), code.getCode(), null, null));
            } else {
                infos.add(new CodeInfo(code.getId(), code.getCode(),
                        userService.findUsernameById(code.getUid()),
                        code.getGmtModified()));
            }
        }
        
        return infos;
    }
}
