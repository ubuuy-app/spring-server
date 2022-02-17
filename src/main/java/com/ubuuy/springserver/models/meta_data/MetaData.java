package com.ubuuy.springserver.models.meta_data;

import com.ubuuy.springserver.models.entities.BaseEntity;
import com.ubuuy.springserver.models.enums.MetaActionEnum;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "meta")
@Access(AccessType.PROPERTY)
public class MetaData extends BaseEntity {

    private ZonedDateTime dateTime = ZonedDateTime.now();
    private String systemUser;
    private MetaActionEnum action;
    private Long _v = 0L;

    public MetaData() {
    }

    @Column(name = "date_time", unique = false)
    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public MetaData setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    @Column(name = "system_user", unique = false)
    public String getSystemUser() {
        return systemUser;
    }

    public MetaData setSystemUser(String systemUser) {
        this.systemUser = systemUser;
        return this;
    }

    @Enumerated(EnumType.STRING)
    public MetaActionEnum getAction() {
        return action;
    }

    public MetaData setAction(MetaActionEnum action) {
        this.action = action;
        if (MetaActionEnum.UPDATE.equals(action)) {
            this.updateVersion();
        }
        return this;
    }

    @Column(name = "_v", unique = false)
    public Long get_v() {
        return _v;
    }

    public MetaData set_v(Long _v) {
        this._v = _v;
        return this;
    }

    private void updateVersion(){
        this._v = this._v +1;
    }
}
