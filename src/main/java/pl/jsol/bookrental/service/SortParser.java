package pl.jsol.bookrental.service;

import org.springframework.data.domain.Sort;

public abstract class SortParser {

    public static Sort.Direction parseSortFromString(String sortStrategy) {
        return sortStrategy == null ?
                Sort.Direction.ASC : sortStrategy.toLowerCase().equals("desc") ?
                        Sort.Direction.DESC : Sort.Direction.ASC;
    }
}
