package com.xiewende.creativehomesuppliescity.service;

import com.xiewende.creativehomesuppliescity.pojo.Brand;

import java.util.List;

/**
 * @create 2021-04-11  18:42
 */
public interface BrandService {

    //添加
    public Integer insertBrand(Brand brand,String designerName);

    //修改
    public Integer updateBrand(Brand brand,String designerName);

    //删除
    public Integer deleteBrand(Integer id);

    //根据id查询
    public Brand selectBrandById(Integer id);

    //查询全部
    public List<Brand> listAllBrand();
}
