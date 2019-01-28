package cn.albumenj.switchmonitor.snmp;

import cn.albumenj.switchmonitor.bean.SwitchesList;
import cn.albumenj.switchmonitor.service.SwitchesListService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.snmp4j.CommunityTarget;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TreeEvent;
import org.snmp4j.util.TreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WalkTest {
    @Autowired
    SwitchesListService switchesListService;

    //@Test
    public void test(){
        List<SwitchesList> switchesLists = switchesListService.select(new SwitchesList());

        ExecutorService executorService = Executors.newCachedThreadPool();
        for(SwitchesList s:switchesLists){

        }

    }

    //@Test
    public void main() {
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("gdgydx_pub"));
        target.setAddress(GenericAddress.parse("udp:172.16.101.1/161")); // supply your own IP and port
        target.setRetries(5);
        target.setTimeout(20);
        target.setVersion(SnmpConstants.version2c);

        Map<String, String> result = null; // ifTable, mib-2 interfaces
        try {
            result = doWalk(".1.3.6.1.2.1.2.2.1.2", target);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Map<String, String> doWalk(String tableOid, Target target) throws IOException {
        Map<String, String> result = new TreeMap<>();
        TransportMapping<? extends Address> transport = new DefaultUdpTransportMapping();
        Snmp snmp = new Snmp(transport);
        transport.listen();

        TreeUtils treeUtils = new TreeUtils(snmp, new DefaultPDUFactory());

        List<TreeEvent> events = treeUtils.getSubtree(target, new OID(tableOid));
        if (events == null || events.size() == 0) {
            System.out.println("Error: Unable to read table...");
            return result;
        }

        for (TreeEvent event : events) {
            if (event == null) {
                continue;
            }
            if (event.isError()) {
                System.out.println("Error: table OID [" + tableOid + "] " + event.getErrorMessage());
                continue;
            }

            VariableBinding[] varBindings = event.getVariableBindings();
            if (varBindings == null || varBindings.length == 0) {
                continue;
            }
            for (VariableBinding varBinding : varBindings) {
                if (varBinding == null) {
                    continue;
                }

                result.put("." + varBinding.getOid().toString(), varBinding.getVariable().toString());
            }

        }
        snmp.close();

        return result;
    }
}
