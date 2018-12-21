package www.ezrpro.com.db.opt.impl;

import org.apache.ibatis.session.SqlSession;

import www.ezrpro.com.db.opt.DbTools;
import www.ezrpro.com.db.opt.dao.OptBdEdBaseBrandMapper;
import www.ezrpro.com.db.opt.model.OptBdEdBaseBrand;

/**
* 
* @auth: nanChen
* @date: 2018-12-21  15:19:23
* 
*/

public class OptBdEdBaseBrandMapperImpl implements OptBdEdBaseBrandMapper {
    
    public OptBdEdBaseBrand selectByPrimaryKey(Integer id) {
        SqlSession session = DbTools.getSqlSession();
        try {
            OptBdEdBaseBrandMapper mapper = session.getMapper(OptBdEdBaseBrandMapper.class);
            return mapper.selectByPrimaryKey(id);
        }finally {
            if(session != null){
                DbTools.closeSqlSession(session);
            }
        }
    }
}
