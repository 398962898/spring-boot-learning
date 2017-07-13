package pers.yuiz.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.yuiz.common.costant.ResultCostant;
import pers.yuiz.common.exception.WarnException;
import pers.yuiz.common.vo.Result;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

public class ResultUtil {
    private final static Logger logger = LoggerFactory.getLogger(ResultUtil.class);

    /**
     * 成功,无数据返回
     *
     * @return
     */
    public static Result success() {
        return new Result(ResultCostant.SUCCESS);
    }

    /**
     * 成功,有数据返回
     *
     * @param data
     * @return
     */
    public static Result success(Object data) {
        Result result = new Result(ResultCostant.SUCCESS);
        if (data instanceof String) {
            result.setData(data);
            return result;
        } else {
            Map<String, Object> dataToMap = beanToMap(data, true);
            result.setData(dataToMap);
            return result;
        }
    }

    /**
     * 服务器异常
     *
     * @return
     */
    public static Result serverError() {
        return new Result(ResultCostant.SERVER_ERROR);
    }

    /**
     * 自定义异常
     *
     * @param e
     * @return
     */
    public static Result warnException(WarnException e) {
        return new Result(e);
    }

    /**
     * 对象转Map
     *
     * @param bean            bean对象
     * @param ignoreNullValue 是否忽略值为空的字段
     * @return Map
     */
    private static Map<String, Object> beanToMap(Object bean, boolean ignoreNullValue) {
        if (bean == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            final PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                boolean isClassFlag = ("class".equals(key)) || ("declaringClass".equals(key));
                if (!isClassFlag) {
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(bean);
                    if (false == ignoreNullValue || (null != value && false == value.equals(bean))) {
                        boolean putFlag = value instanceof Number
                                || value instanceof String
                                || value instanceof Date
                                || value instanceof Boolean
                                || value instanceof Map;
                        boolean isList = value instanceof List;
                        if (putFlag) {
                            map.put(key, value);
                        } else if (isList) {
                            List list = (List) value;
                            List valueList = new ArrayList(list.size());
                            for (int i = 0; i < list.size(); i++) {
                                Map<String, Object> valueMap = beanToMap(list.get(i), ignoreNullValue);
                                valueList.add(valueMap);
                            }
                            map.put(key, valueList);
                        } else {
                            Map<String, Object> valueMap = beanToMap(value, ignoreNullValue);
                            map.put(key, valueMap);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("对象转Map异常:{}", e.getLocalizedMessage());
        }
        return map;
    }
}
