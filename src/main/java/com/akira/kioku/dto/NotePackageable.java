package com.akira.kioku.dto;

import java.util.Date;

/**
 * @author Kripath
 * @date Created in 11:47 2019/2/6
 */
public interface NotePackageable {

    Long getId();

    String getTitle();

    String getContent();

    Date getGmtCreate();
}
