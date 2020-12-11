package dadata;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Manager {
    //ОГРН руководителя (для юрлиц)
    @SerializedName("ogrn")
    private String ogrn;

    //ИНН руководителя
    @SerializedName("inn")
    private String inn;

    //наименование руководителя (для юрлиц)
    @SerializedName("name")
    private String name;

    //ФИО руководителя (для физлиц
    @SerializedName("fio")
    private FIO fio;

    //должность руководителя (для физлиц)
    @SerializedName("post")
    private String post;

    //внутренний идентификатор
    @SerializedName("hid")
    private String hid;

    /**тип руководителя
    EMPLOYEE  — сотрудник
    FOREIGNER — иностранный гражданин
    LEGAL     — юрлицо
     */
    @SerializedName("type")
    private String type;


}
