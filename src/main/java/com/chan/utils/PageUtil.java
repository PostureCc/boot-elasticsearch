package com.chan.utils;

import java.util.ArrayList;
import java.util.List;

public class PageUtil<T> {
    private List<T> bigList; // 大的集合，外界传入
    private int pageNum = 1; // 当前页号，外界传入
    private int pageSize = 20; // 每页条数，外界可以设定
    private List smallList; // 小的集合，返回
    private int pageCount; // 总页数
    private int total; // 总记录条数
    private int prePageIndex; // 上一页序号
    private int nextPageIndex; // 下一页序号
    private boolean isFirstPage; // 是否第一页
    private boolean isLastPage; // 是否最后一页
    private boolean hasNextPage; // 是否还有一页

    public void setCurentPageIndex(int pageNum, int pageSize) { // 每当页数改变，都会调用这个函数，筛选代码可以写在这里
        this.pageNum = pageNum;
        this.pageSize = pageSize;

        // 上一页，下一页确定
        prePageIndex = pageNum - 1;
        nextPageIndex = pageNum + 1;
        // 是否第一页，最后一页
        if (pageNum == 1) {
            isFirstPage = true;
        } else {
            isFirstPage = false;
        }
        if (pageNum == pageCount) {
            isLastPage = true;
        } else {
            isLastPage = false;
        }
        if (isLastPage) {
            hasNextPage = false;
        } else {
            hasNextPage = true;
        }

        // 筛选工作
//        smallList = new ArrayList<T>();
//        for (int i = (curentPageIndex - 1) * pageSize; i < curentPageIndex
//                * pageSize
//                && i < total; i++) {
//            smallList.add(bigList.get(i));
//        }
    }

    public int getPageNum() {
        return pageNum;
    }

    public List<T> getBigList() {
        return bigList;
    }

    public void createPage(List bigList, int pageNum, int pageSize, int total) {
        pageNum = pageNum == 0 ? 1 : pageNum;

        this.bigList = bigList;
        // 计算条数
        this.total = total;
        // 计算页数
        if (this.total % pageSize == 0) {
            pageCount = this.total / pageSize;
        } else {
            pageCount = this.total / pageSize + 1;
        }

        // 筛选工作
        smallList = new ArrayList<>();
        for (int i = (pageNum - 1) * pageSize; i < pageNum
                * pageSize
                && i < this.total; i++) {
            smallList.add(bigList.get(i));
        }

        this.setCurentPageIndex(pageNum, pageSize);
    }

    public int pageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> smallList() {
        return smallList;
    }

    public void setSmallList(ArrayList<T> smallList) {
        this.smallList = smallList;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int total() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getNextPageIndex() {
        return nextPageIndex;
    }

    public void setNextPageIndex(int nextPageIndex) {
        this.nextPageIndex = nextPageIndex;
    }

    public int getPrePageIndex() {
        return prePageIndex;
    }

    public void setPrePageIndex(int prePageIndex) {
        this.prePageIndex = prePageIndex;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public boolean hasNextPage() {
        return this.hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}
