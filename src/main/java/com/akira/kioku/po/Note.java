package com.akira.kioku.po;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 刘昊楠
 */
@Data
@Entity
@Table(name = "note")
public class Note implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String title;

	@Column(name = "gmt_create")
	private java.util.Date gmtCreate;

	@Column(name = "gmt_modified")
	private java.util.Date gmtModified;

}
