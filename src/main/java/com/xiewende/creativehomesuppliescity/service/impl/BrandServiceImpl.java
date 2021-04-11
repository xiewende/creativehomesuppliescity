package com.xiewende.creativehomesuppliescity.service.impl;

import com.xiewende.creativehomesuppliescity.mapper.BrandMapper;
import com.xiewende.creativehomesuppliescity.mapper.DesignerMapper;
import com.xiewende.creativehomesuppliescity.pojo.Brand;
import com.xiewende.creativehomesuppliescity.pojo.BrandExample;
import com.xiewende.creativehomesuppliescity.pojo.Designer;
import com.xiewende.creativehomesuppliescity.pojo.DesignerExample;
import com.xiewende.creativehomesuppliescity.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @create 2021-04-11  18:52
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private DesignerMapper designerMapper;

    @Override
    public Integer insertBrand(Brand brand,String designerName) {
        //去重
        BrandExample brandExample = new BrandExample();
        BrandExample.Criteria criteria = brandExample.createCriteria();
        criteria.andBrandNameEqualTo(brand.getBrandName());
        List<Brand> brands = brandMapper.selectByExample(brandExample);
        if(brands.size()!=0) return 0;
        //没有重复
        //去找设计师的id
        DesignerExample designerExample = new DesignerExample();
        DesignerExample.Criteria criteria1 = designerExample.createCriteria();
        criteria1.andDesignerNameEqualTo(designerName);
        List<Designer> designers = designerMapper.selectByExample(designerExample);
        brand.setDesignerId(designers.get(0).getId());
        return brandMapper.insert(brand);
    }

    @Override
    public Integer updateBrand(Brand brand,String designerName) {
        //去重
        BrandExample brandExample = new BrandExample();
        BrandExample.Criteria criteria = brandExample.createCriteria();
        criteria.andBrandNameEqualTo(brand.getBrandName());
        criteria.andIdNotEqualTo(brand.getId());
        List<Brand> brands = brandMapper.selectByExample(brandExample);
        if(brands.size()!=0) return 0;
        //没有重复
        //去找设计师的id
        DesignerExample designerExample = new DesignerExample();
        DesignerExample.Criteria criteria1 = designerExample.createCriteria();
        criteria1.andDesignerNameEqualTo(designerName);
        List<Designer> designers = designerMapper.selectByExample(designerExample);
        brand.setDesignerId(designers.get(0).getId());
        return brandMapper.updateByPrimaryKey(brand);
    }

    @Override
    public Integer deleteBrand(Integer id) {
        Brand brand = brandMapper.selectByPrimaryKey(id);
        brand.setIsdelete(1);
        return brandMapper.updateByPrimaryKey(brand);
    }

    @Override
    public Brand selectBrandById(Integer id) {
        Brand brand = brandMapper.selectByPrimaryKey(id);
        //去找对应的设计师
        Designer designer = designerMapper.selectByPrimaryKey(brand.getDesignerId());
        brand.setDesigner(designer);
        return brand;
    }

    @Override
    public List<Brand> listAllBrand() {
        BrandExample brandExample = new BrandExample();
        BrandExample.Criteria criteria = brandExample.createCriteria();
        criteria.andIsdeleteEqualTo(0);//没有删除的
        List<Brand> brands = brandMapper.selectByExample(brandExample);
        //封装设计师
        for(Brand brand : brands){
            brand.setDesigner(designerMapper.selectByPrimaryKey(brand.getDesignerId()));
        }
        return brands;
    }
}
