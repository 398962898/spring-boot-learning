package pers.yuiz.common.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 特别注意，该接口不能被扫描到，否则会出错
 *
 * @param <T>
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
