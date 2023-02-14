package com.wcx.onlineshop.service.impl;

import com.wcx.onlineshop.dao.CategoryMapper;
import com.wcx.onlineshop.pojo.Category;
import com.wcx.onlineshop.service.ICategoryService;
import com.wcx.onlineshop.vo.CategoryVO;
import com.wcx.onlineshop.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static com.wcx.onlineshop.consts.OnlineShopConst.ROOT_PARENT_ID;


@Service
public class CategoryServiceImpl implements ICategoryService {

    /**
     * 耗时：http请求 > 磁盘 > 内存
     * mysql(内网+磁盘）
     * 注意不要在for循环中发起http请求或读磁盘，否则耗时长
     */

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseVO<List<CategoryVO>> selectAll() {

        List<Category> categoryList = categoryMapper.selectAll();

        List<CategoryVO> categoryVOList = new ArrayList<>();
        //查询parentId=0，即一级目录
        for (Category category : categoryList) {
            if (category.getParentId().equals(ROOT_PARENT_ID)) {
                categoryVOList.add(category2CategoryVO(category));
            }
        }
        // 同级别目录下，类别根据sort_order字段的值排序
        categoryVOList.sort(Comparator.comparing(CategoryVO::getSortOrder).reversed());
//        //lamdba + stream 替换for()循环查询一级目录
//        List<CategoryVO> categoryVOList = categoryList.stream()
//                .filter(e -> e.getParentId().equals(ROOT_PARENT_ID))
//                .map(this::category2CategoryVO)
//                .collect(Collectors.toList());

        //查询子目录（包括多级子目录）
        findSubCategory(categoryVOList, categoryList);
        return ResponseVO.successByData(categoryVOList);
    }

    @Override
    public void findSubCategoryId(Integer id, Set<Integer> resultSet) {
        List<Category> categoryList = categoryMapper.selectAll();
        findSubCategoryId(id, resultSet, categoryList);
    }

    private void findSubCategoryId(Integer id, Set<Integer> resultSet, List<Category> categoryList) {
        for (Category category : categoryList) {
            if (category.getParentId().equals(id)) {
                resultSet.add(category.getId());
                findSubCategoryId(category.getId(), resultSet,categoryList);
            }
        }
    }


    private void findSubCategory(List<CategoryVO> categoryVOList, List<Category> categoryList) {
        for (CategoryVO categoryVO : categoryVOList) {
            List<CategoryVO> subCategories = new ArrayList<>();
            for (Category category : categoryList) {
                if (categoryVO.getId().equals(category.getParentId())) {
                    subCategories.add(category2CategoryVO(category));
                }
                // 同级别目录下，类别根据sort_order字段的值排序
                subCategories.sort(Comparator.comparing(CategoryVO::getSortOrder).reversed());
                // 递归调用自己，实现多级子目录查询
                findSubCategory(subCategories, categoryList);
            }
            categoryVO.setSubCategories(subCategories);
        }
    }

    private CategoryVO category2CategoryVO(Category category) {
        CategoryVO categoryVO = new CategoryVO();
        BeanUtils.copyProperties(category, categoryVO);
        return categoryVO;
    }
}
