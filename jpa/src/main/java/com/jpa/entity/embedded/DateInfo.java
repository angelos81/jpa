package com.jpa.entity.embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class DateInfo {
    @Column(name = "reg_date")
    private Date regDate;

    @Column(name = "mod_date")
    private Date modDate;

    public DateInfo(){}
    public DateInfo(Date regDate, Date modDate){
        this.regDate = regDate;
        this.modDate = modDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateInfo dateInfo = (DateInfo) o;

        if (!Objects.equals(regDate, dateInfo.regDate)) return false;
        return Objects.equals(modDate, dateInfo.modDate);
    }

    @Override
    public int hashCode() {
        int result = regDate != null ? regDate.hashCode() : 0;
        result = 31 * result + (modDate != null ? modDate.hashCode() : 0);
        return result;
    }
}
