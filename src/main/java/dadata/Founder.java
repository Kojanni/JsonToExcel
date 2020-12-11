package dadata;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;

@Getter
public class Founder {

    //ОГРН учередителя (для юрлиц)
    @SerializedName("ogrn")
    private String ogrn;

    //ИНН учередителя
    @SerializedName("inn")
    private String inn;

    //наименование учередителя (для юрлиц)
    @SerializedName("name")
    private String name;

    //ФИО учередителя (для физлиц
    @SerializedName("fio")
    private FIO fio;

    //должность учередителя (для физлиц)
    @SerializedName("post")
    private String post;

    //внутренний идентификатор
    @SerializedName("hid")
    private String hid;

    /**тип учредителя
     * (LEGAL / PHYSICAL)
     */
    @SerializedName("type")
    private String type;

    //Доля учредителя
    @SerializedName("share")
    private Share share;

    @Data
    public class Share {
        //тип значения (PERCENT / DECIMAL / FRACTION)
        @SerializedName("type")
        private String type;

        //значение (для type = PERCENT и type = DECIMAL)
        @SerializedName("value")
        private String value;

        //числитель дроби (для type = FRACTION)
        @SerializedName("numerator")
        private String numerator;

        //знаменатель дроби (для type = FRACTION)
        @SerializedName("denominator")
        private String denominator;

    }
}
