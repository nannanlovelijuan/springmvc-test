package www.ezrpro.com.util;

import www.ezrpro.com.db.opt.impl.OptBdEdBaseBrandMapperImpl;
import www.ezrpro.com.db.opt.impl.OptBdShardCfgMapperImpl;
import www.ezrpro.com.db.opt.impl.OptBdZkMapperImpl;
import www.ezrpro.com.db.opt.model.OptBdEdBaseBrand;
import www.ezrpro.com.db.opt.model.OptBdShardCfg;
import www.ezrpro.com.db.opt.model.OptBdZk;

/**
* 
* @auth: nanChen
* @date: 2018-12-21  14:38:40
* 
*/

public class DbUtil{

    /**
     * 根据brandId查询获取包含shardingId的品牌表的对象
     * @param brandId 
     * @return
     */
    public static OptBdEdBaseBrand getShardIdByBrandId(int brandId){
        OptBdEdBaseBrandMapperImpl optBdEdBaseBrandMapper = new OptBdEdBaseBrandMapperImpl();
        return optBdEdBaseBrandMapper.selectByPrimaryKey(brandId);
    }

    /**
     * 根据zkId获取hbase连接地址
     * @param zkId
     * @return
     */
    public static OptBdZk getOptBdZk(int zkId){
        OptBdZkMapperImpl optBdZkMapper = new OptBdZkMapperImpl();
        return optBdZkMapper.selectByPrimaryKey(zkId);
    }

    /**
     * 根据shardingId 获取shardGrp
     * @param shardingId
     * @return
     */
    public static OptBdShardCfg getShardGrpByShardingId(int shardingId){
        OptBdShardCfgMapperImpl optBdShardCfgMapper = new OptBdShardCfgMapperImpl();
        return optBdShardCfgMapper.selectByShardingId(shardingId);
    }
}