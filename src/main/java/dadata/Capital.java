package dadata;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Capital {
    //тип капитала
    @SerializedName("type")
    private String type;

    //размер капитала
    @SerializedName("value")
    private Long value;
}
