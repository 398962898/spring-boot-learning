package pers.yuiz.common;

public class PageDTO {
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
}
