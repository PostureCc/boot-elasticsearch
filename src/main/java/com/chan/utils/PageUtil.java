package com.chan.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PageUtil<T> {
    private List<T> bigList; // 大的集合，外界传入
    private int pageNum = 0; // 当前页号，外界传入
    private int pageSize = 20; // 每页条数，外界可以设定
    private List list; // 小的集合，返回
    private int pageCount; // 总页数
    private int total; // 总记录条数
    private int prePageIndex; // 上一页序号
    private int nextPageIndex; // 下一页序号
    private boolean isFirstPage; // 是否第一页
    private boolean isLastPage; // 是否最后一页
    private boolean hasNextPage; // 是否还有一页

    public void setCurentPageIndex(int pageNum, int pageSize, int total, boolean b) { // 每当页数改变，都会调用这个函数，筛选代码可以写在这里
        this.pageNum = pageNum;
        this.pageSize = pageSize;

        // 上一页，下一页确定
        prePageIndex = pageNum - 1;
        nextPageIndex = pageNum + 1;

        // 计算条数
        this.total = total;
        // 计算页数
        if (this.total % pageSize == 0) {
            pageCount = this.total / pageSize;
        } else {
            pageCount = this.total / pageSize + 1;
        }

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
        if (isLastPage && b) {
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

    public List<T> bigList() {
        return bigList;
    }

    public void createPage(List bigList, int pageNum, int pageSize, int total) {
        boolean b = null != bigList && 0 != bigList.size();

        //这里的PageNum取当前的实时页数 因为需要做逻辑控制
        this.setCurentPageIndex(pageNum, pageSize, total, b);

        if (b) {
            PageNumAndSize page = PageNumAndSize.checkPage1(pageNum, pageSize);
            //这里的PageNum永远从1开始 因为它负责切分List
            pageNum = 1;
            pageSize = page.getPageSize();

            this.bigList = bigList;

            // 筛选工作
            list = new ArrayList<>();
            for (int i = (pageNum - 1) * pageSize; i < pageNum
                    * pageSize
                    && i < bigList.size(); i++) {
                list.add(bigList.get(i));
            }
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(ArrayList<T> smallList) {
        this.list = smallList;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotal() {
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

    public boolean getIsFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean getIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public boolean getHasNextPage() {
        return this.hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}
