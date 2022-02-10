package com.ubuuy.springserver.models.entities;

import com.ubuuy.springserver.models.enums.MetaActionEnum;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "meta")
@Access(AccessType.PROPERTY)
public class MetaEntity extends BaseEntity{

    private ZonedDateTime dateTime = ZonedDateTime.now();
    private String systemUser;
    private MetaActionEnum action;
    private Long _v = 0L;

    public MetaEntity() {
    }

    @Column(name = "date_time", unique = false)
    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public MetaEntity setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    @Column(name = "system_user", unique = false)
    public String getSystemUser() {
        return systemUser;
    }

    public MetaEntity setSystemUser(String systemUser) {
        this.systemUser = systemUser;
        return this;
    }

    @Enumerated(EnumType.STRING)
    public MetaActionEnum getAction() {
        return action;
    }

    public MetaEntity setAction(MetaActionEnum action) {
        this.action = action;
        if(MetaActionEnum.UPDATE.equals(action)){
            this.updateVersion();
        }
        return this;
    }

    @Column(name = "_v", unique = false)
    public Long get_v() {
        return _v;
    }

    public MetaEntity set_v(Long _v) {
        this._v = _v;
        return this;
    }

    private void updateVersion(){
        this._v = this._v +1;
    }
}
