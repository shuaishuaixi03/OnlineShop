package com.wcx.onlineshop.service;

import com.wcx.onlineshop.vo.CategoryVO;
import com.wcx.onlineshop.vo.ResponseVO;

import java.util.List;
import java.util.Set;

public interface ICategoryService {

    ResponseVO<List<CategoryVO>> selectAll();

    /**
     *
     */
    void findSubCategoryId(Integer id, Set<Integer> resultSet);
}
