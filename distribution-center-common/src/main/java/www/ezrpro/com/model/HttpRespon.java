package www.ezrpro.com.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class HttpRespon<T> extends BaseRespon{
    
    private T result;
}