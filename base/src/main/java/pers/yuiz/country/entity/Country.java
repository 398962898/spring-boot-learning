package pers.yuiz.country.entity;

import java.io.Serializable;
import java.util.Objects;

public class Country implements Serializable{
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country that = (Country) o;

        return Objects.equals(this.countryCode, that.countryCode) &&
                Objects.equals(this.countryName, that.countryName) &&
                Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCode, countryName, id);
    }
}