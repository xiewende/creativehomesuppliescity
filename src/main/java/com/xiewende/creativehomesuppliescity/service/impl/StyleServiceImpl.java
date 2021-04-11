package com.xiewende.creativehomesuppliescity.service.impl;

import com.xiewende.creativehomesuppliescity.mapper.StyleMapper;
import com.xiewende.creativehomesuppliescity.pojo.Style;
import com.xiewende.creativehomesuppliescity.pojo.StyleExample;
import com.xiewende.creativehomesuppliescity.service.StyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @create 2021-04-10  21:38
 */
@Service
public class StyleServiceImpl implements StyleService {

    @Autowired
    private StyleMapper styleMapper;

    @Override
    public Integer insertStyle(Style style) {
        //查重
        StyleExample styleExample = new StyleExample();
        StyleExample.Criteria criteria = styleExample.createCriteria();
        criteria.andTypeNameEqualTo(style.getTypeName());
        List<Style> styles = styleMapper.selectByExample(styleExample);
        if(styles.size()>0) return 0;

        return styleMapper.insert(style);
    }

    @Override
    public Integer updateStyle(Style style) {
        //查重
        StyleExample styleExample = new StyleExample();
        StyleExample.Criteria criteria = styleExample.createCriteria();
        criteria.andTypeNameEqualTo(style.getTypeName());
        List<Style> styles = styleMapper.selectByExample(styleExample);
        if(styles.size()>0) return 0;

        return styleMapper.updateByPrimaryKey(style);
    }

    @Override
    public Integer deleteStyle(Integer id) {
        Style style = styleMapper.selectByPrimaryKey(id);
        style.setIsdelete(1);
        return styleMapper.updateByPrimaryKey(style);
    }

    @Override
    public List<Style> listAllStyle() {
        return styleMapper.selectByExample(new StyleExample());
    }

    @Override
    public Style selectStyleById(Integer id) {
        return styleMapper.selectByPrimaryKey(id);
    }
}
