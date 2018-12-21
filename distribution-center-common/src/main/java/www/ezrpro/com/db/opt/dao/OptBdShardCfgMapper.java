package www.ezrpro.com.db.opt.dao;

import www.ezrpro.com.db.opt.model.OptBdShardCfg;

/**
 * 
 * @auth: nanChen
 * @date: 2018-12-21 14:21:24
 * 
 */

public interface OptBdShardCfgMapper {

    OptBdShardCfg selectByShardingId(Integer id);

}