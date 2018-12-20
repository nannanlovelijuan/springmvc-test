package www.ezrpro.com.model;

import lombok.Data;

@Data
public class HttpRespon<T> extends BaseRespon{

    private T result;
}