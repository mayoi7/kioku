package com.akira.kioku.service.impl;

import com.akira.kioku.po.Code;
import com.akira.kioku.repository.CodeRepository;
import com.akira.kioku.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kripath
 * @date Created in 11:07 2019/2/3
 */
@Service
public class CodeServiceImpl implements CodeService {

    private final CodeRepository codeRepository;

    @Autowired
    public CodeServiceImpl(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
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
}
