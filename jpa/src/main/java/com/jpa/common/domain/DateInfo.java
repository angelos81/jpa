package com.jpa.common.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Embeddable
@Setter
@NoArgsConstructor
@Builder
public class DateInfo {
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "mod_date")
    private LocalDateTime modDate;

    public DateInfo(LocalDateTime regDate, LocalDateTime modDate){
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
