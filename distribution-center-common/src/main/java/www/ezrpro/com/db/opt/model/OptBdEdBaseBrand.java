package www.ezrpro.com.db.opt.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
* 
* @auth: nanChen
* @date: 2018-12-21  14:25:49
* 
*/

@Getter
@Setter
@AllArgsConstructor
public class OptBdEdBaseBrand {
    private Integer id;

    private Integer copid;

    private String code;

    private String name;

    private String isactive;

    private Integer crmdbshardingid;

    private Integer malldbshardingid;

    private Integer cmsdbshardingid;

    private Integer rtldbshardingid;

    private Integer logdbshardingid;

    private Integer esdbshardingid;

    private String esdbshardingtype;

    private Integer esprodutshardingid;

    private String esprodutshardingtype;

    private Integer esreportshardingid;

    private String createuser;

    private Date createdate;

    private String lastmodifieduser;

    private Date lastmodifieddate;

    private Integer zkid;

    private Integer shardinggrpid;

}