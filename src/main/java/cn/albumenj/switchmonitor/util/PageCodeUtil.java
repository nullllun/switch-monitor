package cn.albumenj.switchmonitor.util;

import cn.albumenj.switchmonitor.constant.PageCodeEnum;
import cn.albumenj.switchmonitor.dto.PageCodeDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 向客户端下发状态
 *
 * @author Albumen
 */
public class PageCodeUtil {

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
