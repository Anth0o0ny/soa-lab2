package org.anth0o0ny.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageMetadata {
    private int size;
    private int number;
    private long totalElements;
    private int totalPages;
}
