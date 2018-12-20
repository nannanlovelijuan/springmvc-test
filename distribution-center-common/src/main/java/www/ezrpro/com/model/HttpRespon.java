package www.ezrpro.com.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpRespon<T> extends BaseRespon{

    private T result;
}