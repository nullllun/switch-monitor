package cn.albumenj.switchmonitor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TreeEvent;
import org.snmp4j.util.TreeUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SNMP工具
 * 目前只提供Walk功能
 *
 * @author Albumen
 */
@Component
public class SnmpUtil {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final int VERSION = SnmpConstants.version2c;
    private static final String PROTOCOL = "udp";
    private static final int PORT = 161;

    private Snmp snmp = null;
    private CommunityTarget target = null;
    private TransportMapping transport = null;
    private TreeUtils treeUtils;

    public SnmpUtil() {

        try {
            transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            transport.listen();
            treeUtils = new TreeUtils(snmp, new DefaultPDUFactory());
        } catch (Exception e) {
            logger.warn("SNMP walk Exception: " + e);
        }
    }

    public Map<Integer,String> walk(String ip, String community, String targetOid){
        String address = PROTOCOL + ":" + ip + "/" + PORT;
        target = createCommunityTarget(address, community, VERSION, 1500, 2);
        OID targetOID = new OID(targetOid);
        try {
            return snmpWalk(snmp, target, targetOID);
        } catch (Exception e) {
            logger.warn("SNMP walk Exception: " + e);
            return null;
        }
    }

    public void close(){
        if (snmp != null) {
            try {
                snmp.close();
            } catch (IOException ex1) {
                snmp = null;
            }
        }
        if (transport != null) {
            try {
                transport.close();
            } catch (IOException ex2) {
                transport = null;
            }
        }
    }

    private Map<Integer, String> snmpWalk(Snmp snmp, CommunityTarget target, OID targetOID) throws Exception {
        Map<Integer, String> result = new HashMap<>(16);

        List<TreeEvent> events = treeUtils.getSubtree(target, targetOID);
        if (events == null || events.size() == 0) {
            logger.warn("Error: Unable to read table...");
            return result;
        }

        for (TreeEvent event : events) {
            if (event == null) {
                continue;
            }
            if (event.isError()) {
                logger.debug("Error: table OID [" + targetOID.toString() + "] " + event.getErrorMessage());
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
                int[] i = varBinding.getOid().getValue();
                result.put(i[i.length - 1], varBinding.getVariable().toString());
            }

        }
        return result;
    }

    /**
     * check snmp walk finish
     *
     * @param targetOID
     * @param responsePDU
     * @param vb
     * @return
     */
    private boolean checkWalkFinished(OID targetOID, PDU responsePDU,
                                      VariableBinding vb) {
        boolean finished = false;
        if (responsePDU.getErrorStatus() != 0) {
            logger.warn("responsePDU.getErrorStatus() != 0");
            logger.warn(responsePDU.getErrorStatusText());
            finished = true;
        } else if (vb.getOid() == null) {
            logger.trace("vb.getOid() == null");
            finished = true;
        } else if (vb.getOid().size() < targetOID.size()) {
            logger.trace("vb.getOid().size() < targetOID.size()");
            finished = true;
        } else if (targetOID.leftMostCompare(targetOID.size(), vb.getOid()) != 0) {
            logger.trace("targetOID.leftMostCompare() != 0");
            finished = true;
        } else if (Null.isExceptionSyntax(vb.getVariable().getSyntax())) {
            logger.warn("Null.isExceptionSyntax(vb.getVariable().getSyntax())");
            finished = true;
        } else if (vb.getOid().compareTo(targetOID) <= 0) {
            logger.warn("Variable received is not " + "lexicographic successor of requested " + "one:");
            logger.warn(vb.toString() + " <= " + targetOID);
            finished = true;
        }
        return finished;
    }

    /**
     * 创建共同体对象communityTarget
     *
     * @param targetAddress
     * @param community
     * @param version
     * @param timeOut
     * @param retry
     * @return CommunityTarget
     */
    public static CommunityTarget createCommunityTarget(Address targetAddress, String community, int version, long timeOut, int retry) {
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(community));
        target.setAddress(targetAddress);
        target.setVersion(version);
        // milliseconds
        target.setTimeout(timeOut);
        target.setRetries(retry);
        return target;
    }

    /**
     * 创建共同体对象communityTarget
     *
     * @param address
     * @param community
     * @param version
     * @param timeOut
     * @param retry
     * @return CommunityTarget
     */
    public static CommunityTarget createCommunityTarget(String address, String community, int version, long timeOut, int retry) {
        Address targetAddress = GenericAddress.parse(address);
        return createCommunityTarget(targetAddress, community, version,
                timeOut, retry);
    }
}
