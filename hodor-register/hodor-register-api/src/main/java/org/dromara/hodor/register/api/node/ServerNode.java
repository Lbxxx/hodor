package org.dromara.hodor.register.api.node;

/**
 * server node
 *
 * @author tomgs
 * @since 2020/7/6
 */
public class ServerNode {

    public static final String METADATA_PATH = "/scheduler/metadata";
    public static final String NODES_PATH = "/scheduler/nodes";
    public static final String COPY_SETS_PATH = "/scheduler/copysets";
    public static final String MASTER_PATH = "/scheduler/master";
    public static final String WORK_PATH = "/scheduler/work";

    public static String getServerNodePath(String serverId) {
        return String.format("%s/%s", NODES_PATH, serverId);
    }

}
