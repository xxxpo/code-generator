package com.xxxlin.core.jpa.page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Objects;

public class PageRange implements Pageable, Serializable {

	private static final long serialVersionUID = 1232825578694716871L;

	private final int limit;//界限
	private final int offset;//抵消
	private Sort sort;//分类

	public PageRange(int limit, int offset, Sort sort) {
		if (limit < 1) {
			throw new IllegalArgumentException("Page limit must not be less than one!");
			//页面限制不嫩少于  one
		}
		if (offset < 0) {
			throw new IllegalArgumentException("Page offset must not be less than zero!");
			//页偏移量不得小于零
		}
		this.limit = limit;
		this.offset = offset;
		this.sort = sort;
	}

	public PageRange(int limit, int offset) {
		this(limit, offset, Sort.unsorted());
	}

	public void setSort(Sort sort){
		this.sort = sort;
	}

	@Override
	public int getPageSize() {
		return limit;
	}

	@Override
	public int getPageNumber() {
		return 1;
	}

	@Override
	public long getOffset() {
		return offset;
	}

	@Override
	public boolean hasPrevious() {
		return false;
	}

	@Override
	public Pageable previousOrFirst() {
		return hasPrevious() ? previous() : first();
	}

	@Override
	public Pageable next(){
		return new PageRange(limit, 0);
	}

	public Pageable previous(){
		return new PageRange(limit, 0);
	}

	@Override
	public Pageable first(){
		return new PageRange(limit, 0);
	}

	@Override
	public Sort getSort() {
		return sort;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PageRange pageRange = (PageRange) o;
		return limit == pageRange.limit &&
				offset == pageRange.offset &&
				Objects.equals(sort, pageRange.sort);
	}

	@Override
	public int hashCode() {
		return Objects.hash(limit, offset, sort);
	}
}
