package myprotocalv2;

import java.io.Serializable;

/**
 * Created by Liuwei on 2020/7/21 9:02
 */
public class ProtocalEntity implements Serializable {
    private Integer id;
    private String name;
    private String version;
    private String param;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "ProtocalEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", param='" + param + '\'' +
                '}';
    }
}
