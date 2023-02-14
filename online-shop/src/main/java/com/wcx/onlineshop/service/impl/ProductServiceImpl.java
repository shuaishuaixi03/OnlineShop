package com.wcx.onlineshop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcx.onlineshop.dao.ProductMapper;
import com.wcx.onlineshop.pojo.Product;
import com.wcx.onlineshop.service.ICategoryService;
import com.wcx.onlineshop.service.IProductService;
import com.wcx.onlineshop.vo.ProductDetailVO;
import com.wcx.onlineshop.vo.ProductVO;
import com.wcx.onlineshop.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.wcx.onlineshop.enums.ProductStatusEnum.DELETE;
import static com.wcx.onlineshop.enums.ProductStatusEnum.OFF_SALE;
import static com.wcx.onlineshop.enums.ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE;

@Service
public class ProductServiceImpl implements IProductService {


    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResponseVO<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        Set<Integer> categoryIdSet = new HashSet<>();
        if (categoryId != null) {
            categoryService.findSubCategoryId(categoryId, categoryIdSet);
            categoryIdSet.add(categoryId);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.selectByCategoryIdSet(categoryIdSet);
        List<ProductVO> productVOList = productList.stream()
                .map(e -> {
                    ProductVO productVO = new ProductVO();
                    BeanUtils.copyProperties(e, productVO);
                    return productVO;
                })
                .collect(Collectors.toList());
        PageInfo productPageInfo = new PageInfo<>(productList);
        productPageInfo.setList(productVOList);
        return ResponseVO.successByData(productPageInfo);
    }

    @Override
    public ResponseVO<ProductDetailVO> detail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product.getStatus().equals(OFF_SALE.getCode()) || product.getStatus().equals(DELETE.getCode())) {
            return ResponseVO.error(PRODUCT_OFF_SALE_OR_DELETE);
        }
        ProductDetailVO productDetailVO = new ProductDetailVO();
        BeanUtils.copyProperties(product, productDetailVO);
        //考虑到库存是敏感数据，对于库存量大于100的按100处理
        productDetailVO.setStock(productDetailVO.getStock() > 100 ? 100 : productDetailVO.getStock());
        return ResponseVO.successByData(productDetailVO);
    }
}
