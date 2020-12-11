package dadata;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;


@Getter
public class Dadata {
    //Наименование компании одной строкой (как показывается в списке подсказок)

    private String value;

    //Наименование компании одной строкой (полное)
    @SerializedName("unrestricted_value")
    private String unrestrictedValue;

    private DadataData data;
}
