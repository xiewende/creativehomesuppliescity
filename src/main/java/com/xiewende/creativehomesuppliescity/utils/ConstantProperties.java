package com.xiewende.creativehomesuppliescity.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @create 2021-04-10  10:59
 */

@Component
@PropertySource("classpath:application-dev.properties")
public class ConstantProperties {

    @Value("${com.xiewende.name}")
    private String name;

    //默认头像地址
    @Value("${com.xiewende.defalt.imge.path}")
    private String defalt_imge_path;

    //默认虚拟机ip
    @Value("${com.xiewende.defalt.ip}")
    private String defaltIp;

    public String getDefalt_imge_path() {
        return defalt_imge_path;
    }

    public void setDefalt_imge_path(String defalt_imge_path) {
        this.defalt_imge_path = defalt_imge_path;
    }

    public String getDefaltIp() {
        return defaltIp;
    }

    public void setDefaltIp(String defaltIp) {
        this.defaltIp = defaltIp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
