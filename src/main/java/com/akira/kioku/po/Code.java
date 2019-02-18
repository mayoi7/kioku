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
@Table(name = "code")
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
public class Code implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String code;

	private Long uid;

	@Column(name = "gmt_create")
	private java.util.Date gmtCreate;

	@Column(name = "gmt_modified")
	private java.util.Date gmtModified;

	public Code(String code) {
	    this.code = code;

	    id = null;
	    uid = null;
	    gmtCreate = null;
	    gmtModified = null;
    }

}
