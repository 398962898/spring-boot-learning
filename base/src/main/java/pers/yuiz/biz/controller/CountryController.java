package pers.yuiz.biz.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yuiz.biz.entity.Country;
import pers.yuiz.biz.service.CountryService;
import pers.yuiz.common.dto.PageDTO;
import pers.yuiz.common.util.ResultUtil;
import pers.yuiz.common.vo.Result;

@RestController
@RequestMapping("/country")
public class CountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping("/list")
    public Result listCountry(PageDTO pageDTO) {
        if (pageDTO == null) {
            pageDTO = new PageDTO();
        }
        PageInfo<Country> pageInfo = countryService.listCountry(pageDTO);
        return ResultUtil.success(pageInfo);
    }
}
