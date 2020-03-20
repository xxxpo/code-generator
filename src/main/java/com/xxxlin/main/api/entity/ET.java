package com.xxxlin.main.api.entity;

import com.xxxlin.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 *
 * @author XiaoLin
 *
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "et")
public class ET extends BaseEntity<String> {

	@Id
	@Column()
	private String id;

	@Column(nullable=false)
	private Long createTime;

	@Column(nullable=false)
	private Long updateTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ET et = (ET) o;
		return Objects.equals(id, et.id) &&
				Objects.equals(createTime, et.createTime) &&
				Objects.equals(updateTime, et.updateTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, createTime, updateTime);
	}
}
