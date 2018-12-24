package www.ezrpro.com.util;

import org.apache.kafka.clients.admin.*;
import org.springframework.kafka.core.KafkaAdmin;
import www.ezrpro.com.db.opt.model.OptBdEdBaseBrand;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author liyuelin
 * @Date 2018/12/21
 */
public class KafkaUtil {
    /**
     *
     * @param id
     * @param idtype  1: BrandId   2: ShardingId
     * @return
     */
    public static Integer getShardingGrp(int id,int idtype){
        int ShardingId ;
        if (idtype == 1){
            OptBdEdBaseBrand optBdEdBaseBrand = DbUtil.getShardIdByBrandId(id);
            ShardingId = optBdEdBaseBrand.getCrmdbshardingid();
        }else {
            ShardingId = id;
        }
        return DbUtil.getShardGrpByShardingId(ShardingId).getShardinggrpid();
    }

    public static String getTopic(int id,int idtype,String appid,String tablieName){

        return getShardingGrp(id,idtype)+appid+tablieName;
    }

    public static KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        String hosts = "cluster2-slave2:9092,cluster2-slave3:9092,cluster2-slave4:9092";
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, hosts);
        return new KafkaAdmin(configs);
    }

    public static Boolean isExistTopic(String topicname) {
        KafkaAdmin kafkaAdmin = admin();
        try {
            AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfig());
            ListTopicsOptions listTopicsOptions = new ListTopicsOptions();
            listTopicsOptions.listInternal(true);
            ListTopicsResult res = adminClient.listTopics(listTopicsOptions);
            Boolean flag = res.names().get().contains(topicname);
            adminClient.close();
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean createTopic(String topicname) {
        try {
            Boolean existflag = isExistTopic(topicname);
            Boolean flag ;
            if (existflag == true){
                flag = true;
            }else {
                KafkaAdmin kafkaAdmin = admin();
                AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfig());
                NewTopic newTopic = new NewTopic(topicname,3,(short)3);
                List<NewTopic> topicList = Arrays.asList(newTopic);
                adminClient.createTopics(topicList);
                adminClient.close();
                flag = isExistTopic(topicname);
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
