package www.ezrpro.com.db.opt.impl;

import org.apache.ibatis.session.SqlSession;

import www.ezrpro.com.db.opt.DbTools;
import www.ezrpro.com.db.opt.dao.OptBdZkMapper;
import www.ezrpro.com.db.opt.model.OptBdZk;

/**
* 
* @auth: nanChen
* @date: 2018-12-21  14:24:52
* 
*/

public class OptBdZkMapperImpl implements OptBdZkMapper {
    
    public OptBdZk selectByPrimaryKey(Integer id) {
        SqlSession session = DbTools.getSqlSession();
        try {
            OptBdZkMapper mapper = session.getMapper(OptBdZkMapper.class);
            return mapper.selectByPrimaryKey(id);
        }finally {
            if(session != null){
                DbTools.closeSqlSession(session);
            }
        }
    }
    
}
