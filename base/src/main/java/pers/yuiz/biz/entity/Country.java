package pers.yuiz.biz.entity;

public class Country {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 名称
     */
    private String countryName;

    /**
     * 代码
     */
    private String countryCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}