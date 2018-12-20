package www.ezrpro.com.model;

import lombok.Data;

@Data
public abstract class BaseRespon{

    private boolean status;
    private int statusCode;
    private String msg;

    
}