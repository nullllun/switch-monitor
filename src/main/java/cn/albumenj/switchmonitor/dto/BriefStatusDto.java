package cn.albumenj.switchmonitor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BriefStatusDto {
    private List<WarningDto> warning;
    private List<Long> stat;
    private List<Integer> cpu;
    private List<Integer> mem;
    private List<Integer> temp;

    public List<WarningDto> getWarning() {
        return warning;
    }

    public void setWarning(List<WarningDto> warning) {
        this.warning = warning;
    }

    public List<Long> getStat() {
        return stat;
    }

    public void setStat(List<Long> stat) {
        this.stat = stat;
    }

    public List<Integer> getCpu() {
        return cpu;
    }

    public void setCpu(List<Integer> cpu) {
        this.cpu = cpu;
    }

    public List<Integer> getMem() {
        return mem;
    }

    public void setMem(List<Integer> mem) {
        this.mem = mem;
    }

    public List<Integer> getTemp() {
        return temp;
    }

    public void setTemp(List<Integer> temp) {
        this.temp = temp;
    }
}
