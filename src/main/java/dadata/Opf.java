package dadata;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Opf {
    //код ОКОПФ
    @SerializedName("code")
    private String code;

    //полное название ОПФ
    @SerializedName("full")
    private String fullName;

    //краткое название ОПФ
    @SerializedName("short")
    private String shortName;

    //версия справочника ОКОПФ (99, 2012 или 2014)
    @SerializedName("type")
    private String type;
}
