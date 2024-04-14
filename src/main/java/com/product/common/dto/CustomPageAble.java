package com.product.common.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomPageAble<T> {
    List<T> content;
    CustomPageable pageable;

    public CustomPageAble(Page<T> page) {
        this.content = page.getContent();
        this.pageable = new CustomPageable(page.getPageable().getPageNumber(),
                page.getPageable().getPageSize(), page.getTotalElements());
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Data
    class CustomPageable {
        int pageNumber;
        int pageSize;
        long totalElements;

        public CustomPageable(int pageNumber, int pageSize, long totalElements) {

            this.pageNumber = pageNumber;
            this.pageSize = pageSize;
            this.totalElements = totalElements;
        }
    }
}
