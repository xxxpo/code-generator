package com.xxxlin.core.config;

import com.xxxlin.core.utils.R;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 全局异常处理器
 * Date:    2019年08月28日 2:18 下午
 *
 * @author xiaolin
 * @version 0.1
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R handleException(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        System.out.println(e.toString());
        if(e instanceof MethodArgumentTypeMismatchException){
            MethodArgumentTypeMismatchException ex = (MethodArgumentTypeMismatchException) e;
            System.out.println("name:"+ex.getName());
            System.out.println("value:"+ex.getValue());
        }else if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            List<ObjectError> errors = ex.getBindingResult().getAllErrors();
            String message = errors.get(0).getDefaultMessage();
            return new R(-1, message);
        } else if(e instanceof NumberFormatException){
            return R.error(ResultStatus.非法参数异常);
        } else if(e instanceof IllegalArgumentException){
            return R.error(ResultStatus.非法参数异常);
        }

        return R.error(e);
    }

}
