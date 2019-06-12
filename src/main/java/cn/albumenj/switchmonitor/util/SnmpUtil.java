package cn.albumenj.switchmonitor.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * SNMP工具
 * 目前只提供Walk功能
 *
 * @author Albumen
 */
@Component
public class SnmpUtil {
    private static final Logger logger = LoggerFactory.getLogger(SnmpUtil.class);

    public SnmpUtil() {
    }

    public Map<Integer, String> walk(String ip, String community, String targetOid) {
        Map<Integer, String> result = new HashMap<>(16);
        Map<Integer, String> ret = walk(ip, community, targetOid, result);
        if (ret.size() == 0) {
            return walk(ip, community, targetOid, result);
        } else {
            return ret;
        }
    }

    public Map<Integer, String> walk(String ip, String community, String targetOid, Map<Integer, String> result) {
        SnmpRequest snmpRequest = new SnmpRequest(ip, community, targetOid);
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ObjectMapper objectMapper = new ObjectMapper();
        HttpEntity<String> requestEntity = null;
        try {
            requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(snmpRequest), headers);
            ResponseEntity<ResultVO> response = client.exchange("http://127.0.0.1:8085/api/snmpwalk", method, requestEntity, ResultVO.class);
            if (Objects.isNull(response.getBody()) || response.getBody().getCode().equals(-1)) {
                return new HashMap<>(1);
            } else {
                result.putAll(response.getBody().getData());
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>(1);
    }

    class SnmpRequest {
        private String ip;
        private String community;
        private String targetOid;

        public SnmpRequest(String ip, String community, String targetOid) {
            this.ip = ip;
            this.community = community;
            this.targetOid = targetOid;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public String getTargetOid() {
            return targetOid;
        }

        public void setTargetOid(String targetOid) {
            this.targetOid = targetOid;
        }
    }

    public static class ResultVO<T> {

        private Integer code;

        private Map<Integer, String> data;

        public ResultVO() {
        }

        public ResultVO(Integer code) {
            this.code = code;
        }

        public ResultVO(Integer code, Map<Integer, String> data) {
            this.code = code;
            this.data = data;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public Map<Integer, String> getData() {
            return data;
        }

        public void setData(Map<Integer, String> data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "ResultVO{" +
                    "code=" + code +
                    ", data=" + data +
                    '}';
        }
    }
}
