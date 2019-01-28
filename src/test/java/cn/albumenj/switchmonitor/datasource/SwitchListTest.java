package cn.albumenj.switchmonitor.datasource;

import cn.albumenj.switchmonitor.bean.SwitchesList;
import cn.albumenj.switchmonitor.service.SwitchesListService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SwitchListTest {
    @Autowired
    SwitchesListService switchesListService;
    @Test
    public void test(){
    }

    public void insert() {
        SwitchesList switchesList = new SwitchesList();
        switchesList.setIp("0.0.0.0");
        switchesList.setBuilding("TEST1");
        switchesList.setModel("0000");
        switchesList.setDescription("");
        switchesListService.insert(switchesList);
    }

    public void select(){
        List<SwitchesList> switchesLists = switchesListService.select(new SwitchesList());
        for(SwitchesList s:switchesLists){
            System.out.println(s.getId() + " " + s.getModel() + " " + s.getBuilding() + " " + s.getDescription());
        }
    }

    public void update(){
        List<SwitchesList> switchesLists = switchesListService.select(new SwitchesList());
        for(SwitchesList s:switchesLists){
            if(s.getBuilding().compareTo("TEST1")==0){
                s.setBuilding("TEST2");
                switchesListService.update(s);
            }
        }
    }

    public void delete(){
        List<SwitchesList> switchesLists = switchesListService.select(new SwitchesList());
        for(SwitchesList s:switchesLists){
            if(s.getBuilding().compareTo("TEST2")==0){
                switchesListService.delete(s);
            }
        }
    }

}

