package com.xiewende.creativehomesuppliescity.service.impl;

import com.xiewende.creativehomesuppliescity.mapper.BrandMapper;
import com.xiewende.creativehomesuppliescity.mapper.CategorySecondMapper;
import com.xiewende.creativehomesuppliescity.mapper.GoodsMapper;
import com.xiewende.creativehomesuppliescity.mapper.StyleMapper;
import com.xiewende.creativehomesuppliescity.pojo.*;
import com.xiewende.creativehomesuppliescity.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @create 2021-04-13  11:13
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private CategorySecondMapper categorySecondMapper;
    @Autowired
    private StyleMapper styleMapper;
    @Autowired
    private BrandMapper brandMapper;

    @Override
    //不考虑重复的问题
    public Integer insertGoods(Goods goods, String categorySecondName, String stypeName, String brandName) {
        //找分类id
        CategorySecondExample categorySecondExample = new CategorySecondExample();
        CategorySecondExample.Criteria criteria = categorySecondExample.createCriteria();
        criteria.andCategoryNameEqualTo(categorySecondName);
        List<CategorySecond> categorySeconds = categorySecondMapper.selectByExample(categorySecondExample);
        goods.setCategoryId(categorySeconds.get(0).getId());
        //找风格id
        StyleExample styleExample = new StyleExample();
        StyleExample.Criteria criteria1 = styleExample.createCriteria();
        criteria1.andTypeNameEqualTo(stypeName);
        List<Style> styles = styleMapper.selectByExample(styleExample);
        goods.setStyleId(styles.get(0).getId());
        //找品牌
        BrandExample brandExample = new BrandExample();
        BrandExample.Criteria criteria2 = brandExample.createCriteria();
        criteria2.andBrandNameEqualTo(brandName);
        List<Brand> brands = brandMapper.selectByExample(brandExample);
        goods.setBrandId(brands.get(0).getId());

        return goodsMapper.insert(goods);
    }

    @Override
    public Integer updateGoods(Goods goods, String categorySecondName, String stypeName, String brandName) {
        //找分类id
        CategorySecondExample categorySecondExample = new CategorySecondExample();
        CategorySecondExample.Criteria criteria = categorySecondExample.createCriteria();
        criteria.andCategoryNameEqualTo(categorySecondName);
        List<CategorySecond> categorySeconds = categorySecondMapper.selectByExample(categorySecondExample);
        goods.setCategoryId(categorySeconds.get(0).getId());
        //找风格id
        StyleExample styleExample = new StyleExample();
        StyleExample.Criteria criteria1 = styleExample.createCriteria();
        criteria1.andTypeNameEqualTo(stypeName);
        List<Style> styles = styleMapper.selectByExample(styleExample);
        goods.setStyleId(styles.get(0).getId());
        //找品牌
        BrandExample brandExample = new BrandExample();
        BrandExample.Criteria criteria2 = brandExample.createCriteria();
        criteria2.andBrandNameEqualTo(brandName);
        List<Brand> brands = brandMapper.selectByExample(brandExample);
        goods.setBrandId(brands.get(0).getId());

        return goodsMapper.updateByPrimaryKeySelective(goods);
    }

    @Override
    public Integer deleteGoods(Integer id) {
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        goods.setIsdelete(1);
        return goodsMapper.updateByPrimaryKeySelective(goods);
    }

    @Override
    public List<Goods> listGoodsWithSomeDemand(String categoryFirstName,String categorySecondName, String stypeName, String brandName, String goodName) {
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        criteria.andIsdeleteEqualTo(0);

        if(goodName != null){
            criteria.andGoodNameLike("%"+goodName+"%");
        }

        //若有二级分类查询二级分类，若没有查询一级分类下的全部
        if(categorySecondName!=null) {
            List<Integer> list = categorySecondMapper.selectIdOfLikeCategoryName(categorySecondName);
            criteria.andCategoryIdIn(list);
        }else {  //二级为空，采取一级
            if(categoryFirstName!=null){
                List<Integer> list4 = categorySecondMapper.selectIdOfLikeFirstName(categoryFirstName);
                criteria.andCategoryIdIn(list4);
            }
        }
        if(stypeName != null){
            List<Integer> list1 = styleMapper.selectIdOfLikeStyleName(stypeName);
            criteria.andStyleIdIn(list1);
        }
        if(brandName != null){
            List<Integer> list2 = brandMapper.selectIdOfLikeBrandName(brandName);
            criteria.andBrandIdIn(list2);
        }
        List<Goods> goods = goodsMapper.selectByExample(goodsExample);
        //封装其他信息
        for(Goods good : goods){
            good.setCategorySecond(categorySecondMapper.selectByPrimaryKey(good.getCategoryId()));
            good.setStyle(styleMapper.selectByPrimaryKey(good.getStyleId()));
            good.setBrand(brandMapper.selectByPrimaryKey(good.getBrandId()));
        }

        return goods;
    }

    @Override
    public Goods findGoodById(Integer id) {
        Goods good = goodsMapper.selectByPrimaryKey(id);
        good.setCategorySecond(categorySecondMapper.selectByPrimaryKey(good.getCategoryId()));
        good.setStyle(styleMapper.selectByPrimaryKey(good.getStyleId()));
        good.setBrand(brandMapper.selectByPrimaryKey(good.getBrandId()));
        return good;
    }

    @Override
    public Goods selectGoodsDetails(Integer id) {
        Goods good = goodsMapper.selectByPrimaryKey(id);
        good.setHitNumber(good.getHitNumber()+1);
        goodsMapper.updateByPrimaryKey(good);
        good.setCategorySecond(categorySecondMapper.selectByPrimaryKey(good.getCategoryId()));
        good.setStyle(styleMapper.selectByPrimaryKey(good.getStyleId()));
        good.setBrand(brandMapper.selectByPrimaryKey(good.getBrandId()));
        return good;
    }
}
