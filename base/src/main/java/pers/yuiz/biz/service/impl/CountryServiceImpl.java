package pers.yuiz.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.yuiz.biz.dao.CountryDao;
import pers.yuiz.biz.entity.Country;
import pers.yuiz.biz.service.CountryService;
import pers.yuiz.common.PageDTO;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryDao countryDao;

    /**
     * 分页查看国家数据
     *
     * @param pageDTO
     * @return
     */
    @Override
    public PageInfo<Country> listCountry(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPageNum(),pageDTO.getPageSize());
        List<Country> list = countryDao.selectAll();
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
}