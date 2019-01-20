package cn.albumenj.switchmonitor.util;

import cn.albumenj.switchmonitor.constant.PageCodeEnum;
import cn.albumenj.switchmonitor.dto.PageCodeDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Albumen
 */
public class PageCodeUtil {
    public static PageCodeDto get(Object o) {
        if (o instanceof Integer) {
            if (((Integer) o).compareTo(0) == 0) {
                return new PageCodeDto(PageCodeEnum.GET_FAILED);
            } else {
                return new PageCodeDto(PageCodeEnum.GET_SUCCESS, o);
            }
        } else {
            if (o != null) {
                return new PageCodeDto(PageCodeEnum.GET_SUCCESS, o);
            } else {
                return new PageCodeDto(PageCodeEnum.GET_FAILED);
            }
        }
    }

    public static PageCodeDto add(boolean flag) {
        if (flag) {
            return new PageCodeDto(PageCodeEnum.ADD_SUCCESS);
        } else {
            return new PageCodeDto(PageCodeEnum.ADD_FAILED);
        }
    }

    public static PageCodeDto delete(boolean flag) {
        if (flag) {
            return new PageCodeDto(PageCodeEnum.DELETE_SUCCESS);
        } else {
            return new PageCodeDto(PageCodeEnum.DELETE_FAILED);
        }
    }

    public static PageCodeDto update(boolean flag) {
        if (flag) {
            return new PageCodeDto(PageCodeEnum.MODIFY_SUCCESS);
        } else {
            return new PageCodeDto(PageCodeEnum.MODIFY_FAILED);
        }
    }

    public static void printCode(HttpServletResponse response, PageCodeEnum pageCodeEnum) throws IOException {
        printCode(response, pageCodeEnum, 200);
    }

    public static void printCode(HttpServletResponse response, PageCodeEnum pageCodeEnum, Integer statusCode) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(statusCode);
        PrintWriter writer = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(new PageCodeDto(pageCodeEnum));
        writer.print(json);
        writer.close();
        response.flushBuffer();
    }
}
