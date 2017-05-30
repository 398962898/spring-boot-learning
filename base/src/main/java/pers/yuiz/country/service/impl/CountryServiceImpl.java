package pers.yuiz.country.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.yuiz.country.mapper.CountryMapper;
import pers.yuiz.country.entity.Country;
import pers.yuiz.country.service.CountryService;
import pers.yuiz.common.dto.PageDTO;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryMapper countryMapper;

    /**
     * 分页查看国家数据
     *
     * @param pageDTO
     * @return
     */
    @Override
    public PageInfo<Country> listCountry(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPageNum(),pageDTO.getPageSize());
        List<Country> list = countryMapper.selectAll();
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
}
