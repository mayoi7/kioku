package com.akira.kioku.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 刘昊楠
 */
@Data
@Entity
@Table(name = "note")
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
public class Note implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private Long uid;

	private String title;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@Column(name = "gmt_create")
	private java.util.Date gmtCreate;

	@Column(name = "gmt_modified")
	private java.util.Date gmtModified;

	public Note(Long uid, String title) {
	    this.uid = uid;
	    this.title = title;
    }

}
