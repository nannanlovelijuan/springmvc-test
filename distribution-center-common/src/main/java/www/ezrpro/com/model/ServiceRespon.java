package www.ezrpro.com.model;

import lombok.Getter;
import lombok.Setter;

/**
* 
* @auth: nanChen
* @date: 2018-12-21  10:56:03
* 
*/
@Getter
@Setter
public class ServiceRespon<T> extends BaseRespon{

    /**
     * 具体数据
     */
    private T result;
}