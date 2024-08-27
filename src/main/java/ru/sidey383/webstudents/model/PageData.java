package ru.sidey383.webstudents.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageData<T> {

    private List<T> data;

    private int pageSize;

    private int pageCount;

    private int pageNumber;

}
