package com.example.movierama.ui.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Pagination {
    private int page;
    private int pageSize;
    private int totalPages;
    private String sortBy;

}
