
package es.upm.miw.demoretrofit.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Eng {

    @SerializedName("f")
    @Expose
    private String f;
    @SerializedName("m")
    @Expose
    private String m;

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

}
