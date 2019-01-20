package cn.albumenj.switchmonitor.schedule;

import cn.albumenj.switchmonitor.dao.SwitchesStatusMapper;
import cn.albumenj.switchmonitor.dto.BriefStatusDto;
import cn.albumenj.switchmonitor.dto.SwitchesDetailDto;
import cn.albumenj.switchmonitor.dto.SwitchesPortDetailDto;
import cn.albumenj.switchmonitor.dto.WarningDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

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

    public void refresh() {
        List<SwitchesDetailDto> switchesDetailDtos = switchesStatusMapper.selectDetail();
        List<WarningDto> warning = new LinkedList<>();
        List<Integer> cpuLoad = new LinkedList<>();
        List<Integer> memUsed = new LinkedList<>();
        List<Integer> temp = new LinkedList<>();
        List<Long> status = new LinkedList<>();
        for (SwitchesDetailDto switchesDetailDto : switchesDetailDtos) {
            if (switchesDetailDto.getTemp() != null && switchesDetailDto.getTemp() > tempThreshold) {
                WarningDto warningDto = new WarningDto("heat",switchesDetailDto.getOriginIp(),switchesDetailDto.getModel());
                warningDto.setTemp(switchesDetailDto.getTemp().toString());
                warning.add(warningDto);
            }
            if (switchesDetailDto.getCpuLoad() != null && switchesDetailDto.getCpuLoad() > cpuThreshold) {
                WarningDto warningDto = new WarningDto("cpu_overload",switchesDetailDto.getOriginIp(),switchesDetailDto.getModel());
                warningDto.setCpuLoad(switchesDetailDto.getCpuLoad().toString());
                warning.add(warningDto);
            }
            if (switchesDetailDto.getMemoryUsed() != null && switchesDetailDto.getMemoryUsed() > memThreshold) {
                WarningDto warningDto = new WarningDto("mem_overload",switchesDetailDto.getOriginIp(),switchesDetailDto.getModel());
                warningDto.setMemUsed(switchesDetailDto.getMemoryUsed().toString());
                warning.add(warningDto);
            }
            if (switchesDetailDto.getReachable().equals(0)) {
                WarningDto warningDto = new WarningDto("devices_down",switchesDetailDto.getOriginIp(),switchesDetailDto.getModel());
                warningDto.setDownTime(switchesDetailDto.getDownTime().getTime());
                warning.add(warningDto);
            }
            for (SwitchesPortDetailDto switchesPortDetailDto : switchesDetailDto.getSwitchesPortDetailDto()) {
                if (switchesPortDetailDto.getInSpeed() != null
                        && switchesPortDetailDto.getInSpeed() >
                        (switchesPortDetailDto.getSpeedMax() * 131072 / 100 * speedThreshold)
                        && switchesPortDetailDto.getInSpeed() <
                        (switchesPortDetailDto.getSpeedMax() * 131072)) {
                    WarningDto warningDto = new WarningDto("if_in",switchesDetailDto.getOriginIp(),switchesDetailDto.getModel());
                    warningDto.setPortSpeed(switchesPortDetailDto.getInSpeed() / 131072 + "/" + switchesPortDetailDto.getSpeedMax());
                    warningDto.setPortName(switchesPortDetailDto.getName());
                    warning.add(warningDto);
                }
                if (switchesPortDetailDto.getOutSpeed() != null
                        && switchesPortDetailDto.getOutSpeed() >
                        (switchesPortDetailDto.getSpeedMax() * 131072 / 100 * speedThreshold)
                        && switchesPortDetailDto.getOutSpeed() <
                        (switchesPortDetailDto.getSpeedMax() * 131072)) {
                    WarningDto warningDto = new WarningDto("if_out",switchesDetailDto.getOriginIp(),switchesDetailDto.getModel());
                    warningDto.setPortSpeed(switchesPortDetailDto.getOutSpeed() / 131072 + "/" + switchesPortDetailDto.getSpeedMax());
                    warningDto.setPortName(switchesPortDetailDto.getName());
                    warning.add(warningDto);
                }
            }
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
            }
            else {
                status.add(switchesDetailDto.getDownTime().getTime());
            }
        }
        briefStatusDtoCpu.setCpu(cpuLoad);
        briefStatusDtoMem.setMem(memUsed);
        briefStatusDtoTemp.setTemp(temp);
        briefStatusDtoWarn.setWarning(warning);
        briefStatusDtoStat.setStat(status);
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
}
