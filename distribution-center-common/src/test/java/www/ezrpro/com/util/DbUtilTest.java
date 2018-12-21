package www.ezrpro.com.util;

import www.ezrpro.com.db.opt.model.OptBdEdBaseBrand;
import www.ezrpro.com.db.opt.model.OptBdShardCfg;

/**
* 
* @auth: nanChen
* @date: 2018-12-21  15:02:36
* 
*/

public class DbUtilTest{
    public static void main(String[] args) {
        OptBdEdBaseBrand brandObject = DbUtil.getShardIdByBrandId(1);
        int shardingId = brandObject.getCrmdbshardingid();
        System.out.println("----------shardingId:"+shardingId);

        OptBdShardCfg optBdShardCfg = DbUtil.getShardGrpByShardingId(shardingId);

        int shardGrp = optBdShardCfg.getShardinggrpid();
        System.out.println("----------shardGrp:"+shardGrp);

    }
}