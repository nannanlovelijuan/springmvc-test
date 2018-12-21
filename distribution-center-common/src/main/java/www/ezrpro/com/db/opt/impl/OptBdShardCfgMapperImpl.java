package www.ezrpro.com.db.opt.impl;

import org.apache.ibatis.session.SqlSession;

import www.ezrpro.com.db.opt.DbTools;
import www.ezrpro.com.db.opt.dao.OptBdShardCfgMapper;
import www.ezrpro.com.db.opt.model.OptBdShardCfg;

/**
* 
* @auth: nanChen
* @date: 2018-12-21  15:19:33
* 
*/

public class OptBdShardCfgMapperImpl implements OptBdShardCfgMapper {
    
    public OptBdShardCfg selectByShardingId(Integer id) {
        SqlSession session = DbTools.getSqlSession();
        try {
            OptBdShardCfgMapper mapper = session.getMapper(OptBdShardCfgMapper.class);
            return mapper.selectByShardingId(id);
        }finally {
            if(session != null){
                DbTools.closeSqlSession(session);
            }
        }
    }
    
}
