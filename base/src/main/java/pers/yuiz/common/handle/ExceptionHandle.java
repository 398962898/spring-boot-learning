package pers.yuiz.common.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.yuiz.common.exception.WarnException;
import pers.yuiz.common.util.ResultUtil;
import pers.yuiz.common.vo.Result;

@ControllerAdvice
public class ExceptionHandle {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        if (e instanceof WarnException) {
            WarnException warnException = (WarnException) e;
            return ResultUtil.warnException(warnException);
        } else {
            logger.error("【系统异常】{}", e);
            return ResultUtil.serverError();
        }
    }
}
