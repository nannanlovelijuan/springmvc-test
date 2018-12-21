package www.ezrpro.com.db.opt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
* 
* @auth: nanChen
* @date: 2018-12-21  14:26:18
* 
*/

@Getter
@Setter
@AllArgsConstructor
public class OptBdShardCfg {
    private Integer id;

    private Integer shardingid;

    private String datacenter;

    private Integer regioncount;

    private Integer clusterid;

    private Integer shardinggrpid;

    private String eshost;

    private Integer esid;

    private String hbnamespace;

    private Integer zkid;

}