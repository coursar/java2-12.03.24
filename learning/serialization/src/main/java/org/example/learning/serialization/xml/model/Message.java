package org.example.learning.serialization.xml.model;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message {
    private long id;
    private String value;

    public Message(long id, String value) {
        this.id = id;
        this.value = value;
    }

    public Message() {
    }

    public long getId() {
        return this.id;
    }

    public String getValue() {
        return this.value;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Message)) return false;
        final Message other = (Message) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        final Object this$value = this.getValue();
        final Object other$value = other.getValue();
        if (this$value == null ? other$value != null : !this$value.equals(other$value)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Message;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $id = this.getId();
        result = result * PRIME + (int) ($id >>> 32 ^ $id);
        final Object $value = this.getValue();
        result = result * PRIME + ($value == null ? 43 : $value.hashCode());
        return result;
    }

    public String toString() {
        return "Message(id=" + this.getId() + ", value=" + this.getValue() + ")";
    }
}
