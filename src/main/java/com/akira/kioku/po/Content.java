package com.akira.kioku.po;

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
@Table(name = "content")
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
public class Content implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private Long nid;

	private String content;

	@Column(name = "gmt_create")
	private java.util.Date gmtCreate;

	@Column(name = "gmt_modified")
	private java.util.Date gmtModified;

	public Content(Long nid, String content) {
		this.nid = nid;
		this.content = content;
	}

}
