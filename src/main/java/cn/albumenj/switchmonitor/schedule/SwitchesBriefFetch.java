package cn.albumenj.switchmonitor.schedule;

import cn.albumenj.switchmonitor.dao.SwitchesStatusMapper;
import cn.albumenj.switchmonitor.dto.BriefStatusDto;
import cn.albumenj.switchmonitor.dto.SwitchesDetailDto;
import cn.albumenj.switchmonitor.dto.SwitchesPortDetailDto;
import cn.albumenj.switchmonitor.dto.WarningDto;
import cn.albumenj.switchmonitor.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 获取设备概况
 * 获取时间较长，定为每分钟获取一次
 * 在此方法调用前客户端无法正常获取到首页数据
 *
 * @author Albumen
 */
@Component
public class SwitchesBriefFetch {
    @Value("${threshold.cpu}")
    Integer cpuThreshold;
    @Value("${threshold.mem}")
    Integer memThreshold;
    @Value("${threshold.temp}")
    Integer tempThreshold;
    @Value("${threshold.speed}")
    Integer speedThreshold;
    @Autowired
    SwitchesStatusMapper switchesStatusMapper;

    private volatile static BriefStatusDto briefStatusDtoCpu = new BriefStatusDto();
    private volatile static BriefStatusDto briefStatusDtoMem = new BriefStatusDto();
    private volatile static BriefStatusDto briefStatusDtoTemp = new BriefStatusDto();
    private volatile static BriefStatusDto briefStatusDtoWarn = new BriefStatusDto();
    private volatile static BriefStatusDto briefStatusDtoStat = new BriefStatusDto();
    private volatile static BriefStatusDto briefStatusDtoReach = new BriefStatusDto();

    public void refresh() {
        List<SwitchesDetailDto> switchesDetailDtos = switchesStatusMapper.selectDetail();
        List<WarningDto> warning = new LinkedList<>();
        List<Integer> cpuLoad = new LinkedList<>();
        List<Integer> memUsed = new LinkedList<>();
        List<Integer> temp = new LinkedList<>();
        List<Long> status = new LinkedList<>();
        List<WarningDto> reach = new LinkedList<>();
        for (SwitchesDetailDto switchesDetailDto : switchesDetailDtos) {
            getWarning(switchesDetailDto, warning);

            /**
             * 图表
             */
            if (!"".equals(switchesDetailDto.getCpuLoad())) {
                cpuLoad.add(Integer.valueOf(switchesDetailDto.getCpuLoad()));
            }
            if (!"".equals(switchesDetailDto.getMemoryUsed())) {
                memUsed.add(Integer.valueOf(switchesDetailDto.getMemoryUsed()));
            }
            if (!"".equals(switchesDetailDto.getTemp())) {
                temp.add(Integer.valueOf(switchesDetailDto.getTemp()));
            }
            if (switchesDetailDto.getReachable() == 1) {
                status.add(1L);
            } else {
                status.add(switchesDetailDto.getDownTime().getTime());
            }
        }

        getReach(switchesDetailDtos, reach, warning);

        briefStatusDtoCpu.setCpu(cpuLoad);
        briefStatusDtoMem.setMem(memUsed);
        briefStatusDtoTemp.setTemp(temp);
        briefStatusDtoWarn.setWarning(warning);
        briefStatusDtoStat.setStat(status);
        briefStatusDtoReach.setWarning(reach);

        switchesDetailDtos = null;
    }

    public BriefStatusDto getBriefStatusDtoCpu() {
        return briefStatusDtoCpu;
    }

    public BriefStatusDto getBriefStatusDtoMem() {
        return briefStatusDtoMem;
    }

    public BriefStatusDto getBriefStatusDtoTemp() {
        return briefStatusDtoTemp;
    }

    public BriefStatusDto getBriefStatusDtoWarn() {
        return briefStatusDtoWarn;
    }

    public BriefStatusDto getBriefStatusDtoStat() {
        return briefStatusDtoStat;
    }

    public BriefStatusDto getBriefStatusDtoReach() {
        return briefStatusDtoReach;
    }

