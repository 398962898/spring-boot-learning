package pers.yuiz.common.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * 分页请求传输类
 */
public class PageDTO implements Serializable{
    /**
     * 当前页
     */
    private Integer pageNum;
    /**
     * 每页记录数目
     */
    private Integer pageSize;
    /**
     * 排序方式
     */
    private Integer sort;

    public Integer getPageNum() {
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        if (pageSize == null || pageSize < 0) {
            pageSize = 0;
        }
        this.sort = sort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageDTO that = (PageDTO) o;

        return Objects.equals(this.pageNum, that.pageNum) &&
                Objects.equals(this.pageSize, that.pageSize) &&
                Objects.equals(this.sort, that.sort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageNum, pageSize, sort);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("pageNum = " + pageNum)
                .add("pageSize = " + pageSize)
                .add("sort = " + sort)
                .toString();
    }
}
