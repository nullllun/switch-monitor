package cn.albumenj.switchmonitor.dao;

import cn.albumenj.switchmonitor.bean.PortStatus;
import cn.albumenj.switchmonitor.dto.PortStatusDto;
import cn.albumenj.switchmonitor.dto.VlanDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PortStatusMapper {
    int insert(@Param("portStatus") PortStatus portStatus);

    int insertList(@Param("portStatuss") List<PortStatus> portStatuss);

    int update(@Param("portStatus") PortStatus portStatus);

    int updateVlan(@Param("portStatus") PortStatus portStatus);

    int updateList(@Param("portStatuses") List<PortStatus> portStatuses);

    int delete(@Param("portStatus") PortStatus portStatus);

    List<PortStatus> selectBySwitch(@Param("portStatus") PortStatus portStatus);

    PortStatus selectByPort(@Param("portStatus") PortStatus portStatus);

    List<PortStatusDto> selectByIP(@Param("ip") String ip);

    List<PortStatus> selectOld();

    List<VlanDto> selectVlanOrigin();

    String fetchPort(@Param("ip") String ip, @Param("port") String port);
}
