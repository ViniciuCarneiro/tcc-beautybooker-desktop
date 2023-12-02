package com.beautybooker.beautybooker.models.bean;

import java.util.List;

public class ProfissionalResponse {

    private int page;
    private int pageSize;
    private int totalPages;

    private List<Profissional> profissional;

    public List<Profissional> getProfissional() {
        return profissional;
    }

    public void setProfissional(List<Profissional> profissional) {
        this.profissional = profissional;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
