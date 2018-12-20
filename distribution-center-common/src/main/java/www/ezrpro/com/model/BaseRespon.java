package www.ezrpro.com.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseRespon{

    private boolean status;
    private int statusCode;
    private String msg;

    
}