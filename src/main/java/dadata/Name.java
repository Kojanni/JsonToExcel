package dadata;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Name {
    //полное наименование
    @SerializedName("full_with_opf")
    private String fullWithOpf;
    //краткое наименование
    @SerializedName("short_with_opf")
    private String shortWithOpf;
    //не заполняется
    @SerializedName("latin")
    private String latin;
    //полное наименование (будет удалено в 2021)
    @SerializedName("full")
    private String fullWithoutOpf;

    //краткое наименование (будет удалено в 2021)
    @SerializedName("short")
    private String shortWithoutOpf;

}
