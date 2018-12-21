package www.ezrpro.com.db.opt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
* 
* @auth: nanChen
* @date: 2018-12-21  14:26:41
* 
*/

@Getter
@Setter
@AllArgsConstructor
public class OptBdZk {
    private Integer id;

    private String zkhost;

    private String zkclustername;

}