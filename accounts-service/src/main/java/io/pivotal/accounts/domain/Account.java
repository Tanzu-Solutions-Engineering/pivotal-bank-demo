package io.pivotal.accounts.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
/**
 * Represents the account.
 *
 * Entity object that represents a user account.
 *
 * @author David Ferreira Pinto
 *
 */
@Entity
@Table(name = "ACCOUNT")
public class Account implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -3057275461420965784L;

	@Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
	private Integer id;

	@Column(name = "userid", length = 250)
    @NotNull
    private String userid;
	
	@Column(name = "name", length = 250)
    @NotNull
    private String name;
	
	@Column(name = "accounttype")
	@NotNull
	@Enumerated(EnumType.STRING)
	//@Convert(converter = AccountTypeConverter.class)
	private AccountType type;

	@Column(name = "creationdate")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "LL")
	@NotNull
    private Date creationdate;

	@Column(name = "openbalance", precision = 14, scale = 2, nullable=false)
	@NotNull
    private BigDecimal openbalance = new BigDecimal(0);

	@Column(name = "balance", precision = 14, scale = 2, nullable=false)
	@NotNull
    private BigDecimal balance = new BigDecimal(0);
	
	@Column(name = "currency", length = 3)
	@NotNull
	private String currency;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public Date getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public BigDecimal getOpenbalance() {
		return openbalance;
	}

	public void setOpenbalance(BigDecimal openbalance) {
		this.openbalance = openbalance;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result
				+ ((creationdate == null) ? 0 : creationdate.hashCode());
		result = prime * result
				+ ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((openbalance == null) ? 0 : openbalance.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (creationdate == null) {
			if (other.creationdate != null)
				return false;
		} else if (!creationdate.equals(other.creationdate))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (openbalance == null) {
			if (other.openbalance != null)
				return false;
		} else if (!openbalance.equals(other.openbalance))
			return false;
		if (type != other.type)
			return false;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Account [id=").append(id).append(", userid=")
				.append(userid).append(", name=").append(name)
				.append(", type=").append(type).append(", creationdate=")
				.append(creationdate).append(", openbalance=")
				.append(openbalance).append(", balance=").append(balance)
				.append(", currency=").append(currency).append("]");
		return builder.toString();
	}

}
