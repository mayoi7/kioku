package com.akira.kioku.dto;

import com.akira.kioku.po.Note;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Kripath
 * @date Created in 11:28 2019/2/6
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteInfo implements Serializable {

    private Long id;

    private String title;

    private String content;

    private Long date;

    public NoteInfo(NotePackageable noter) {
        this.id = noter.getId();
        this.title = noter.getTitle();
        this.content = noter.getContent();
        // 时区问题，减去8个小时
        this.date = noter.getGmtCreate().getTime() - 28800000;
    }
}
