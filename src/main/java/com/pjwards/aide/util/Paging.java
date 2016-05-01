package com.pjwards.aide.util;

public class Paging {
    private int requestPage;        // Request page
    private int totalPageCount;     // Total page size
    private int firstRow;           // First row
    private int endRow;             // End row
    private int beginPage;          // Begin page
    private int endPage;            // End page

    public Paging paging(int requestPage, int countPerPage, int totalCount) {

        final int PAGE_PER_SIZE = 10;
        int totalPageCount = 0;
        int firstRow = 0;
        int endRow = 0;
        int beginPage = 0;
        int endPage = 0;

        if(totalCount != 0 || requestPage < 0) {
            /*
            Get total page size from total article size
            */
            // Total Articles : 31 / Per Page : 10
            totalPageCount = totalCount / countPerPage;         // pageCount : 3
            if(totalCount % countPerPage > 0) {
                totalPageCount++;                               // remainder is 1. So add 1. pageCount : 4
            }

            /*
            Get first row and end row in articles
             */
            firstRow = (requestPage - 1) * countPerPage + 1;
            endRow = firstRow + countPerPage - 1;               // end row has itself. So count it out.

            if(endRow > totalCount){
                endRow = totalCount;
            }

            /*
            Begin and End page for previous and next
             */
            if(totalCount != 0) {
                beginPage = (requestPage - 1) / PAGE_PER_SIZE * PAGE_PER_SIZE + 1;
                endPage = beginPage + PAGE_PER_SIZE - 1;
                if(endPage > totalPageCount){
                    endPage = totalPageCount;
                }
            }
        } else {
            requestPage = 0;
        }

        Paging paging = new Paging(requestPage, totalPageCount, firstRow, endRow, beginPage, endPage);

        return paging;
    }

    public Paging() {
    }

    public Paging(int requestPage, int totalPageCount, int firstRow, int endRow, int beginPage, int endPage) {
        this.requestPage = requestPage;
        this.totalPageCount = totalPageCount;
        this.firstRow = firstRow;
        this.endRow = endRow;
        this.beginPage = beginPage;
        this.endPage = endPage;
    }

    public int getRequestPage() {
        return requestPage;
    }

    public void setRequestPage(int requestPage) {
        this.requestPage = requestPage;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(int firstRow) {
        this.firstRow = firstRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getBeginPage() {
        return beginPage;
    }

    public void setBeginPage(int beginPage) {
        this.beginPage = beginPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }
}
