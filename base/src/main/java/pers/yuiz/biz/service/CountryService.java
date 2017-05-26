package pers.yuiz.biz.service;

import com.github.pagehelper.PageInfo;
import pers.yuiz.biz.entity.Country;
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
