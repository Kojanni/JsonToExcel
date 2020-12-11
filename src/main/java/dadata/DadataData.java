package dadata;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;

@Getter
public class DadataData {
//базовые поля

    //ИНН
    @SerializedName("inn")
    private String inn;

    //КПП
    @SerializedName("kpp")
    private String kpp;

    //ОГРН
    @SerializedName("ogrn")
    private String ogrn;

    //Дата выдачи ОГРН
    @SerializedName("ogrn_date")
    private Long ogrnDate;

    //Внутренний идентификатор в Дадате
    @SerializedName("hid")
    private String hid;

    /**
     * Тип организации
     * LEGAL — юридическое лицо;
     * INDIVIDUAL — индивидуальный предприниматель.
     */
    @SerializedName("type")
    private String type;

    //Наименование
    @SerializedName("name")
    private Name name;

//    //Код ОКАТО
//    @SerializedName("okato")
//    private String okato;

//    //Код ОКТМО
//    @SerializedName("oktmo")
//    private String oktmo;

    //Код ОКПО (не заполняется)
    @SerializedName("okpo")
    private String okpo;

    //Код ОКВЭД
    @SerializedName("okved")
    private String okved;

    //Версия справочника ОКВЭД (2001 или 2014)
    @SerializedName("okved_type")
    private String okvedType;

    //ОКОПФ
    @SerializedName("opf")
    private Opf opf;

    //Руководители
    @SerializedName("management")
    private Management management;

    /**
     * Тип подразделения
     * MAIN   — головная организация
     * BRANCH — филиал
     */
    @SerializedName("branch_type")
    private String branchType;

    //Количество филиалов
    @SerializedName("branch_count")
    private String branchCount;

    //Адрес
    @SerializedName("address")
    private Address address;

    //Статус
    @SerializedName("state")
    private State state;


//Расширенный и максимальный тарифы

    //Среднесписочная численность работников
    @SerializedName("employee_count")
    private long employeeCount;

    //Сведения о налоговой, ПФР и ФСС
    @SerializedName("authorities")
    private Authorities authorities;

    //Учередители компании
    @SerializedName("founders")
    private List<Founder> founders;

    //Руководители компании
    @SerializedName("managers")
    private List<Manager> managers;

    //Финансовые показатели за год
    @SerializedName("finance")
    private Finance finance;

    //Уставной капитал компании
    @SerializedName("capital")
    private Capital capital;

    //Документы
    @SerializedName("documents")
    private Documents documents;

    //Лицензии
    @SerializedName("licenses")
    private List<License> licenses;
}
