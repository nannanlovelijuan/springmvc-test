package www.ezrpro.com.model;

import lombok.Getter;
import lombok.Setter;

/**
* 
* @auth: nanChen
* @date: 2018-12-21  10:56:16
* 
*/

@Getter
@Setter
public abstract class BaseRespon{

    /**
     * 状态，true请求成功,false失败
     */
    private boolean status;

    /**
     * 状态码
     */
    private int statusCode;

    /**
     * 相关信息
     */
    private String msg;

    
}