    private void getReach(List<SwitchesDetailDto> switchesDetailDtos, List<WarningDto> reach, List<WarningDto> warning) {
        /**
         * 按楼栋分组
         */
        Map<String, List<SwitchesDetailDto>> linkedListMap = new LinkedHashMap<>();
        for (SwitchesDetailDto switchesDetailDto : switchesDetailDtos) {
            List<SwitchesDetailDto> list = linkedListMap.get(switchesDetailDto.getBuilding());
            if (list == null) {
                list = new LinkedList<>();
                linkedListMap.put(switchesDetailDto.getBuilding(), list);
            }
            list.add(switchesDetailDto);
        }

        Set<Map.Entry<String, List<SwitchesDetailDto>>> entries = linkedListMap.entrySet();
        for (Map.Entry<String, List<SwitchesDetailDto>> entry : entries) {
            List<WarningDto> list = new LinkedList<>();
            for (SwitchesDetailDto switchesDetailDto : entry.getValue()) {
                if (switchesDetailDto.getReachable().equals(0)) {
                    WarningDto warningDto = new WarningDto("devices_down",
                            switchesDetailDto.getOriginIp(), switchesDetailDto.getModel(), switchesDetailDto.getBuilding());
                    warningDto.setDownTime(switchesDetailDto.getDownTime().getTime());
                    list.add(warningDto);
                }
            }
            if (list.size() == entry.getValue().size()) {
                WarningDto warningDto = new WarningDto("devices_down",
                        IpUtil.getSegment(entry.getValue().get(0).getOriginIp(),3),
                        "全部设备", entry.getKey());
                warningDto.setDownTime(entry.getValue().get(0).getDownTime().getTime());
                warning.add(warningDto);
                reach.add(warningDto);
            } else {
                warning.addAll(list);
                reach.addAll(list);
            }
        }
    }

    private void getWarning(SwitchesDetailDto switchesDetailDto, List<WarningDto> warning) {
        /**
         * 导航栏警告
         */
        if (switchesDetailDto.getTemp() != null && switchesDetailDto.getTemp() > tempThreshold) {
            WarningDto warningDto = new WarningDto("heat",
                    switchesDetailDto.getOriginIp(), switchesDetailDto.getModel(), switchesDetailDto.getBuilding());
            warningDto.setTemp(switchesDetailDto.getTemp().toString());
            warning.add(warningDto);
        }
        if (switchesDetailDto.getCpuLoad() != null && switchesDetailDto.getCpuLoad() > cpuThreshold) {
            WarningDto warningDto = new WarningDto("cpu_overload",
                    switchesDetailDto.getOriginIp(), switchesDetailDto.getModel(), switchesDetailDto.getBuilding());
            warningDto.setCpuLoad(switchesDetailDto.getCpuLoad().toString());
            warning.add(warningDto);
        }
        if (switchesDetailDto.getMemoryUsed() != null && switchesDetailDto.getMemoryUsed() > memThreshold) {
            WarningDto warningDto = new WarningDto("mem_overload",
                    switchesDetailDto.getOriginIp(), switchesDetailDto.getModel(), switchesDetailDto.getBuilding());
            warningDto.setMemUsed(switchesDetailDto.getMemoryUsed().toString());
            warning.add(warningDto);
        }
        for (SwitchesPortDetailDto switchesPortDetailDto : switchesDetailDto.getSwitchesPortDetailDto()) {
            if (switchesPortDetailDto.getInSpeed() != null
                    && switchesPortDetailDto.getInSpeed() >
                    (switchesPortDetailDto.getSpeedMax() * 131072 / 100 * speedThreshold)
                    && switchesPortDetailDto.getInSpeed() <
                    (switchesPortDetailDto.getSpeedMax() * 131072)) {
                WarningDto warningDto = new WarningDto("if_in",
                        switchesDetailDto.getOriginIp(), switchesDetailDto.getModel(), switchesDetailDto.getBuilding());
                warningDto.setPortSpeed(switchesPortDetailDto.getInSpeed() / 131072 + "/" + switchesPortDetailDto.getSpeedMax());
                warningDto.setPortName(switchesPortDetailDto.getName());
                warningDto.setCvlan(switchesPortDetailDto.getCvlan());
                warningDto.setPvlan(switchesPortDetailDto.getPvlan());
                warning.add(warningDto);
            }
            if (switchesPortDetailDto.getOutSpeed() != null
                    && switchesPortDetailDto.getOutSpeed() >
                    (switchesPortDetailDto.getSpeedMax() * 131072 / 100 * speedThreshold)
                    && switchesPortDetailDto.getOutSpeed() <
                    (switchesPortDetailDto.getSpeedMax() * 131072)) {
                WarningDto warningDto = new WarningDto("if_out",
                        switchesDetailDto.getOriginIp(), switchesDetailDto.getModel(), switchesDetailDto.getBuilding());
                warningDto.setPortSpeed(switchesPortDetailDto.getOutSpeed() / 131072 + "/" + switchesPortDetailDto.getSpeedMax());
                warningDto.setPortName(switchesPortDetailDto.getName());
                warningDto.setCvlan(switchesPortDetailDto.getCvlan());
                warningDto.setPvlan(switchesPortDetailDto.getPvlan());
                warning.add(warningDto);
            }
        }
    }
}
