package www.ezrpro.com.model;


import lombok.Getter;
import lombok.Setter;

/**
* 
* @auth: nanChen
* @date: 2018-12-21  10:56:11
* 
*/

@Getter
@Setter
public class ClientRequest{


    /**
     * 用来查找所属哪个shardgrp
     */
    private int id;

    /**
     * brandId或者shardingId
     */
    private String idType;

    /**
     * 表名,服务端分配，有可能是虚拟的表名,例如行为分析的数据
     */
    private String tableName;

    /**
     * 具体的数据
     */
    private String data;
    
    /**
     * 服务端分配的应用id,用来区分数据来自哪个业务
     */
    private String appId;

    /**
     * 压缩算法
     */
    private String compressType;

    /**
     * 加密算法
     */
    private String encryptType;

}