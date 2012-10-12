package com.vaadin.bugrap.business;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

@MappedSuperclass
abstract public class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private int consistencyVersion = -1;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getConsistencyVersion() {
        return consistencyVersion;
    }

    public void setConsistencyVersion(int consistencyVersion) {
        this.consistencyVersion = consistencyVersion;
    }

    @Transient
    public boolean isPersistent() {
        return id != null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (obj.getClass().equals(this.getClass())) {

            if (!isPersistent()) {
                return super.equals(obj);
            }

            return this.getId().equals(((AbstractEntity) obj).getId());
        }

        return false;
    }

    @Override
    public int hashCode() {
        if (getId() == null) {
            super.hashCode();
        }

        return id.hashCode();
    }
}
