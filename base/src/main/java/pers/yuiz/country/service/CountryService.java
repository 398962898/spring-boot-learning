package pers.yuiz.country.service;

import com.github.pagehelper.PageInfo;
import pers.yuiz.country.entity.Country;
import pers.yuiz.common.dto.PageDTO;

public interface CountryService {
    /**
     * 分页查看国家数据
     *
     * @param pageDTO
     * @return
     */
    public PageInfo<Country> listCountry(PageDTO pageDTO);
}
