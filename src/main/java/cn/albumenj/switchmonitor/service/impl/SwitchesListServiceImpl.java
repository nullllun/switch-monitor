package cn.albumenj.switchmonitor.service.impl;

import cn.albumenj.switchmonitor.bean.SwitchesList;
import cn.albumenj.switchmonitor.dao.SwitchesListMapper;
import cn.albumenj.switchmonitor.service.SwitchesListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Albumen
 */
@Service
public class SwitchesListServiceImpl implements SwitchesListService{

    @Autowired
    private SwitchesListMapper switchesListMapper;

    @Override
    public int insert(SwitchesList switchesList){
        return switchesListMapper.insert(switchesList);
    }

    @Override
    public int insertList(List<SwitchesList> switchesLists){
        return switchesListMapper.insertList(switchesLists);
    }

    @Override
    public int update(SwitchesList switchesList){
        return switchesListMapper.update(switchesList);
    }

    @Override
    public List<SwitchesList> select(SwitchesList switchesList) {
        return switchesListMapper.select(switchesList);
    }

    @Override
    public List<SwitchesList> selectOnline(SwitchesList switchesList) {
        return switchesListMapper.selectOnline(switchesList);
    }

    @Override
    public int delete(SwitchesList switchesList) {
        return switchesListMapper.delete(switchesList);
    }

    @Override
    public List<String> selectBuilding() {
        return switchesListMapper.selectBuilding();
    }
}
